package com.xmrbi.warehouse.application;

import android.app.Application;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.xmrbi.warehouse.base.Config;


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
        LogUtils.getConfig().setLogSwitch(Config.isOpenLog);
        //崩溃日志
//        CrashUtils.init(Config.CrashDir);

    }
}
