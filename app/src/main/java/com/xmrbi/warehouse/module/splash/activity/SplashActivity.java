package com.xmrbi.warehouse.module.splash.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.HttpUtils;
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

        Observable.timer(3, TimeUnit.SECONDS) // thanks to XieEDeHeiShou
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
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
}
