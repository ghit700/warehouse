package com.xmrbi.warehouse.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by wzn on 2018/3/29.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    protected Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        ActivityStackUtils.addActivity(this);
        onViewCreated();
        initEventAndData();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setToolBar(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.titleBaseAction);
        tvTitle.setText(title);
        findViewById(R.id.leftBaseActionbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackUtils.removeActivity(this);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected abstract int getLayout();

    protected abstract void onViewCreated();

    protected abstract void initEventAndData();
    /******utils******/
    /**
     * 初始化获取权限的类
     *
     * @return
     */
    protected RxPermissions getRxPermissionsInstance() {
        return new RxPermissions(mContext);
    }

    ;
}
