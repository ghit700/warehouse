package com.xmrbi.warehouse.component.http;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by wzn on 2018/4/17.
 */

public class RetrofitHelper {
    /**
     * 获取一个默认服务
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> service) {
        return getInstance(service, HttpUtils.getBaseUrl(), OkHttpHepler.getClient());
    }

    /**
     * 自定义baseurl
     * @param service
     * @param baseUrl
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> service,String baseUrl) {
        return getInstance(service, baseUrl, OkHttpHepler.getClient());
    }

    /**
     * 获取一个自定义的服务
     * @param service
     * @param baseUrl
     * @param client
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> service, String baseUrl, OkHttpClient client) {
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(client)
                .build()
                .create(service);
    }
}
