package com.xmrbi.warehouse.application;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.utils.ImageLoader;


/**
 * Created by wzn on 2018/3/29.
 */

public class WareHouseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化utils
        initUtils();
    }

    private void initUtils() {
        Utils.init(this);
        //log
        LogUtils.getConfig().setLogSwitch(Config.IS_OPEN_LOG);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.GuideClearMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.GuideClearMemory(this);
    }
}
