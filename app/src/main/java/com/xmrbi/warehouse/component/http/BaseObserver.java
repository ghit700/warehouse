package com.xmrbi.warehouse.component.http;


import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wzn on 2018/4/17.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    /**
     * 是否显示错误信息的toast
     */
    private boolean isShowErrorToast = true;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
//        if(NetworkUtils.isConnected()){
//            ToastUtils.showLong(R.string.no_connect);
//            showLoadingProgress();
//        }else{
//            d.dispose();
//        }
        showLoadingProgress();

    }




    @Override
    public void onNext(@NonNull T t) {
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtils.e("httpError", e.getMessage());
        //处理error
        e = ExceptionHandle.handleException(e);
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
        closeLoadingProgress();
    }

    protected void onError(ExceptionHandle.ResponeThrowable e) {
        if (isShowErrorToast) {
            ToastUtils.showLong(e.message);
        }
        closeLoadingProgress();

    }
    protected  void closeLoadingProgress(){}
    protected  void showLoadingProgress(){}

    public void setShowErrorToast(boolean showErrorToast) {
        isShowErrorToast = showErrorToast;
    }
}
