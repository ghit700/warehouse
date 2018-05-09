package com.xmrbi.warehouse.module.main.activity;


import android.Manifest;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.event.ChangeStoreHouseSettingEvent;
import com.xmrbi.warehouse.module.deliver.activity.DevicePostCardActivity;
import com.xmrbi.warehouse.module.deliver.activity.PlaceShelvesActivity;
import com.xmrbi.warehouse.module.san.activity.ScanActivity;
import com.xmrbi.warehouse.module.search.activity.RfidSearchActivity;
import com.xmrbi.warehouse.module.setting.activity.SettingActivity;
import com.xmrbi.warehouse.utils.RxBus;
import com.xmrbi.warehouse.utils.UpdateUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.xmrbi.warehouse.base.Config.SP.SP_IS_NEW;
import static com.xmrbi.warehouse.base.Config.SP.SP_NAME;

public class MainActivity extends BaseActivity {


    @BindView(R.id.llMainDeliverGoods)
    LinearLayout llMainDeliverGoods;
    @BindView(R.id.llMainPickGoods)
    LinearLayout llMainPickGoods;
    @BindView(R.id.llMainCheckGoods)
    LinearLayout llMainCheckGoods;
    @BindView(R.id.llMainSearchGoods)
    LinearLayout llMainSearchGoods;
    @BindView(R.id.leftBaseActionbar)
    ImageView leftBaseActionbar;
    @BindView(R.id.titleBaseAction)
    TextView titleBaseAction;
    @BindView(R.id.rightBaseActionbar)
    ImageView rightBaseActionbar;
    @BindView(R.id.ivMainSearchIcon)
    ImageView ivMainSearchIcon;
    @BindView(R.id.tvMainDeleverText)
    TextView tvMainDeleverText;
    @BindView(R.id.tvMainCheckText)
    TextView tvMainCheckText;
    @BindView(R.id.tvMainPickText)
    TextView tvMainPickText;
    @BindView(R.id.tvMainSearchText)
    TextView tvMainSearchText;

    private MainLocalSource mainLocalSource;
    private StoreHouse mStoreHouse;
    /**
     * 是否是新版手持枪
     */
    private boolean mIsNew;
    /**
     * 上次按键时间
     */
    private long mkeyTime;

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onViewCreated() {
        leftBaseActionbar.setBackgroundResource(R.drawable.ic_scan);
        rightBaseActionbar.setBackgroundResource(R.drawable.ic_settings);
        leftBaseActionbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lauch(RfidSearchActivity.class);
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mainLocalSource = new MainLocalSource();
        changeStoreHouseSetting();
        //仓库设置改变事件
        RxBus.getDefault().toObservable(ChangeStoreHouseSettingEvent.class)
                .subscribe(new Consumer<ChangeStoreHouseSettingEvent>() {
                    @Override
                    public void accept(ChangeStoreHouseSettingEvent changeStoreHouseSettingEvent) throws Exception {
                        changeStoreHouseSetting();
                    }
                });
        //初始化rfid扫描器
        initRfid();
        //更新apk
        new UpdateUtils(mContext).updateAPK();
    }

    @Override
    protected void onDestroy() {
        if (RfidUtils.getIuhfService() != null) {
            RfidUtils.closeDevice();
            RfidUtils.destoryService();
        }
        super.onDestroy();
    }

    @OnClick({R.id.llMainDeliverGoods, R.id.llMainPickGoods, R.id.llMainCheckGoods, R.id.llMainSearchGoods, R.id.leftBaseActionbar, R.id.rightBaseActionbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBaseActionbar:
                break;
            case R.id.rightBaseActionbar:
                lauch(SettingActivity.class);
                break;
            case R.id.llMainDeliverGoods:
                if (mIsNew) {
                    lauchPermission(new String[]{Manifest.permission.CAMERA}, ScanActivity.class, "送货扫码");
                } else {
                    lauch(DevicePostCardActivity.class);
                }
                break;
            case R.id.llMainPickGoods:
                if (mIsNew) {
                    lauchPermission(new String[]{Manifest.permission.CAMERA}, ScanActivity.class, "领料扫码");
                } else {
                    lauch(PlaceShelvesActivity.class);
                }
                break;
            case R.id.llMainCheckGoods:
                lauchPermission(new String[]{Manifest.permission.CAMERA}, ScanActivity.class, "盘点扫码");
                break;
            case R.id.llMainSearchGoods:
                if (mIsNew) {

                } else {
                    lauchPermission(new String[]{Manifest.permission.CAMERA}, ScanActivity.class, "领料扫码");
                }
                break;
        }
    }

    /**
     * 切换仓库配置
     */
    public void changeStoreHouseSetting() {
        mIsNew = SPUtils.getInstance(SP_NAME).getBoolean(SP_IS_NEW);
        mStoreHouse = mainLocalSource.getStoreHouse();
        titleBaseAction.setText(mStoreHouse.getName());
        if (!mIsNew) {
            tvMainCheckText.setText(R.string.main_text_check_goods_old);
            tvMainPickText.setText(R.string.main_text_pick_goods_old);
            tvMainDeleverText.setText(R.string.main_text_deliver_goods_old);
            tvMainSearchText.setText(R.string.main_text_search_goods_old);
        } else {
            tvMainCheckText.setText(R.string.main_text_check_goods);
            tvMainPickText.setText(R.string.main_text_pick_goods);
            tvMainDeleverText.setText(R.string.main_text_deliver_goods);
            tvMainSearchText.setText(R.string.main_text_search_goods);
        }
    }

    /**
     * 初始化扫描枪
     */
    private void initRfid() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                if (RfidUtils.getIuhfService() != null) {
                    if (RfidUtils.getIuhfService() != null) {
                        //打开设备是一个比较耗时的操作，所以放到最前面
                        RfidUtils.openDevice();
                    }
                    e.onNext(true);
                } else {
                    e.onNext(false);
                }
            }
        })
                .compose(new IOTransformer<Boolean>(MainActivity.this))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean result) throws Exception {
                        //如果不是对应的扫描枪应用不可用
                        if (!result) {
                            finish();
                        }
                    }
                });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                    mkeyTime = System.currentTimeMillis();
                    ToastUtils.showLong(R.string.main_exit_toast);
                } else {
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
