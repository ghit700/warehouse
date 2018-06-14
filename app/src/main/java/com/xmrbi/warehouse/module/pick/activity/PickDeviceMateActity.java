package com.xmrbi.warehouse.module.pick.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.speedata.libuhf.bean.Tag_Data;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;
import com.xmrbi.warehouse.data.repository.PickRepository;
import com.xmrbi.warehouse.event.RfidScanEvent;
import com.xmrbi.warehouse.event.UpdatePickListDetailEvent;
import com.xmrbi.warehouse.module.pick.adapter.PickDeviceRfidAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;
import com.xmrbi.warehouse.utils.RxBus;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.iflytek.sunflower.config.a.m;


/**
 * Created by wzn on 2018/4/26.
 */

public class PickDeviceMateActity extends BaseActivity {

    public static void lauch(Context context, PickListDetail detail, long pickListId, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("PickListDetail", detail);
        bundle.putLong("pickListId", pickListId);
        bundle.putInt("position", position);
        ActivityStackUtils.lauch(context, PickDeviceMateActity.class, bundle);
    }

    @BindView(R.id.tvDeviceMateName)
    TextView tvDeviceMateName;
    @BindView(R.id.tvDeviceMateNum)
    TextView tvDeviceMateNum;
    @BindView(R.id.tvDeviceMateBrand)
    TextView tvDeviceMateBrand;
    @BindView(R.id.tvDeviceMateModel)
    TextView tvDeviceMateModel;
    @BindView(R.id.tvDeviceMatePlace)
    TextView tvDeviceMatePlace;
    @BindView(R.id.listPickDeviceMate)
    RecyclerView listPickDeviceMate;

    /**
     * 设备
     */
    private PickListDetail mPickListDetail;
    /**
     * 领料单id
     */
    private long mPickListId;
    /**
     * 点击的列表位置（为了更新列表用）
     */
    private int mPosition;
    private List<PickListDetail> mlstDetails;
    private PickDeviceRfidAdapter mAdapter;
    private PickRepository pickRepository;
    /**
     * 是否扫描
     */
    private boolean isScan;
    private MaterialDialog mScanDialog;


    @Override
    protected int getLayout() {
        return R.layout.pick_activity_device_mate;
    }

    @Override
    protected void onViewCreated() {
        listPickDeviceMate.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mlstDetails = new ArrayList<>();
        mAdapter = new PickDeviceRfidAdapter(mlstDetails);
        listPickDeviceMate.setAdapter(mAdapter);
        mPickListDetail = (PickListDetail) mBundle.getSerializable("PickListDetail");
        tvDeviceMateBrand.setText(mPickListDetail.getBrand());
        tvDeviceMateModel.setText(mPickListDetail.getModel());
        tvDeviceMateName.setText(mPickListDetail.getName());
        tvDeviceMateNum.setText(mPickListDetail.getNum());
        tvDeviceMatePlace.setText(mPickListDetail.getStorage());
    }

    @Override
    protected void initEventAndData() {
        isScan = false;
        mPickListId = mBundle.getLong("pickListId");
        mPosition = mBundle.getInt("position");
        pickRepository = new PickRepository(this);
        getPickListDetail();
        Disposable d = RxBus.getDefault().toObservable(RfidScanEvent.class)
                .compose(this.<RfidScanEvent>bindToLifecycle())
                .subscribe(new Consumer<RfidScanEvent>() {
                    @Override
                    public void accept(RfidScanEvent rfidScanEvent) throws Exception {
                        RfidUtils.stop();
                        List<Tag_Data> lstDatas = rfidScanEvent.getLstTagDatas();
                        StringBuffer lstErrorEPCs=new StringBuffer();
                        for (Tag_Data td : lstDatas) {
                            //符合规定且不曾扫描过的rfid，上传到领料单rfid中
                            if (RfidUtils.isAccord(td.epc)&&mlstDetails.size()>0 && (StringUtils.isEmpty(mPickListDetail.getRfid()) || !mPickListDetail.getRfid().contains(td.epc))) {
                                for (PickListDetail detail : mlstDetails) {
                                    if (detail.getRfidTag().equals(td.epc)) {
                                        updatePickListRfid(detail);
                                    }
                                }
                            } else {
                                lstErrorEPCs.append(",").append(td.epc);
                            }
                        }
                        //显示错签信息
                        if(lstErrorEPCs.length()>0){
                            ToastUtils.showLong(lstErrorEPCs.substring(1)+"标签错误");
//                            new MaterialDialog.Builder(mContext)
//                                    .title("Rfid错误")
//                                    .items(lstErrorEPCs)
//                                    .positiveText(android.R.string.cancel)
//                                    .show();
                        }
                        isScan = false;
                        if (mScanDialog != null) {
                            mScanDialog.dismiss();
                        }

                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        RfidUtils.stop();
    }

    /**
     * 获取领料单明细
     */
    public void getPickListDetail() {
        pickRepository.getPickListDetail(mPickListDetail.getDeviceId(), mPickListId)
                .subscribe(new ResponseObserver<List<PickListDetail>>() {
                    @Override
                    public void handleData(@NotNull List<PickListDetail> data) {
                        if (data != null && !data.isEmpty()) {
                            mlstDetails.addAll(data);
                            //rfid排序
                            Collections.sort(mlstDetails, new Comparator<PickListDetail>() {
                                @Override
                                public int compare(PickListDetail o1, PickListDetail o2) {
                                    if (!StringUtils.isEmpty(o1.getRfidTag()) && !StringUtils.isEmpty(o2.getRfidTag())) {
                                        return o1.getRfidTag().compareTo(o2.getRfidTag());
                                    } else {
                                        return 0;
                                    }
                                }
                            });
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 更新领料情况
     */
    public void updatePickListRfid(final PickListDetail detail) {
        pickRepository.updatePickListRfid(detail.getRfidTag(), mPickListDetail.getDeviceId(), mPickListId)
                .subscribe(new BaseObserver<String>(mContext, true) {
                    @Override
                    public void onNext(@NonNull String result) {
                        if (result.equals("fail")) {
                            //防止重复上传没有更新到位
                            String rfid = !StringUtils.isEmpty(mPickListDetail.getRfid()) ? mPickListDetail.getRfid() + "," + detail.getRfidTag() : detail.getRfidTag();
                            mPickListDetail.setRfid(rfid);
                            detail.setRfid(mPickListDetail.getRfid());
                            ToastUtils.showLong("重复上传rfid");
                        } else if (result.equals("success")) {
                            String rfid = !StringUtils.isEmpty(mPickListDetail.getRfid()) ? mPickListDetail.getRfid() + "," + detail.getRfidTag() : detail.getRfidTag();
                            mPickListDetail.setRfid(rfid);
                            detail.setRfid(mPickListDetail.getRfid());
                            ToastUtils.showLong("上传成功");
                        } else {
                            ToastUtils.showLong(result);
                        }
                        mAdapter.notifyDataSetChanged();
                        String[] rfids = mPickListDetail.getRfid().split(",");
                        if (rfids.length == mlstDetails.size()) {
                            mPickListDetail.setMate("1");
                        }
                        RxBus.getDefault().post(new UpdatePickListDetailEvent(mPosition, mPickListDetail));
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mPickListDetail.getMate().equals("1")) {
            finish();
        } else {
            showWarningDialog();
        }
    }

    private void showWarningDialog() {
        new MaterialDialog.Builder(this)
                .content("还未匹配完，确定退出?")
                .positiveText(R.string.setting_agree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                        finish();
                    }
                })
                .negativeText(R.string.main_cancel)
                .show();
    }

    /**
     * 判断是否按压扫描按键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 134) {
            if (!isScan) {
                if (RfidUtils.getIuhfService() != null) {
                    isScan = true;
                    RfidUtils.start();
                    if (mScanDialog == null) {
                        mScanDialog = new MaterialDialog.Builder(mContext)
                                .content(R.string.post_rfid_card_scaning)
                                .progress(true, 0)
                                .progressIndeterminateStyle(false)
                                .show();
                    } else {
                        mScanDialog.show();
                    }
                } else {
                    ToastUtils.showLong(R.string.post_rfid_card_initing);
                }
            } else {
                ToastUtils.showLong(R.string.post_rfid_card_scaning);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
