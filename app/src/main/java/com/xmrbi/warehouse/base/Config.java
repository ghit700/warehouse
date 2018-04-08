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
    public static String SD_PATH = Environment.getExternalStorageDirectory()
            .getPath();
    /**
     * log是否显示
     */
    public static boolean IS_OPEN_LOG = true;

    public static class SharedPreferences {
        /**
         * 设置的SharedPreferences的名字
         */
        public static String SETTING = "WAREHOURE_SETTING";
    }
    public static class Crash{

        /**
         * 崩溃文件的地址
         */
        public static String CRASH_DIR = SD_PATH + File.separator + "gmms" + File.separator + "errorFile";

    }


    /**
     * 启动页风格
     */
    public static int WELCOME_STYLE = R.style.WelcomeStart;
}
