package com.xmrbi.warehouse.component.http;


import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.application.WareHouseApplication;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wzn on 2018/4/17.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private MaterialDialog dialog;
    private Context mContext;
    /**
     * 是否显示错误信息的toast
     */
    private boolean isShowErrorToast = true;
    /**
     * 是否显示dailog
     */
    private boolean isShowDialog = true;
    /**
     * 是否是上传
     */
    private boolean isPost = false;

    public BaseObserver() {
    }

    public BaseObserver(Context context) {
        mContext = context;
    }

    /**
     * 是否为上传
     *
     * @param context
     * @param isPost
     */
    public BaseObserver(Context context, boolean isPost) {
        mContext = context;
        this.isPost = isPost;
    }

    public BaseObserver(Context mContext, boolean isShowErrorToast, boolean isShowDialog) {
        this.mContext = mContext;
        this.isShowErrorToast = isShowErrorToast;
        this.isShowDialog = isShowDialog;
    }

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

    protected void closeLoadingProgress() {
        if (dialog != null && mContext != null) {
            dialog.dismiss();
        }
    }

    protected void showLoadingProgress() {
        if (isShowDialog && mContext != null) {
            if (dialog == null) {
                if (isPost) {
                    dialog = new MaterialDialog.Builder(mContext)
                            .title(R.string.main_progress_post_title)
                            .content(R.string.main_progress_content)
                            .progress(true, 0)
                            .progressIndeterminateStyle(false)
                            .show();
                } else {
                    dialog = new MaterialDialog.Builder(mContext)
                            .title(R.string.main_progress_title)
                            .content(R.string.main_progress_content)
                            .progress(true, 0)
                            .progressIndeterminateStyle(false)
                            .show();
                }
            } else {
                dialog.show();
            }
        }
    }

    /**
     * 设置样式
     */
    protected void setDialog(MaterialDialog dialog) {
        this.dialog = dialog;
    }

}
