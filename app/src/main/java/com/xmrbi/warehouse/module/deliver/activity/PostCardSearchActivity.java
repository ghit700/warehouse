package com.xmrbi.warehouse.module.deliver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
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
    public static void lauch(Context context, String searchContent) {
        Bundle bundle = new Bundle();
        bundle.putString("searchContent", searchContent);
        Intent intent = new Intent(context, PostCardSearchActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

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
     * 搜索内容
     */
    private String mSearchContent;

    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_post_card_search;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("搜索");
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

    }

    @Override
    protected void initEventAndData() {
        deliverLocalSource = new DeliverLocalSource();
        mLstSearchHistories.addAll(deliverLocalSource.queryAllRfidSearchHistory());
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.btnDeliverDeleteSearchHistory, R.id.btnDeliverSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeliverDeleteSearchHistory:
                deleteAllHistory();
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
        RfidSearchHistory history=new RfidSearchHistory();
        history.setContent(etDeliverPostCardSearch.getText().toString().trim());
        deliverLocalSource.saveRfidSearchHistory(history);
        RxBus.getDefault().post(new RfidSearchHistoryEvent(history.getContent()));
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
