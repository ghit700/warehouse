package com.xmrbi.warehouse.module.main.activity;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.module.san.activity.ScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tvMainWarehouseName)
    TextView tvMainWarehouseName;
    @BindView(R.id.rlMainWarehouse)
    RelativeLayout rlMainWarehouse;
    @BindView(R.id.llMainDeliverGoods)
    LinearLayout llMainDeliverGoods;
    @BindView(R.id.llMainPickGoods)
    LinearLayout llMainPickGoods;
    @BindView(R.id.llMainCheckGoods)
    LinearLayout llMainCheckGoods;
    @BindView(R.id.llMainSearchGoods)
    LinearLayout llMainSearchGoods;

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected void initEventAndData() {
        initCrash();
    }

    @OnClick({R.id.rlMainWarehouse, R.id.llMainDeliverGoods, R.id.llMainPickGoods, R.id.llMainCheckGoods, R.id.llMainSearchGoods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlMainWarehouse:
                break;
            case R.id.llMainDeliverGoods:
                lauch(ScanActivity.class,"送货扫码");
                break;
            case R.id.llMainPickGoods:
                lauch(ScanActivity.class,"领料扫码");
                break;
            case R.id.llMainCheckGoods:
                lauch(ScanActivity.class,"盘点扫码");
                break;
            case R.id.llMainSearchGoods:
                break;
        }
    }

    /**
     * 初始化崩溃文件日志以便上传回溯崩溃
     */
    private void initCrash() {
        getRxPermissionsInstance().request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean granted) throws Exception {
                if (granted) {
                    //崩溃日志
                    CrashUtils.init(Config.Crash.CRASH_DIR);
                } else {
                    ToastUtils.showLong(R.string.permissions_fail);
                }
            }
        });
    }
}
