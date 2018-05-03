package com.xmrbi.warehouse.module.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.module.check.adapter.ManualCheckDeviceAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

import static com.iflytek.sunflower.config.a.s;

/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceActivity extends BaseActivity {
    public static void lauch(Context context, long checkId, String drawerName, RfidNewCheckingEntity.DataBean item) {
        Bundle bundle = new Bundle();
        bundle.putLong("checkId", checkId);
        bundle.putSerializable("item", item);
        bundle.putString("drawerName", drawerName);
        ActivityStackUtils.lauch(context, ManualCheckDeviceActivity.class, bundle);
    }

    @BindView(R.id.tvItemCheckDevicePlace)
    TextView tvItemCheckDevicePlace;
    @BindView(R.id.tvItemCheckDeviceName)
    TextView tvItemCheckDeviceName;
    @BindView(R.id.tvItemCheckDeviceNum)
    TextView tvItemCheckDeviceNum;
    @BindView(R.id.tvItemCheckDeviceBrand)
    TextView tvItemCheckDeviceBrand;
    @BindView(R.id.tvItemCheckDeviceModel)
    TextView tvItemCheckDeviceModel;
    @BindView(R.id.listCheckRfidCode)
    RecyclerView listCheckRfidCode;

    private ManualCheckDeviceAdapter mAdapter;
    private List<RfidNewCheckingEntity.DataBean> mLstCheckStroeDeviceItems;
    private Long mCheckId;
    private RfidNewCheckingEntity.DataBean mItem;
    private String mDrawerName;
    private CheckRepository checkRepository;

    @Override
    protected int getLayout() {
        return R.layout.check_activity_manual_check_device;
    }

    @Override
    protected void onViewCreated() {
        listCheckRfidCode.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLstCheckStroeDeviceItems = new ArrayList<>();
        mAdapter = new ManualCheckDeviceAdapter(mLstCheckStroeDeviceItems);
        listCheckRfidCode.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                new MaterialDialog.Builder(mContext)
                        .content(R.string.check_manual_dailog_content)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                RfidNewCheckingEntity.DataBean item = mLstCheckStroeDeviceItems.get(position);
                                manualCheckStoreDeviceItemOrRfid(item.getRfid(), item.getCsriID(), item.getBookValue(), position);
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();

            }
        });
        mItem = (RfidNewCheckingEntity.DataBean) mBundle.getSerializable("item");
        tvItemCheckDeviceBrand.setText(mItem.getBrand());
        tvItemCheckDeviceModel.setText(mItem.getModel());
        tvItemCheckDeviceName.setText(mItem.getName());
        tvItemCheckDevicePlace.setText(mItem.getDrawerName());
        if (!StringUtils.isEmpty(mItem.getBookValues())) {
            String[] bookValues = mItem.getBookValues().split(",");
            Double bookValue = 0d;
            for (String book :
                    bookValues) {
                bookValue += Double.parseDouble(book);
            }
            tvItemCheckDeviceNum.setText(bookValue.toString());
        } else {
            tvItemCheckDeviceNum.setText(mItem.getBookValue().toString());
        }
    }

    @Override
    protected void initEventAndData() {
        mDrawerName = mBundle.getString("drawerName");
        mCheckId = mBundle.getLong("checkId");
        checkRepository = new CheckRepository(this);
        downloadCheckStoreDeviceItemOrRfidByDrawer();
    }


    private void downloadCheckStoreDeviceItemOrRfidByDrawer() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("isMerge", "false");
        queryMap.put("deviceId", String.valueOf(mItem.getDeviceId()));
        checkRepository.downloadCheckStoreDeviceItemOrRfidByDrawer(mCheckId, mDrawerName, false, queryMap)
                .subscribe(new BaseObserver<RfidNewCheckingEntity>(mContext) {
                    @Override
                    public void onNext(@NonNull RfidNewCheckingEntity entity) {
                        if (entity.isSuccess()) {
                            if (entity.getData() != null) {
                                mLstCheckStroeDeviceItems.addAll(entity.getData());
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if (StringUtils.isEmpty(entity.getErrorMsg())) {
                                ToastUtils.showLong("查询失败");
                            } else {
                                ToastUtils.showLong(entity.getErrorMsg());
                            }
                        }
                    }

                });
    }

    /**
     * 盘点
     *
     * @param rfid
     * @param csriID
     * @param count
     */
    private void manualCheckStoreDeviceItemOrRfid(String rfid, String csriID, Double count, final int position) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("checkId", mCheckId.toString());
        queryMap.put("rfid", rfid);
        queryMap.put("count", count.toString());
        queryMap.put("id", StringUtils.isEmpty(csriID) ? "" : csriID);

        checkRepository.manualCheckStoreDeviceItemOrRfid(queryMap)
                .subscribe(new BaseObserver<String>(mContext, true) {
                    @Override
                    public void onNext(@NonNull String result) {
                        if (result.contains("成功")) {
                            //修改总数
                            double bookvalue = mLstCheckStroeDeviceItems.get(position).getBookValue();
                            double totalBookValue = Double.parseDouble(tvItemCheckDeviceNum.getText().toString().trim());
                            tvItemCheckDeviceNum.setText(String.valueOf(totalBookValue - bookvalue));
                            mAdapter.remove(position);
                            if (mLstCheckStroeDeviceItems.isEmpty()) {
                                finish();
                            }
                        }
                        ToastUtils.showLong(result);
                    }
                });
    }


}
