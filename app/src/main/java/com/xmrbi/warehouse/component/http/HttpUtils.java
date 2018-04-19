package com.xmrbi.warehouse.component.http;

import com.blankj.utilcode.util.SPUtils;
import com.xmrbi.warehouse.base.Config;

/**
 * Created by wzn on 2018/4/16.
 */

public class HttpUtils {
    public static String getBaseUrl() {
        if (Config.Http.IS_IP_ADDRESS) {
            return "http://"+Config.Http.SERVER_IP + ":" + Config.Http.SERVER_PORT + "/";
        } else {
            return Config.Http.SERVER_IP + "/";
        }
    }

    /**
     * 重置服务器地址
     */
    public static void resetServerAddress(){
        Config.Http.SERVER_IP = SPUtils.getInstance(Config.SP.SP_NAME).getString(Config.SP.SP_SERVER_IP);
        Config.Http.SERVER_PORT = SPUtils.getInstance(Config.SP.SP_NAME).getString(Config.SP.SP_SERVER_PORT);
    }
}
