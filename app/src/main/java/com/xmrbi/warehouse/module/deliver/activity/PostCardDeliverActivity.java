package com.xmrbi.warehouse.module.deliver.activity;


import android.widget.FrameLayout;

import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.module.deliver.fragment.PostCardDeliverFragment;
import com.xmrbi.warehouse.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 到货贴卡
 * Created by wzn on 2018/4/19.
 */

public class PostCardDeliverActivity extends BaseActivity {
    @BindView(R.id.flDeliverPostCardList)
    FrameLayout flDeliverPostCardList;
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
        mPostCardDeliverFragment=PostCardDeliverFragment.newInstance();
        FragmentUtils.add(getFragmentManager(),mPostCardDeliverFragment,R.id.flDeliverPostCardList);
    }


    @OnClick(R.id.tvDeliverPostCardSearchText)
    public void onClick() {
    }

}
