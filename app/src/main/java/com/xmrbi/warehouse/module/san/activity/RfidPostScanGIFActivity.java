package com.xmrbi.warehouse.module.san.activity;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.blankj.utilcode.util.ActivityUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.utils.ImageLoader;
import com.xmrbi.warehouse.utils.TTSUtils;


import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * Created by wzn on 2018/4/18.
 */

public class RfidPostScanGIFActivity extends BaseActivity {


    public static void lauch(Context context, String type, Long storeHouseId) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putLong("storeHouseId", storeHouseId);
        Intent intent = new Intent(context, RfidPostScanGIFActivity.class);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    @BindView(R.id.ivRfiScanGIF)
    ImageView ivRfiScanGIF;

    private Long mStoreHouseId;
    private String mType;

    @Override
    protected int getLayout() {
        return R.layout.scan_rfid_gif_activity;
    }

    @Override
    protected void onViewCreated() {
        ImageLoader.loadImageViewDynamicGif(mContext, R.drawable.rfidyanshibaii, ivRfiScanGIF);
    }

    @Override
    protected void initEventAndData() {
        mType = mBundle.getString("type");
        mStoreHouseId = mBundle.getLong("storeHouseId");
        //语音提示
        Observable.interval(0L, 5000L, TimeUnit.MILLISECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mType.equals("pick")) {
                            TTSUtils.speak(R.string.scan_pick_hint);
                        } else if (mType.equals("check")) {
                            TTSUtils.speak(R.string.scan_check_hint);
                        }

                    }
                });
    }
}
