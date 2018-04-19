package com.xmrbi.warehouse.module.deliver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wzn on 2018/4/19.
 */

public class DevicePostCardActivity extends BaseActivity {
    @BindView(R.id.llDevicePostCardWarehouse)
    LinearLayout llDevicePostCardWarehouse;
    @BindView(R.id.llDevicePostCardManage)
    LinearLayout llDevicePostCardManage;
    @BindView(R.id.llDevicePostCardDeliver)
    LinearLayout llDevicePostCardDeliver;

    public static void lauch(Context context,  Long storeHouseId) {
        Bundle bundle = new Bundle();
        bundle.putLong("storeHouseId", storeHouseId);
        Intent intent = new Intent(context, DevicePostCardActivity.class);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.device_post_card_activity;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("设备贴卡");
    }

    @Override
    protected void initEventAndData() {

    }


    @OnClick({R.id.llDevicePostCardWarehouse, R.id.llDevicePostCardManage, R.id.llDevicePostCardDeliver})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDevicePostCardWarehouse:
                break;
            case R.id.llDevicePostCardManage:
                break;
            case R.id.llDevicePostCardDeliver:
                break;
        }
    }
}
