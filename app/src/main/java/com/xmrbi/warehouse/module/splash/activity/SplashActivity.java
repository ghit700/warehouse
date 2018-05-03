package com.xmrbi.warehouse.module.splash.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.HttpUtils;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.module.main.activity.MainActivity;
import com.xmrbi.warehouse.module.setting.activity.SettingActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by wzn on 2018/3/29.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Config.WELCOME_STYLE);

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        initCrash();
                        if (SPUtils.getInstance(Config.SP.SP_NAME).getBoolean(Config.SP.SP_IS_SETTING)) {
                            HttpUtils.resetServerAddress();
                            ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
                        } else {
                            ActivityUtils.startActivity(SplashActivity.this, SettingActivity.class);
                        }
                        finish();
                    }
                });

    }

    /**
     * 初始化崩溃文件日志以便上传回溯崩溃
     */
    private void initCrash() {
        if (Build.VERSION.SDK_INT >= 23) {
            new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
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
        } else {
            CrashUtils.init(Config.Crash.CRASH_DIR);
        }
    }

}
