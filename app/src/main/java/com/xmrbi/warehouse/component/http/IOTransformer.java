package com.xmrbi.warehouse.component.http;


import android.content.Context;

import com.xmrbi.warehouse.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wzn on 2018/4/17.
 */

public class IOTransformer<T> implements ObservableTransformer<T, T> {
    private BaseActivity mBaseActivity;

    public IOTransformer(BaseActivity activity) {
        mBaseActivity = activity;
    }

    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mBaseActivity.<T>bindToLifecycle());
    }
}
