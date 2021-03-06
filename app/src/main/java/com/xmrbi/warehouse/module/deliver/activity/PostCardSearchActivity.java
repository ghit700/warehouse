package com.xmrbi.warehouse.module.deliver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.flowlayout.FlowLayoutManager;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.data.entity.deliver.RfidSearchHistory;
import com.xmrbi.warehouse.data.local.DeliverLocalSource;
import com.xmrbi.warehouse.event.RfidSearchHistoryEvent;
import com.xmrbi.warehouse.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wzn on 2018/4/22.
 */

public class PostCardSearchActivity extends BaseActivity {
    public static void lauch(Context context, String searchContent, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("searchContent", searchContent);
        bundle.putInt("type", type);
        Intent intent = new Intent(context, PostCardSearchActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 贴卡管理
     */
    public static final int TYPE_POST_CARD = 0X001;
    /**
     * 设备上架
     */
    public static final int TYPE_PLACE_SHELVES = 0X002;

    @BindView(R.id.etDeliverPostCardSearch)
    EditText etDeliverPostCardSearch;
    @BindView(R.id.btnDeliverDeleteSearchHistory)
    ImageView btnDeliverDeleteSearchHistory;
    @BindView(R.id.btnDeliverSearch)
    Button btnDeliverSearch;
    @BindView(R.id.listDeliverSearch)
    RecyclerView listDeliverSearch;
    private List<RfidSearchHistory> mLstSearchHistories;
    private BaseQuickAdapter<RfidSearchHistory, BaseViewHolder> mAdapter;
    private DeliverLocalSource deliverLocalSource;
    /**
     * 删除历史记录提示框
     */
    private MaterialDialog mDeleteDialog;
    /**
     * 搜索内容
     */
    private String mSearchContent;
    /**
     * 搜索的类型
     */
    private int mType;

    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_post_card_search;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("搜索");
        if (mType == TYPE_PLACE_SHELVES) {
            etDeliverPostCardSearch.setHint(R.string.deliver_place_shelves_search_text);
        } else if (mType == TYPE_POST_CARD) {
            etDeliverPostCardSearch.setHint(R.string.deliver_post_card_search_text);

        }
        FlowLayoutManager manager = new FlowLayoutManager();
        listDeliverSearch.setLayoutManager(manager);
        mLstSearchHistories = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<RfidSearchHistory, BaseViewHolder>(R.layout.deliver_item_post_card_history, mLstSearchHistories) {
            @Override
            protected void convert(BaseViewHolder helper, RfidSearchHistory item) {
                helper.setText(R.id.tvDeliverHistoryContent, item.getContent());
            }
        };
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RxBus.getDefault().post(new RfidSearchHistoryEvent(mLstSearchHistories.get(position)));
                finish();
            }
        });
        listDeliverSearch.setAdapter(mAdapter);
        if (!StringUtils.isEmpty(mBundle.getString("searchContent"))) {
            mSearchContent = mBundle.getString("searchContent");
            etDeliverPostCardSearch.setText(mSearchContent);
        }
        mDeleteDialog = new MaterialDialog.Builder(this)
                .content(R.string.search_delete_hint_content)
                .positiveText(R.string.search_delete_agree_btn)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteAllHistory();
                    }
                })
                .negativeText(R.string.main_cancel)
                .build();

    }

    @Override
    protected void initEventAndData() {
        mType = mBundle.getInt("type");
        deliverLocalSource = new DeliverLocalSource();
        mLstSearchHistories.addAll(deliverLocalSource.queryAllRfidSearchHistory(mType));
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.btnDeliverDeleteSearchHistory, R.id.btnDeliverSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeliverDeleteSearchHistory:
                mDeleteDialog.show();
                break;
            case R.id.btnDeliverSearch:
                searchContent();
                break;
        }
    }

    /**
     * 搜索内容
     */
    private void searchContent() {
        String content = etDeliverPostCardSearch.getText().toString().trim();
        if (!StringUtils.isEmpty(content)) {
            RfidSearchHistory history = new RfidSearchHistory();
            history.setContent(etDeliverPostCardSearch.getText().toString().trim());
            history.setType(mType);
            deliverLocalSource.saveRfidSearchHistory(history);
        }
        RxBus.getDefault().post(new RfidSearchHistoryEvent(content, mType));
        finish();
    }

    /**
     * 删除全部搜索内容
     */
    private void deleteAllHistory() {
        deliverLocalSource.deleteAllRfidSearchHistoty();
        mLstSearchHistories.clear();
        mAdapter.notifyDataSetChanged();
    }

}
