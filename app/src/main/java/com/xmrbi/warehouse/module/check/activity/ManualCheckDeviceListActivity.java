package com.xmrbi.warehouse.module.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.module.check.adapter.ManualCheckDeviceListAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 需人工盘点设备列表
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceListActivity extends BaseActivity {
    public static void lauch(Context context, long checkId, String drawerName) {
        Bundle bundle = new Bundle();
        bundle.putLong("checkId", checkId);
        bundle.putString("drawerName", drawerName);
        ActivityStackUtils.lauch(context, ManualCheckDeviceListActivity.class, bundle);
    }

    @BindView(R.id.listCheckManualDeviceList)
    RecyclerView listCheckManualDeviceList;

    private ManualCheckDeviceListAdapter mAdapter;
    /**
     * 未盘点的设备列表
     */
    private List<CheckStroeDeviceItem> mlstCheckStoreDeviceItems;
    private long mCheckId;
    private String mDrawerName;
    private CheckRepository mCheckRepository;

    @Override
    protected int getLayout() {
        return R.layout.check_activity_manual_check_device_list;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("库存盘点");
        listCheckManualDeviceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mlstCheckStoreDeviceItems = new ArrayList<>();
        mAdapter = new ManualCheckDeviceListAdapter(mlstCheckStoreDeviceItems);
        listCheckManualDeviceList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ManualCheckDeviceActivity.lauch(mContext, mCheckId, mDrawerName, mlstCheckStoreDeviceItems.get(position));
            }
        });
//        mAdapter.setEmptyView(R.layout.empty_view);
    }

    @Override
    protected void initEventAndData() {
        mCheckId = mBundle.getLong("checkId");
        mDrawerName = mBundle.getString("drawerName");
        mCheckRepository = new CheckRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mobileUnCheckStoreDeviceItemList();
    }

    private void mobileUnCheckStoreDeviceItemList() {
        mCheckRepository.mobileUnCheckStoreDeviceItemList(mCheckId, mDrawerName)
                .subscribe(new ResponseObserver<List<CheckStroeDeviceItem>>(this) {
                    @Override
                    public void handleData(List<CheckStroeDeviceItem> data) {
                        if(data.size()==0){
                            finish();
                            return;
                        }
                        mlstCheckStoreDeviceItems.clear();
                        mlstCheckStoreDeviceItems.addAll(data);
                        mAdapter.notifyDataSetChanged();

                    }
                });

    }

}
