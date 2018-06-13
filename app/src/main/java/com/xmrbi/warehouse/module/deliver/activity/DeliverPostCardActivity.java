package com.xmrbi.warehouse.module.deliver.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.module.deliver.fragment.PostCardDeliverFragment;
import com.xmrbi.warehouse.utils.FragmentUtils;


/**
 * Created by wzn on 2018/6/13.
 */
public class DeliverPostCardActivity extends BaseActivity {

    private PostCardDeliverFragment mFragment;
    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_deliver_post_card;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("设备贴卡");
    }

    @Override
    protected void initEventAndData() {
        mFragment=PostCardDeliverFragment.newInstance(PostCardDeliverActivity.POST_CARD,getIntent().getStringExtra("assetCode"));
        FragmentUtils.add(getFragmentManager(),mFragment,R.id.flDeliverPostCardList);
    }


}
