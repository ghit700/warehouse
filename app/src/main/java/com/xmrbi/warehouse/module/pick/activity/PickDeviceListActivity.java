package com.xmrbi.warehouse.module.pick.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.data.entity.main.Useunit;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;
import com.xmrbi.warehouse.data.repository.MainRepository;
import com.xmrbi.warehouse.data.repository.PickRepository;
import com.xmrbi.warehouse.event.UpdatePickListDetailEvent;
import com.xmrbi.warehouse.module.pick.adapter.PickDeviceAdapter;
import com.xmrbi.warehouse.utils.ActivityStackUtils;
import com.xmrbi.warehouse.utils.RxBus;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static android.R.attr.data;


/**
 * Created by wzn on 2018/4/26.
 */

public class PickDeviceListActivity extends BaseActivity {
    public static void lauch(Context context, long pickListId) {
        Bundle bundle = new Bundle();
        bundle.putLong("pickListId", pickListId);
        ActivityStackUtils.lauch(context, PickDeviceListActivity.class, bundle);
    }

    @BindView(R.id.listPickDeviceList)
    RecyclerView listPickDeviceList;
    @BindView(R.id.srlPickDeviceList)
    SwipeRefreshLayout srlPickDeviceList;

    private PickDeviceAdapter mAdapter;
    private List<PickListDetail> mlstPickListDetails;
    private PickRepository pickRepository;
    private Long mPickListId;

    @Override
    protected int getLayout() {
        return R.layout.pick_activity_device_list;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("领料单领料");
        listPickDeviceList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mlstPickListDetails = new ArrayList<>();
        mAdapter = new PickDeviceAdapter(mlstPickListDetails);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PickDeviceMateActity.lauch(mContext, mlstPickListDetails.get(position), mPickListId, position);
            }
        });
        listPickDeviceList.setAdapter(mAdapter);
//        mAdapter.setEmptyView(R.layout.empty_view);
        srlPickDeviceList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlPickDeviceList.setRefreshing(false);
                getPickListById();
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mPickListId = mBundle.getLong("pickListId");
        pickRepository = new PickRepository(this);
        getPickListById();
        RxBus.getDefault().toObservable(UpdatePickListDetailEvent.class)
                .compose(this.<UpdatePickListDetailEvent>bindToLifecycle())
                .subscribe(new Consumer<UpdatePickListDetailEvent>() {
                    @Override
                    public void accept(UpdatePickListDetailEvent event) throws Exception {
                        mlstPickListDetails.set(event.getPosition(), event.getPickListDetail());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getPickListById() {
        pickRepository.getPickListById(mPickListId)
                .subscribe(new ResponseObserver<List<PickListDetail>>(mContext) {
                    @Override
                    public void handleData(@NotNull List<PickListDetail> data) {
                        mlstPickListDetails.clear();
                        mlstPickListDetails.addAll(data);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


}
