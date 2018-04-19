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
    /**
     * 科大讯飞的appid
     */
    public static String SPEECHCONSTANT_APPID="5ad7567b";
    public static class DB{
        public static String DB_NAME="WAREHOURE_DB";
    }

    public static class SP {
        /**
         * 设置的SharedPreferences的名字
         */
        public static String SP_NAME = "WAREHOURE_SETTING";
        /**
         * 是否设置仓库信息
         */
        public static String SP_IS_SETTING ="is_setting";
        /**
         * 是否是新版手持枪
         */
        public final static String SP_IS_NEW ="is_new";
        public final static String SP_SERVER_IP ="server_ip";
        public final static String SP_SERVER_PORT ="server_port";
    }
    public static class Crash{
        /**
         * 崩溃文件的地址
         */
        public static String CRASH_DIR = SD_PATH + File.separator + "gmms" + File.separator + "errorFile";

    }
    public static class Http{
        public static final int DEFAULT_TIMEOUT=100;
        /**
         * 服务器地址
         */
        public static  String SERVER_IP="172.16.53.225";
        /**
         * 服务器端口
         */
        public static  String SERVER_PORT="8080";
        /**
         * gmms地址
         */
        public static final String SERVER_GMMS="http://172.16.53.226:8080/gmms/";
        public static final boolean IS_IP_ADDRESS=true;
    }


    /**
     * 启动页风格
     */
    public static int WELCOME_STYLE = R.style.WelcomeStart;
}
