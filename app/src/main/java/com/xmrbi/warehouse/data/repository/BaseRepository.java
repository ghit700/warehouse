package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;

/**
 * Created by wzn on 2018/4/26.
 */

public class BaseRepository {
    protected BaseActivity mBaseActivity;

    public BaseRepository(BaseActivity mBaseActivity) {
        this.mBaseActivity = mBaseActivity;
    }
}
