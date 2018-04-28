package com.xmrbi.warehouse.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wzn on 2018/4/19.
 */

public abstract class BaseFragment extends RxFragment {
    protected Unbinder mUnbinder;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            initData();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (getView() == null) {
            view = inflater.inflate(getLayout(), container, false);
        } else {
            view = getView();
        }
        mUnbinder=ButterKnife.bind(this, view);
        onViewCreated();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
    /**
     * 创建的view
     *
     * @return
     */
    protected abstract int getLayout();

    protected abstract void   onViewCreated();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
