package com.xmrbi.warehouse.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.xmrbi.warehouse.base.Config;

/**
 * Created by wzn on 2018/3/29.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Config.welcome_style);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.startActivity(SplashActivity.this, MainActivity.class);
                finish();
            }
        }, 3000);

    }
}
