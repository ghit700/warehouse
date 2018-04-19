package com.xmrbi.warehouse.component.http;

import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.greendao.annotation.NotNull;

import io.reactivex.annotations.NonNull;

/**
 * 统一返回格式的统一判断（{"success":false,"data":"毫无数据"}）
 * Created by wzn on 2018/4/17.
 */

public abstract class ResponseObserver<T> extends BaseObserver<Response> {
    @Override
    public void onNext(@NonNull Response response) {
        super.onNext(response);
        if (!response.isSuccess()) {
            ToastUtils.showLong((String) response.getData());
        } else {
            handleData((T) response.getData());
        }
    }

    public abstract void handleData(@NotNull T data);
}
