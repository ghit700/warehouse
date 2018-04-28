package com.xmrbi.warehouse.module.deliver.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.BaseFragment;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.ExceptionHandle;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.data.entity.deliver.NeedClass;
import com.xmrbi.warehouse.data.entity.deliver.PlaceShavesEntity;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.data.repository.DeliverRepository;
import com.xmrbi.warehouse.module.deliver.adapter.PlaceShevesDeviceAdapter;

import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by wzn on 2018/4/28.
 */

public class PlaceShelvesFragment extends BaseFragment {
    @BindView(R.id.tvDeliverPostCardSearchText)
    TextView tvDeliverPostCardSearchText;
    @BindView(R.id.listDeliverPlaceShelves)
    RecyclerView listDeliverPlaceShelves;
    @BindView(R.id.srlDeliverPlaceShelves)
    SwipeRefreshLayout srlDeliverPlaceShelves;

    public static PlaceShelvesFragment newInstance(int type) {
        PlaceShelvesFragment fragment = new PlaceShelvesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 已上架
     */
    public static final int TYPE_PLACE = 1;
    /**
     * 未上架
     */
    public static final int TYPE_NONE_PLACE = 0;

    /**
     * 类型
     */
    private int mType;
    private PlaceShevesDeviceAdapter mAdapter;
    private List<PlaceShavesEntity> mLstPlaceShavesEntities;
    private DeliverRepository deliverRepository;
    private String mSearchContent;
    private MainLocalSource mainLocalSource;
    private StoreHouse mStoreHouse;
    private int mPageNo = 1;
    private int mPageSize = 10;
    private MaterialDialog mChooseDrawerDialog;
    /**
     * 货架信息显示
     */
    private TextView mTvDeliverItemPlaceDrawar;
    /**
     * 货架列表
     */
    private List<NeedClass> mLstNeedClass;

    @Override
    protected int getLayout() {
        return R.layout.deliver_fragment_place_shelves;
    }

    @Override
    protected void onViewCreated() {
        mType = getArguments().getInt("type");
        mLstPlaceShavesEntities = new ArrayList<>();
        mAdapter = new PlaceShevesDeviceAdapter(mLstPlaceShavesEntities, mType);
        srlDeliverPlaceShelves.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryStoreDeviceNoShelves();
            }
        });
        listDeliverPlaceShelves.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listDeliverPlaceShelves.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                queryDrawerNeedClass(mLstPlaceShavesEntities.get(position).getNeedId());
                mTvDeliverItemPlaceDrawar = listDeliverPlaceShelves.getChildAt(position).findViewById(R.id.tvDeliverItemPlaceCardTime);
                if (mChooseDrawerDialog == null) {
                    mLstNeedClass = new ArrayList<>();
                    mChooseDrawerDialog = new MaterialDialog.Builder(getActivity())
                            .title("货架选择")
                            .items(new ArrayList())
                            .itemsCallbackMultiChoice(new Integer[]{0}, new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    return false;
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                    Integer[] selectIndices = dialog.getSelectedIndices();
                                    if (selectIndices != null && selectIndices.length > 0) {
                                        StringBuffer selectText = new StringBuffer();
                                        for (int index : selectIndices) {
                                            selectText.append(",").append(mLstNeedClass.get(index).getName());
                                        }
                                        updateDeviceDrawer(mLstPlaceShavesEntities.get(position).getDeviceId(), selectText.toString(), position);
                                    } else {
                                        ToastUtils.showLong("请选择货架");
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                    mChooseDrawerDialog.clearSelectedIndices();
                                }
                            })
                            .positiveText("确定")
                            .negativeText("取消")
                            .autoDismiss(false)
                            .neutralText("清空")
                            .build();
                }

            }
        });

    }

    @Override
    protected void initData() {
        deliverRepository = new DeliverRepository(((BaseActivity) getActivity()));
        mSearchContent = "";
        srlDeliverPlaceShelves.post(new Runnable() {
            @Override
            public void run() {
                srlDeliverPlaceShelves.setRefreshing(true);
                queryStoreDeviceNoShelves();
            }
        });
        mainLocalSource = new MainLocalSource();
        mStoreHouse = mainLocalSource.getStoreHouse();
    }

    /**
     * 获取在库设备上架情况
     */
    public void queryStoreDeviceNoShelves() {
        deliverRepository.queryStoreDeviceNoShelves(mSearchContent, mType == TYPE_PLACE ? "1" : "", mStoreHouse.getId(), mPageNo, mPageSize)
                .subscribe(new ResponseObserver<List<PlaceShavesEntity>>(getActivity(), true, false) {
                    @Override
                    public void handleData(@NotNull List<PlaceShavesEntity> data) {
                        if (mPageNo == 1) {
                            mLstPlaceShavesEntities.clear();
                        }
                        srlDeliverPlaceShelves.setRefreshing(false);
                        mLstPlaceShavesEntities.addAll(data);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 查询货架信息
     */
    public void queryDrawerNeedClass(long classNameId) {
        deliverRepository.queryDrawerNeedClass(classNameId, mStoreHouse.getId())
                .subscribe(new BaseObserver<String>(getActivity()) {
                    @Override
                    public void onNext(@NonNull String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            if (StringUtils.isEmpty(json.getString("errorMsg"))) {
                                List<NeedClass> data = JSON.parseArray(json.getString("data"), NeedClass.class);
                                if (data != null && !data.isEmpty()) {
                                    mLstNeedClass.clear();
                                    mLstNeedClass.addAll(data);
                                    mChooseDrawerDialog.getBuilder().items(mLstNeedClass);
                                    mChooseDrawerDialog.notifyItemsChanged();
                                    mChooseDrawerDialog.show();
                                } else {
                                    mChooseDrawerDialog.getItems().clear();
                                    ToastUtils.showLong("该仓库没有找到对应类型的货架");
                                }
                            } else {
                                mChooseDrawerDialog.getItems().clear();
                                ToastUtils.showLong(json.getString("errorMsg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onError(ExceptionHandle.ResponeThrowable e) {
                        super.onError(e);
                        mChooseDrawerDialog.getItems().clear();

                    }
                });

    }

    /**
     * 更新货架信息
     *
     * @param deviceId
     * @param drawerNames
     * @param position
     */
    public void updateDeviceDrawer(long deviceId, final String drawerNames, final int position) {
        deliverRepository.updateDeviceDrawer(deviceId, mStoreHouse.getId(), drawerNames)
                .subscribe(new BaseObserver<String>(getActivity()) {
                    @Override
                    public void onNext(@NonNull String result) {
                        if (result.contains("成功")) {
                            mTvDeliverItemPlaceDrawar.setText(drawerNames);
                            mLstPlaceShavesEntities.remove(position);
                        }
                        ToastUtils.showLong(result);
                    }

                    @Override
                    protected void showLoadingProgress() {
                        if (getDialog() == null) {
                            setDialog(new MaterialDialog.Builder(getActivity())
                                    .title("更新货架中")
                                    .content(R.string.main_progress_content)
                                    .progress(true, 0)
                                    .progressIndeterminateStyle(false)
                                    .build());
                        }
                        super.showLoadingProgress();
                    }

                });
    }


}
