package com.xmrbi.warehouse.module.check.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.speedata.libuhf.bean.Tag_Data;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.ExceptionHandle;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;
import com.xmrbi.warehouse.data.entity.check.RfidUpdateAutoCheckingEntity;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.event.RfidScanEvent;
import com.xmrbi.warehouse.utils.ActivityStackUtils;
import com.xmrbi.warehouse.utils.RxBus;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wzn on 2018/4/26.
 */

public class CheckStoreDeviceActivity extends BaseActivity {
    /**
     * @param context
     * @param checkId    盘点单id
     * @param drawerName 货架名
     */
    public static void lauch(Context context, long checkId, String drawerName) {
        Bundle bundle = new Bundle();
        bundle.putLong("checkId", checkId);
        bundle.putString("drawerName", drawerName);
        ActivityStackUtils.lauch(context, CheckStoreDeviceActivity.class, bundle);
    }

    @BindView(R.id.pcCheckChart)
    PieChart pcCheckChart;
    @BindView(R.id.btnCheckAuto)
    Button btnCheckAuto;
    @BindView(R.id.btnCheckManual)
    Button btnCheckManual;
    /**
     * 盘点单id
     */
    private long mCheckId;
    /**
     * 货架名
     */
    private String mDrawerName;
    /**
     * 盘点单明细列表
     */
    private List<CheckStroeDeviceItem> mlstCheckStroeDeviceItem;
    private int mUnAutoCheckCount = 0;
    private CheckRepository checkRepository;
    /**
     * 是否正在更新自动盘点的数据
     */
    private boolean isUpdate = false;
    private Disposable mAutoScan;

    @Override
    protected int getLayout() {
        return R.layout.check_activity_store_device;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("库存盘点");
        initChart();
    }

    @Override
    protected void initEventAndData() {
        mDrawerName = mBundle.getString("drawerName");
        mCheckId = mBundle.getLong("checkId");
        checkRepository = new CheckRepository(this);
        mlstCheckStroeDeviceItem = new ArrayList<>();
        mAutoScan = RxBus.getDefault().toObservable(RfidScanEvent.class)
                .compose(this.<RfidScanEvent>bindToLifecycle())
                .subscribe(new Consumer<RfidScanEvent>() {
                    @Override
                    public void accept(RfidScanEvent rfidScanEvent) throws Exception {
                        if (mUnAutoCheckCount > 0) {
                            if (!rfidScanEvent.getLstTagDatas().isEmpty() && !isUpdate) {
                                isUpdate = true;
                                //搜索得到的rfid，并且是盘点单上的rfid
                                StringBuffer scanRfid = new StringBuffer();
                                for (Tag_Data td :
                                        rfidScanEvent.getLstTagDatas()) {
                                    scanRfid.append(",").append(td.epc);
                                }
                                LogUtils.w(" scanRfid:" + scanRfid.toString());
                                //更新自动盘点
                                if (scanRfid.length() > 0) {
                                    mobileAutoCheckStoreRfid(scanRfid.substring(1));
                                } else {
                                    isUpdate = false;
                                }
                            }
                        } else {
                            RfidUtils.stop();
                            btnCheckAuto.setText(R.string.check_btn_stop_auto_text);
                            ToastUtils.showLong(mDrawerName + getString(R.string.check_auto_finish));
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新盘点数据
        mobileCountCheckStoreDeviceItemDetail();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (btnCheckAuto.getText().toString().trim().equals(getString(R.string.check_btn_stop_auto_text))) {
            RfidUtils.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (btnCheckAuto.getText().toString().trim().equals(getString(R.string.check_btn_stop_auto_text))) {
            RfidUtils.stop();
        }
        if (mAutoScan != null && !mAutoScan.isDisposed()) {
            mAutoScan.dispose();
        }
        isUpdate = false;
    }

    @Override
    protected void onDestroy() {
        if (btnCheckAuto.getText().toString().trim().equals(getString(R.string.check_btn_stop_auto_text))) {
            RfidUtils.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (btnCheckAuto.getText().toString().trim().equals(getString(R.string.check_btn_stop_auto_text))) {
            RfidUtils.stop();
        }
        super.onBackPressed();
    }

    private void mobileCountCheckStoreDeviceItemDetail() {
        checkRepository.mobileCountCheckStoreDeviceItemDetail(mCheckId, mDrawerName)
                .subscribe(new ResponseObserver<List<CheckStroeDeviceItem>>() {
                    @Override
                    public void handleData(List<CheckStroeDeviceItem> data) {
                        mlstCheckStroeDeviceItem.clear();
                        mlstCheckStroeDeviceItem.addAll(data);
                        bindChartData();
                    }
                });


    }

    /**
     * 初始化图表
     */
    private void initChart() {
        //使用百分比显示
        pcCheckChart.setUsePercentValues(true);
        //是否接受点击事件
        pcCheckChart.setTouchEnabled(false);
        //去除描述
        pcCheckChart.getDescription().setEnabled(false);
    }

    /**
     * 绑定图表的数据
     */
    private void bindChartData() {
        int finishCheckCount = 0;
        int autoCheckCount = 0;
        int unCheckCount = 0;
        int unCheckItemCount=0;
        for (CheckStroeDeviceItem item : mlstCheckStroeDeviceItem) {
            if (item.getIsCheck().equals("1")) {
                finishCheckCount++;
            } else {
                unCheckItemCount++;
                if (item.isAuto()) {
                    autoCheckCount += item.getAutoCount();
                } else {
                    unCheckCount++;
                }
            }
        }
        mUnAutoCheckCount = autoCheckCount;
        //中间显示的文字
        pcCheckChart.setCenterText("已盘点" + finishCheckCount + "件\n" +
                "未盘点" + unCheckItemCount + "件");
        // 设置饼图各个区域颜色
        List<Integer> colors = new ArrayList<>();
        //设置每个饼图显示的文字和百分比
        List<PieEntry> valueList = new ArrayList<>();
        if (finishCheckCount > 0) {
            colors.add(Color.BLUE);
            valueList.add(new PieEntry(finishCheckCount, "已盘点(" + finishCheckCount + ")"));
        }
        //可自动盘点数
        if (autoCheckCount > 0) {
            colors.add(Color.RED);
            valueList.add(new PieEntry(autoCheckCount, "可自动盘点标签(" + autoCheckCount + ")"));
        } else {
            //如果没有可自动盘点的数量那就不显示按钮
            if (btnCheckAuto.getText().toString().equals(getString(R.string.check_btn_stop_auto_text))) {
                RfidUtils.stop();
                ToastUtils.showLong(mDrawerName + getString(R.string.check_auto_finish));
            }
            btnCheckAuto.setVisibility(View.INVISIBLE);
        }
        //需人工盘点数
        if (unCheckCount > 0) {
            colors.add(Color.GRAY);
            valueList.add(new PieEntry(unCheckCount, "需人工盘点(" + unCheckCount + ")"));
        }
        //没有需盘点的数量，按钮不显示
        if (unCheckItemCount == 0) {
            btnCheckManual.setVisibility(View.INVISIBLE);
        }

        //显示在比例图上
        PieDataSet dataSet = new PieDataSet(valueList, "");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(3f);
        // 部分区域被选中时多出的长度
        dataSet.setSelectionShift(5f);


        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        //设置以百分比显示
        data.setValueFormatter(new PercentFormatter());
        //区域文字的大小
        data.setValueTextSize(11f);
        //设置区域文字的颜色
        data.setValueTextColor(Color.WHITE);
        //设置区域文字的字体
        data.setValueTypeface(Typeface.DEFAULT);
        //装载数据
        pcCheckChart.setData(data);
        //渲染图表
        pcCheckChart.highlightValues(null);
        pcCheckChart.invalidate();
    }

    /**
     * 自动盘点
     */
    @OnClick(R.id.btnCheckAuto)
    public void autoCheckDevice() {
        if (btnCheckAuto.getText().toString().trim().equals(getString(R.string.check_btn_auto_text))) {
            RfidUtils.start();
            btnCheckAuto.setText(R.string.check_btn_stop_auto_text);
        } else {
            btnCheckAuto.setText(R.string.check_btn_auto_text);
            RfidUtils.stop();
        }
    }

    /**
     * 按压扫描键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 134) {
            autoCheckDevice();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 扫描枪自动盘点更新扫描的rifdj进行自动盘点
     */
    public void mobileAutoCheckStoreRfid(String rfids) {
        checkRepository.mobileAutoCheckStoreRfid(mCheckId, rfids)
                .subscribe(new ResponseObserver<String>() {
                    @Override
                    public void handleData(String data) {
                        mobileCountCheckStoreDeviceItemDetail();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        isUpdate = false;

                    }
                });

    }

    /**
     * 人工盘点
     */
    @OnClick(R.id.btnCheckManual)
    public void manualCheckDevice() {
        ManualCheckDeviceListActivity.lauch(mContext, mCheckId, mDrawerName);
    }

}
