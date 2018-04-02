package com.xmrbi.warehouse.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xmrbi.warehouse.utils.ActivityStackUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by wzn on 2018/3/29.
 */

public abstract  class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    protected Unbinder mUnbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder= ButterKnife.bind(this);
        onViewCreated();
        mContext=this;
        ActivityStackUtils.addActivity(this);
        initEventAndData();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.removeActivity(this);
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
    }

    protected abstract int getLayout();

    protected abstract void onViewCreated();

    protected abstract void initEventAndData();
}
