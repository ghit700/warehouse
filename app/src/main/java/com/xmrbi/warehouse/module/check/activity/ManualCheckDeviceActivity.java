package com.xmrbi.warehouse.module.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.data.entity.check.CheckStoreRfid;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.module.check.adapter.ManualCheckDeviceAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceActivity extends BaseActivity {
    public static void lauch(Context context, long checkId, String drawerName, CheckStroeDeviceItem item) {
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
    private List<CheckStoreRfid> mlstCheckStoreRfids;
    private Long mCheckId;
    private CheckStroeDeviceItem mItem;
    private String mDrawerName;
    private CheckRepository mCheckRepository;

    @Override
    protected int getLayout() {
        return R.layout.check_activity_manual_check_device;
    }

    @Override
    protected void onViewCreated() {
        listCheckRfidCode.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mlstCheckStoreRfids = new ArrayList<>();
        mAdapter = new ManualCheckDeviceAdapter(mlstCheckStoreRfids);
        listCheckRfidCode.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                new MaterialDialog.Builder(mContext)
                        .content(R.string.check_manual_dailog_content)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                CheckStoreRfid item = mlstCheckStoreRfids.get(position);
                                mobileSubmitCheckStroeDeviceItem(item.getCode(), item.getFactAmount(), position);
                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();

            }
        });
        mItem = (CheckStroeDeviceItem) mBundle.getSerializable("item");
        mDrawerName = mBundle.getString("drawerName");
        tvItemCheckDeviceBrand.setText(mItem.getBrandName());
        tvItemCheckDeviceModel.setText(mItem.getModelName());
        tvItemCheckDeviceName.setText(mItem.getComponentName());
        tvItemCheckDevicePlace.setText(mDrawerName);
        if(mItem.getFactNum()!=null&&mItem.getFactNum()>0){
            Double profitLoss=mItem.getFactNum()-mItem.getBookValue();
            tvItemCheckDeviceNum.setText(profitLoss.toString());

        }else{
            tvItemCheckDeviceNum.setText(mItem.getBookValue().toString());

        }
    }

    @Override
    protected void initEventAndData() {
        mCheckId = mBundle.getLong("checkId");
        mCheckRepository = new CheckRepository(this);
        mobileUnCheckStoreDeviceItemDetail();
    }


    private void mobileUnCheckStoreDeviceItemDetail() {
        mCheckRepository.mobileUnCheckStoreDeviceItemDetail(mItem.getId())
                .subscribe(new ResponseObserver<CheckStroeDeviceItem>(this) {
                    @Override
                    public void handleData(CheckStroeDeviceItem data) {
                        if (data.getLstCheckStoreRfids().size() > 0) {
                            mlstCheckStoreRfids.addAll(data.getLstCheckStoreRfids());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            CheckStoreRfid csr = new CheckStoreRfid();
                            csr.setAmount(mItem.getBookValue());
                            csr.setFactAmount(mItem.getBookValue());
                            mlstCheckStoreRfids.add(csr);
                        }
                    }
                });


    }

    /**
     * 盘点
     *
     * @param rfid
     * @param factNum
     * @param position
     */
    private void mobileSubmitCheckStroeDeviceItem(String rfid, Double factNum, final int position) {
        mCheckRepository.mobileSubmitCheckStroeDeviceItem(mItem.getId(), factNum.intValue(), rfid)
                .subscribe(new ResponseObserver<String>(this,true) {
                    @Override
                    public void handleData(String data) {
                        if (data.contains("success")) {
                            double bookvalue = mlstCheckStoreRfids.get(position).getFactAmount();
                            double totalBookValue = Double.parseDouble(tvItemCheckDeviceNum.getText().toString().trim());
                            tvItemCheckDeviceNum.setText(String.valueOf(totalBookValue - bookvalue));
                            mAdapter.remove(position);
                            if (mlstCheckStoreRfids.isEmpty()) {
                                finish();
                            }
                        }
                    }
                });

    }


}
