package com.xmrbi.warehouse.base;

import android.os.Environment;

import com.xmrbi.warehouse.R;

import java.io.File;
import java.io.FileFilter;

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
//    public static String SPEECHCONSTANT_APPID = "5ad7567b";

    public static class DB {
        public static String DB_NAME = "WAREHOURE_DB";
    }

    public static class SP {
        /**
         * 设置的SharedPreferences的名字
         */
        public static String SP_NAME = "WAREHOURE_SETTING";
        /**
         * 是否设置仓库信息
         */
        public static String SP_IS_SETTING = "is_setting";
        /**
         * 是否是新版上位机
         */
        public final static String SP_IS_NEW = "is_new";
        public final static String SP_SERVER_IP = "server_ip";
        public final static String SP_SERVER_PORT = "server_port";
        public final static String SP_OA_IP = "oa_ip";
    }

    public static class Crash {
        /**
         * 崩溃文件的地址
         */
        public static String CRASH_DIR = SD_PATH + File.separator + "gmms" + File.separator + "errorFile";

    }

    public static class Http {
        /**
         * 超时时间
         */
        public static final int DEFAULT_TIMEOUT = 30;

//        public static String SERVER_IP = "192.168.4.44";
        /**
         * 服务器地址（正式）
         */
        public static String SERVER_IP = "172.20.60.40";
        //厦门西地址
//        public static  String SERVER_IP="192.168.192.40";
        /**
         * 服务器端口
         */
//        public static String SERVER_PORT = "8787";
        public static String SERVER_PORT = "8280";
        /**
         * gmms地址
         */
//        public static  String SERVER_GMMS="http://192.168.0.110:8844/";
//        public static String SERVER_GMMS = "http://192.168.4.44:8844/";
        //正式地址(厦门西)
//        public static  String SERVER_GMMS="http://192.168.192.40:8280/";
        //正式环境
        public static  String SERVER_GMMS = "http://172.20.60.40:8280/";
        public static final boolean IS_IP_ADDRESS = true;
        /**
         * apk的web地址
         */
        public static String UPDATE_APK_ADDRESS = SERVER_GMMS + "gmms/files/wareHouse.apk";
        /**
         * 判断apk是否更新的web文件
         */
        public static String UPDATE_APK_UPDATE_FILE = SERVER_GMMS + "gmms/updateRfid.xml";
        /**
         * apk本地下载地址目录（创建）
         */
        public static final String UPDATE_APK_FILE_ADDRESS_DIR = SD_PATH + File.separator + "gmms" + File.separator + "apk";
        /**
         * apk本地下载地址
         */
        public static final String UPDATE_APK_FILE_ADDRESS = UPDATE_APK_FILE_ADDRESS_DIR + File.separator + "wareHouse.apk";
    }


    /**
     * 启动页风格
     */
    public static int WELCOME_STYLE = R.style.WelcomeStart;
}
