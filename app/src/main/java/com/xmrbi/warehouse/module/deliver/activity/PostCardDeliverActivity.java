package com.xmrbi.warehouse.module.deliver.activity;


import android.widget.FrameLayout;
import android.widget.TextView;

import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.event.RfidSearchHistoryEvent;
import com.xmrbi.warehouse.module.deliver.fragment.PostCardDeliverFragment;
import com.xmrbi.warehouse.utils.FragmentUtils;
import com.xmrbi.warehouse.utils.RxBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 到货贴卡
 * Created by wzn on 2018/4/19.
 */

public class PostCardDeliverActivity extends BaseActivity {
    @BindView(R.id.flDeliverPostCardList)
    FrameLayout flDeliverPostCardList;
    @BindView(R.id.tvDeliverPostCardSearchText)
    TextView tvDeliverPostCardSearchText;
    PostCardDeliverFragment mPostCardDeliverFragment;

    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_post_card_deliver;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("到货贴卡");
    }

    @Override
    protected void initEventAndData() {
        mPostCardDeliverFragment = PostCardDeliverFragment.newInstance();
        FragmentUtils.add(getFragmentManager(), mPostCardDeliverFragment, R.id.flDeliverPostCardList);
        RxBus.getDefault().toObservable(RfidSearchHistoryEvent.class)
                .compose(this.<RfidSearchHistoryEvent>bindToLifecycle())
                .subscribe(new Consumer<RfidSearchHistoryEvent>() {
                    @Override
                    public void accept(RfidSearchHistoryEvent event) throws Exception {
                        tvDeliverPostCardSearchText.setText(event.getHistory().getContent());
                    }
                });
    }


    @OnClick(R.id.tvDeliverPostCardSearchText)
    public void onClick() {
        PostCardSearchActivity.lauch(mContext, mPostCardDeliverFragment.getSearchContent());
    }


}
