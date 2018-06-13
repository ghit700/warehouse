package com.xmrbi.warehouse.module.deliver.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.BaseFragment;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.ExceptionHandle;
import com.xmrbi.warehouse.data.entity.deliver.RfidAlreadyCardEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidIsExistStoreDeviceEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.data.repository.DeliverRepository;
import com.xmrbi.warehouse.event.RfidPostSucessEvent;
import com.xmrbi.warehouse.event.RfidSearchHistoryEvent;
import com.xmrbi.warehouse.module.deliver.activity.PostCardDeliverActivity;
import com.xmrbi.warehouse.module.deliver.activity.PostRfidCardActivity;
import com.xmrbi.warehouse.module.deliver.adapter.PostCardDeliverAdapter;
import com.xmrbi.warehouse.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
    private int mMaxNo = Integer.MAX_VALUE;
    private int mPageNo = 1;
    private int mPageNum = 5;
    private StoreHouse mStoreHouse;
    private MainLocalSource mainLocalSource;
    private PostCardDeliverAdapter mAdapter;
    /**
     * 用户设备列表
     */
    private List<RfidUserDeviceEntity.RowsBean> mLstUserDeviceRowsBeen;
    /**
     * 库存设备列表
     */
    private List<RfidIsExistStoreDeviceEntity.RowsBean> mLstStoreDeviceRowsBeen;
    /**
     * 已经贴卡的设备列表
     */
    private List<RfidAlreadyCardEntity.RowsBean> mLstReadyDeviceRowsBeen;
    /**
     * 搜索的内容
     */
    private String mSearchContent;
    /**
     * 类型
     */
    private int mType;

    public static PostCardDeliverFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        PostCardDeliverFragment fragment = new PostCardDeliverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    public static PostCardDeliverFragment newInstance(int type,String assetCode) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("assetCode", assetCode);
        PostCardDeliverFragment fragment = new PostCardDeliverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.deliver_fragment_post_card_deliver;
    }


    @Override
    protected void onViewCreated() {
        mType = getArguments().getInt("type");
        listPostCardDeliver.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        switch (mType) {
            case PostCardDeliverActivity.POST_CARD:
                mLstUserDeviceRowsBeen = new ArrayList<>();
                mAdapter = new PostCardDeliverAdapter<>(mLstUserDeviceRowsBeen, mType);
                break;
            case PostCardDeliverActivity.POST_STORE:
                mLstStoreDeviceRowsBeen = new ArrayList<>();
                mAdapter = new PostCardDeliverAdapter<>(mLstStoreDeviceRowsBeen, mType);
                break;
            case PostCardDeliverActivity.POST_MANAGE:
                mLstReadyDeviceRowsBeen = new ArrayList<>();
                mAdapter = new PostCardDeliverAdapter<>(mLstReadyDeviceRowsBeen, mType);
                break;
        }
        listPostCardDeliver.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPageNo++;
                queryData();
            }
        }, listPostCardDeliver);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List list = mLstUserDeviceRowsBeen != null ? mLstUserDeviceRowsBeen : mLstStoreDeviceRowsBeen != null ? mLstStoreDeviceRowsBeen : mLstReadyDeviceRowsBeen;
                PostRfidCardActivity.lauch(getActivity(), list.get(position), mType);
            }
        });
        mAdapter.setEmptyView(R.layout.empty_view);
        srlPostCardDeliver.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                queryData();
            }
        });
    }

    @Override
    protected void initData() {
        if( getArguments().containsKey("assetCode")){
            mSearchContent=getArguments().getString("assetCode");
        }else{
            mSearchContent = "";
        }
        mainLocalSource = new MainLocalSource();
        mStoreHouse = mainLocalSource.getStoreHouse();
        deliverRepository = new DeliverRepository((BaseActivity) getActivity());
        srlPostCardDeliver.post(new Runnable() {
            @Override
            public void run() {
                srlPostCardDeliver.setRefreshing(true);
                queryData();
            }
        });
        RxBus.getDefault().toObservable(RfidSearchHistoryEvent.class)
                .compose(this.<RfidSearchHistoryEvent>bindToLifecycle())
                .subscribe(new Consumer<RfidSearchHistoryEvent>() {
                    @Override
                    public void accept(RfidSearchHistoryEvent event) throws Exception {
                        mSearchContent = event.getHistory().getContent();
                        mPageNo = 1;
                        srlPostCardDeliver.setRefreshing(true);
                        queryData();
                    }
                });
        RxBus.getDefault().toObservable(RfidPostSucessEvent.class)
                .subscribe(new Consumer<RfidPostSucessEvent>() {
                    @Override
                    public void accept(RfidPostSucessEvent event) throws Exception {
                        switch (mType) {
                            case PostCardDeliverActivity.POST_CARD:
                                mLstUserDeviceRowsBeen.remove(event.getRowBean());
                                break;
                            case PostCardDeliverActivity.POST_STORE:
                                mLstStoreDeviceRowsBeen.remove(event.getRowBean());
                                break;
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void queryData() {
        switch (mType) {
            case PostCardDeliverActivity.POST_CARD:
                selectAllUserDeviceList();
                break;
            case PostCardDeliverActivity.POST_STORE:
                selectIsExistStoreDevice();
                break;
            case PostCardDeliverActivity.POST_MANAGE:
                selectAlreadyRfidDevice();
                break;
        }
    }

    /**
     * 修改库存已经贴卡的设备
     */
    public void selectAlreadyRfidDevice() {
        if (mPageNo <= mMaxNo) {
            deliverRepository.selectAlreadyRfidDevice(mStoreHouse.getLesseeId(), mStoreHouse.getId(), mSearchContent, 1, mPageNo, mPageNum)
                    .subscribe(new BaseObserver<RfidAlreadyCardEntity>(getActivity(), true, false) {
                        @Override
                        public void onNext(@NonNull RfidAlreadyCardEntity entity) {
                            if (mPageNo == 1) {
                                mMaxNo = entity.getLastPageNO();
                                mLstReadyDeviceRowsBeen.clear();
                            }
                            mLstReadyDeviceRowsBeen.addAll(entity.getRows());
                            mAdapter.notifyDataSetChanged();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }

                        @Override
                        protected void onError(ExceptionHandle.ResponeThrowable e) {
                            super.onError(e);
                            mLstReadyDeviceRowsBeen.clear();
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void closeLoadingProgress() {
                            super.closeLoadingProgress();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }
                    });
        } else {
            mAdapter.loadMoreEnd();
            srlPostCardDeliver.setRefreshing(false);
        }
    }

    /**
     * 库存设备中未贴卡的
     */
    public void selectIsExistStoreDevice() {
        if (mPageNo <= mMaxNo) {
            deliverRepository.selectIsExistStoreDevice(mStoreHouse.getLesseeId(), mStoreHouse.getId(), mSearchContent, mPageNo, mPageNum)
                    .subscribe(new BaseObserver<RfidIsExistStoreDeviceEntity>(getActivity(), true, false) {
                        @Override
                        public void onNext(@NonNull RfidIsExistStoreDeviceEntity entity) {
                            if (mPageNo == 1) {
                                mMaxNo = entity.getLastPageNO();
                                mLstStoreDeviceRowsBeen.clear();
                            }
                            mLstStoreDeviceRowsBeen.addAll(entity.getRows());
                            mAdapter.notifyDataSetChanged();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }

                        @Override
                        protected void onError(ExceptionHandle.ResponeThrowable e) {
                            super.onError(e);
                            mLstStoreDeviceRowsBeen.clear();
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void closeLoadingProgress() {
                            super.closeLoadingProgress();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }
                    });
        } else {
            mAdapter.loadMoreEnd();
            srlPostCardDeliver.setRefreshing(false);
        }
    }


    /**
     * 从所有经办人保管设备中选择
     */
    public void selectAllUserDeviceList() {
        if (mPageNo <= mMaxNo) {
            deliverRepository.selectAllUserDeviceList(0, mStoreHouse.getLesseeId(), mStoreHouse.getId(), mSearchContent, mPageNo, mPageNum)
                    .subscribe(new BaseObserver<RfidUserDeviceEntity>(getActivity(), true, false) {
                        @Override
                        public void onNext(@NonNull RfidUserDeviceEntity entity) {
                            if (mPageNo == 1) {
                                mMaxNo = entity.getLastPageNO();
                                mLstUserDeviceRowsBeen.clear();
                            }
                            mLstUserDeviceRowsBeen.addAll(entity.getRows());
                            mAdapter.notifyDataSetChanged();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }

                        @Override
                        protected void onError(ExceptionHandle.ResponeThrowable e) {
                            super.onError(e);
                            mLstUserDeviceRowsBeen.clear();
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        protected void closeLoadingProgress() {
                            super.closeLoadingProgress();
                            srlPostCardDeliver.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                        }
                    });
        } else {
            mAdapter.loadMoreEnd();
            srlPostCardDeliver.setRefreshing(false);
        }
    }

    /**
     * 获取搜索内容
     *
     * @return
     */
    public String getSearchContent() {
        return mSearchContent;
    }
}
