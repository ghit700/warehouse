package com.xmrbi.warehouse.module.deliver.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 设备列表
 * Created by wzn on 2018/4/19.
 */

public class PostCardDeliverActivity extends BaseActivity {

    public static void lauch(Context context, int type) {
        Bundle bundle = new Bundle();
        //设备列表的类型
        bundle.putInt("type", type);
        Intent intent = new Intent(context, PostCardDeliverActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 到货贴卡
     */
    public final static int POST_CARD = 0x001;
    /**
     * 库存贴卡
     */
    public final static int POST_STORE = 0x002;
    /**
     * 贴卡管理
     */
    public final static int POST_MANAGE = 0x003;

    @BindView(R.id.flDeliverPostCardList)
    FrameLayout flDeliverPostCardList;
    @BindView(R.id.tvDeliverPostCardSearchText)
    TextView tvDeliverPostCardSearchText;
    PostCardDeliverFragment mPostCardDeliverFragment;
    private int mType;

    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_post_card_deliver;
    }

    @Override
    protected void onViewCreated() {
        mType = mBundle.getInt("type");
        String title = "";
        switch (mType) {
            case POST_CARD:
                title = "到货贴卡";
                break;
            case POST_STORE:
                title = "库存贴卡";
                break;
            case POST_MANAGE:
                title = "贴卡管理";
                break;

        }
        setActionBarTitle(title);
    }

    @Override
    protected void initEventAndData() {
        mPostCardDeliverFragment = PostCardDeliverFragment.newInstance(mType);
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
