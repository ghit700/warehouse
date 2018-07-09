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
    public MaterialDialog mDialog;
    private Context mContext;
    /**
     * 是否显示错误信息的toast
     */
    protected boolean isShowErrorToast = true;
    /**
     * 是否显示dailog
     */
    protected boolean isShowDialog = true;
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
        mDialog = setDialog();
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
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    protected void showLoadingProgress() {
        if (isShowDialog && mContext != null) {
            if (mDialog == null) {
                if (isPost) {
                    mDialog = new MaterialDialog.Builder(mContext)
                            .title(R.string.main_progress_post_title)
                            .content(R.string.main_progress_content)
                            .progress(true, 0)
                            .progressIndeterminateStyle(false)
                            .show();
                } else {
                    mDialog = new MaterialDialog.Builder(mContext)
                            .title(R.string.main_progress_title)
                            .content(R.string.main_progress_content)
                            .progress(true, 0)
                            .progressIndeterminateStyle(false)
                            .show();
                }
            } else {
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
        }
    }

    public MaterialDialog getmDialog() {
        return mDialog;
    }

    /**
     * 设置dialog的样式
     */
    protected MaterialDialog setDialog() {
        return null;
    }
}
