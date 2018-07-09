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
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.module.check.adapter.CheckStoreDeviceListAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wzn on 2018/4/26.
 */

public class CheckStoreDeviceListActivity extends BaseActivity {
    public static void lauch(Context context, long checkId) {
        Bundle bundle = new Bundle();
        bundle.putLong("checkId", checkId);
        ActivityStackUtils.lauch(context, CheckStoreDeviceListActivity.class, bundle);
    }

    @BindView(R.id.listCheckStoreDevice)
    RecyclerView listCheckStoreDevice;

    private List<RfidNewInventoryEntity> mLstEntities;
    private CheckStoreDeviceListAdapter mAdapter;
    /**
     * 盘点单id
     */
    private long mCheckId;
    private CheckRepository checkRepository;


    @Override
    protected int getLayout() {
        return R.layout.check_activity_store_device_list;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("库存盘点");
        listCheckStoreDevice.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLstEntities = new ArrayList<>();
        mAdapter = new CheckStoreDeviceListAdapter(mLstEntities);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RfidNewInventoryEntity entity = mLstEntities.get(position);
                CheckStoreDeviceActivity.lauch(mContext, mCheckId, entity.getDrawerName());
            }
        });
        listCheckStoreDevice.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        mCheckId = mBundle.getLong("checkId");
        checkRepository = new CheckRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回到当前页面刷新数据
        countCheckStoreDeviceItemOrRfid();
    }

    public void countCheckStoreDeviceItemOrRfid() {
        checkRepository.mobileCountCheckStoreDeviceItem(mCheckId)
                .subscribe(new ResponseObserver<List<RfidNewInventoryEntity>>(mContext) {
                    @Override
                    public void handleData(@NotNull List<RfidNewInventoryEntity> data) {
                        if(data!=null&&!data.isEmpty()){
                            mLstEntities.clear();
                            mLstEntities.addAll(data);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
