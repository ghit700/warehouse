package com.xmrbi.warehouse.component.http;

import com.xmrbi.warehouse.base.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.xmrbi.warehouse.base.Config.Http.DEFAULT_TIMEOUT;

/**
 * Created by wzn on 2018/4/17.
 */

public class OkHttpHepler {
    private static OkHttpClient mClient;

    /**
     * 获取一个默认的clinet
     * @return
     */
    public static OkHttpClient getClient() {
        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            if (Config.IS_OPEN_LOG) {
                builder.addInterceptor(new HttpLoggingInterceptor());
            }
            mClient = builder.build();
        }
        return mClient;
    }

    /**
     * 获取一个build进行配置
     *
     * @return
     */
    public OkHttpClient.Builder getNewClient() {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (Config.IS_OPEN_LOG) {
            builder.addInterceptor(new HttpLoggingInterceptor());
        }
        return builder;
    }
}
