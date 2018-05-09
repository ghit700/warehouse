package com.xmrbi.warehouse.component.http;

/**
 * 统一解析的判断
 * Created by wzn on 2018/4/17.
 */

public class Response<T> {
    private boolean success;
    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
