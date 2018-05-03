package com.xmrbi.warehouse.module.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;
import com.xmrbi.warehouse.data.repository.CheckRepository;
import com.xmrbi.warehouse.module.check.adapter.ManualCheckDeviceListAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

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
    private List<RfidNewCheckingEntity.DataBean> mlstCheckStoreDeviceItems;
    private long mCheckId;
    private String mDrawerName;
    private CheckRepository checkRepository;

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
        checkRepository = new CheckRepository(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloadCheckStoreDeviceItemOrRfidByDrawer();
    }

    private void downloadCheckStoreDeviceItemOrRfidByDrawer() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("isMerge", "true");
        checkRepository.downloadCheckStoreDeviceItemOrRfidByDrawer(mCheckId, mDrawerName, false, queryMap)
                .subscribe(new BaseObserver<RfidNewCheckingEntity>(mContext) {
                    @Override
                    public void onNext(@NonNull RfidNewCheckingEntity entity) {
                        if (entity.isSuccess()) {
                            if (entity.getData() != null) {
                                if (entity.getData().isEmpty()) {
                                    finish();
                                }
                                mlstCheckStoreDeviceItems.clear();
                                mlstCheckStoreDeviceItems.addAll(entity.getData());
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if (StringUtils.isEmpty(entity.getErrorMsg())) {
                                ToastUtils.showLong("查询失败");
                            } else {
                                ToastUtils.showLong(entity.getErrorMsg());
                            }
                        }
                    }

                });
    }

}
