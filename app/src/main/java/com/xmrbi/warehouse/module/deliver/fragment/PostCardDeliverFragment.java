package com.xmrbi.warehouse.module.deliver.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseFragment;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.data.repository.DeliverRepository;
import com.xmrbi.warehouse.module.deliver.adapter.PostCardDeliverAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

/**
 * 到货贴卡
 * Created by wzn on 2018/4/19.
 */

public class PostCardDeliverFragment extends BaseFragment {
    @BindView(R.id.listPostCardDeliver)
    RecyclerView listPostCardDeliver;
    @BindView(R.id.srlPostCardDeliver)
    SwipeRefreshLayout srlPostCardDeliver;

    private DeliverRepository deliverRepository;
    private int mPageNo = 1;
    private int mMaxNo = Integer.MAX_VALUE;
    private int mPageNum = 10;
    private StoreHouse mStoreHouse;
    private MainLocalSource mainLocalSource;
    private PostCardDeliverAdapter mAdapter;
    private List<RfidUserDeviceEntity.RowsBean> mLstRowsBeen;
    /**
     * 搜索的内容
     */
    private String mSearchContent;

    public static PostCardDeliverFragment newInstance() {
        PostCardDeliverFragment fragment = new PostCardDeliverFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.deliver_fragment_post_card_deliver;
    }


    @Override
    protected void onViewCreated() {
        mLstRowsBeen = new ArrayList<>();
        listPostCardDeliver.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new PostCardDeliverAdapter(mLstRowsBeen);
        listPostCardDeliver.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                selectAllUserDeviceList(mSearchContent);
                mPageNo++;
            }
        }, listPostCardDeliver);

        srlPostCardDeliver.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    mPageNo = 1;
                    selectAllUserDeviceList(mSearchContent);
            }
        });
    }

    @Override
    protected void initData() {
        mSearchContent = "";
        mainLocalSource = new MainLocalSource();
        mStoreHouse = mainLocalSource.getStoreHouse();
        deliverRepository = new DeliverRepository();
        srlPostCardDeliver.post(new Runnable() {
            @Override
            public void run() {
                srlPostCardDeliver.setRefreshing(true);
                selectAllUserDeviceList(mSearchContent);
            }
        });
    }

    /**
     * 从所有经办人保管设备中选择
     *
     * @param name
     */
    public void selectAllUserDeviceList(String name) {
        if (mPageNo <= mMaxNo) {
            deliverRepository.selectAllUserDeviceList(0, mStoreHouse.getLesseeId(), mStoreHouse.getId(), name, mPageNo, mPageNum)
                    .compose(new IOTransformer<RfidUserDeviceEntity>())
                    .compose(this.<RfidUserDeviceEntity>bindToLifecycle())
                    .subscribe(new BaseObserver<RfidUserDeviceEntity>(getActivity(),true,false) {
                        @Override
                        public void onNext(@NonNull RfidUserDeviceEntity entity) {
                            if (mPageNo == 1) {
                                mMaxNo = entity.getLastPageNO();
                                mLstRowsBeen.clear();
                            }
                            mLstRowsBeen.addAll(entity.getRows());
                            mAdapter.notifyDataSetChanged();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }


                    });
        } else {
            mAdapter.loadMoreEnd();
        }
    }

}
