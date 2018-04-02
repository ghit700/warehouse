package com.xmrbi.warehouse.base;

import android.os.Environment;

import com.xmrbi.warehouse.R;

import java.io.File;

/**
 * Created by wzn on 2018/3/29.
 */

public class Config {
    /**
     * SD卡的路径
     */
    public static String SDPath = Environment.getExternalStorageDirectory()
            .getPath();
    /**
     * log是否显示
     */
    public static boolean isOpenLog=true;
    /**
     * 设置的SharedPreferences的名字
     */
    public static String settingSpName="WAREHOURE_SETTING";
    /**
     * 崩溃文件的地址
     */
    public static String CrashDir= SDPath+ File.separator+"gmms"+File.separator+"errorFile";
    /**
     * 启动页风格
     */
    public static int welcome_style= R.style.WelcomeStart;
}
