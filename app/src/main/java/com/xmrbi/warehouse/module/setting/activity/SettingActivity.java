package com.xmrbi.warehouse.module.setting.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.HttpUtils;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.entity.main.StoreHouseAioConfig;
import com.xmrbi.warehouse.data.entity.main.Useunit;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.data.repository.MainRepository;
import com.xmrbi.warehouse.module.main.activity.MainActivity;
import com.xmrbi.warehouse.utils.ActivityStackUtils;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.data;
import static com.xmrbi.warehouse.base.Config.SP.SP_IS_NEW;
import static com.xmrbi.warehouse.base.Config.SP.SP_IS_SETTING;
import static com.xmrbi.warehouse.base.Config.SP.SP_NAME;
import static com.xmrbi.warehouse.base.Config.SP.SP_SERVER_IP;
import static com.xmrbi.warehouse.base.Config.SP.SP_SERVER_PORT;

/**
 * Created by wzn on 2018/4/12.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.btnSettingSave)
    Button btnSettingSave;
    @BindView(R.id.tvSettingWarehouseText)
    TextView tvSettingWarehouseText;
    @BindView(R.id.tvSettingLessessText)
    TextView tvSettingLessessText;
    @BindView(R.id.tvSettingWarehouse)
    TextView tvSettingWarehouse;
    @BindView(R.id.tvSettingLessee)
    TextView tvSettingLessee;
    @BindView(R.id.etSettingPort)
    EditText etSettingPort;
    @BindView(R.id.etSettingServerIp)
    EditText etSettingServerIp;
    @BindView(R.id.btnSettingConnect)
    Button btnSettingConnect;
    @BindView(R.id.rgSettingDevice)
    RadioGroup rgSettingDevice;

    private MainRepository mainRepository;
    private MainLocalSource mainLocalSource;
    /**
     * 请求progress
     */
    private MaterialDialog mProgress;
    /**
     * 保存仓库配置的progress
     */
    private MaterialDialog mSaveProgress;
    private MaterialDialog mlstLessessDialog;
    private MaterialDialog mlstStoreHouseDialog;
    private List<Useunit> mLstUseunits;
    private List<StoreHouse> mLstStoreHouses;
    private StoreHouse mStoreHouse;
    private Useunit mUseunit;
    private Boolean isNewDevice;

    @Override
    protected int getLayout() {
        return R.layout.setting_activity;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("设置");
        etSettingServerIp.setText(Config.Http.SERVER_IP);
        etSettingPort.setText(Config.Http.SERVER_PORT);
        mProgress = new MaterialDialog.Builder(mContext)
                .title(R.string.main_progress_title)
                .content(R.string.main_progress_content)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .build();
        mSaveProgress = new MaterialDialog.Builder(mContext)
                .title(R.string.setting_save_progress_title)
                .content(R.string.main_progress_content)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .build();
        rgSettingDevice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                isNewDevice = checkedId == R.id.rbSettingNew;
            }
        });
    }

    @Override
    protected void initEventAndData() {
        mainLocalSource = new MainLocalSource();
        if (SPUtils.getInstance(SP_NAME).getBoolean(SP_IS_SETTING)) {
            mStoreHouse = mainLocalSource.getStoreHouse();
            mUseunit = mainLocalSource.getUseunit(mStoreHouse.getUseunitId());
            tvSettingWarehouse.setText(mStoreHouse.getName());
            tvSettingLessee.setText(mUseunit.getName());
            rgSettingDevice.check(SPUtils.getInstance(SP_NAME).getBoolean(SP_IS_NEW) ? R.id.rbSettingNew : R.id.rbSettingOld);
        } else {
            queryLessessMsg();
        }
    }


    @OnClick({R.id.btnSettingSave, R.id.btnSettingConnect, R.id.tvSettingLessee, R.id.tvSettingWarehouse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSettingLessee:
                if (mlstLessessDialog != null) {
                    mlstLessessDialog.show();
                } else {
                    ToastUtils.showLong(R.string.setting_fail_lessess_dialog);
                }
                break;
            case R.id.tvSettingWarehouse:
                if (mlstStoreHouseDialog != null) {
                    mlstStoreHouseDialog.show();
                } else {
                    ToastUtils.showLong(R.string.setting_fail_house_dialog);
                }
                break;
            case R.id.btnSettingSave:
                saveStoreHouseSetting();
                break;
            case R.id.btnSettingConnect:
                queryLessessMsg();
                break;
        }
    }

    /**
     * 保存仓库配置信息
     */
    private void saveStoreHouseSetting() {
        if (isNewDevice == null) {
            ToastUtils.showLong(R.string.setting_no_choose_device);
            return;
        }
        SPUtils.getInstance(SP_NAME).put(SP_IS_NEW, isNewDevice);
        SPUtils.getInstance(SP_NAME).put(SP_SERVER_IP, etSettingServerIp.getText().toString().trim());
        SPUtils.getInstance(SP_NAME).put(SP_SERVER_PORT, etSettingPort.getText().toString().trim());
        HttpUtils.resetServerAddress();
        mainLocalSource.saveStoreHouse(mStoreHouse);
        mainLocalSource.saveUseunit(mUseunit);
        SPUtils.getInstance(SP_NAME).put(SP_IS_SETTING, true);
        finish();
        lauch(MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (!SPUtils.getInstance(SP_NAME).getBoolean(SP_IS_SETTING, false)) {
            new MaterialDialog.Builder(this)
                    .title(R.string.setting_dailog_title)
                    .content(R.string.setting_warning, true)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .positiveText(R.string.setting_agree)
                    .negativeText(R.string.main_cancel)
                    .show();
        } else {
            finish();
        }
    }

    /**
     * 获取租户信息
     */
    private void queryLessessMsg() {
        setFakeData();
        //每次获取租户信息都要更改baseurl
        Config.Http.SERVER_IP = etSettingServerIp.getText().toString();
        Config.Http.SERVER_PORT = etSettingPort.getText().toString();
        mainRepository = new MainRepository(this);
        mainRepository.mobileLesseeIdStoreHouse()
                .subscribe(new ResponseObserver<List<Useunit>>() {

                    @Override
                    public void handleData(@NotNull List<Useunit> data) {
                        String[] items = new String[data.size()];
                        mLstUseunits = data;
                        for (int i = 0; i < data.size(); i++) {
                            items[i] = data.get(i).getName();
                        }
                        initDialog("租户", items, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                tvSettingLessee.setText(text);
                                mUseunit = mLstUseunits.get(position);
                                mLstStoreHouses = mLstUseunits.get(position).getStoreHouses();
                                String[] items = new String[mLstStoreHouses.size()];
                                for (int i = 0; i < mLstStoreHouses.size(); i++) {
                                    items[i] = mLstStoreHouses.get(i).getName();
                                }
                                initDialog("仓库", items, new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        mStoreHouse = mLstStoreHouses.get(position);
                                        mobileQueryAioConfig(mStoreHouse.getLesseeId(), mStoreHouse.getId());
                                        tvSettingWarehouse.setText(text);
                                    }
                                });
                            }
                        });

                    }

                    @Override
                    protected void showLoadingProgress() {
                        mProgress.show();
                    }

                    @Override
                    protected void closeLoadingProgress() {
                        mProgress.dismiss();
                    }
                });
    }

    /**
     * 设置假数据
     */
    private void setFakeData() {
        String[] items = new String[1];
        mLstUseunits = new ArrayList<>();
        Useunit useunit = new Useunit();
        useunit.setName("管理公司");
        List<StoreHouse> lstStoreHouses = new ArrayList<>();
        StoreHouse house = new StoreHouse();
        house.setId(10L);
        house.setLesseeId(8L);
        house.setName("厦门大桥仓库");
        lstStoreHouses.add(house);
        useunit.setStoreHouses(lstStoreHouses);
        mLstUseunits.add(useunit);
        for (int i = 0; i < mLstUseunits.size(); i++) {
            items[i] = mLstUseunits.get(i).getName();
        }
        initDialog("租户", items, new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                tvSettingLessee.setText(text);
                mUseunit = mLstUseunits.get(position);
                mLstStoreHouses = mLstUseunits.get(position).getStoreHouses();
                String[] items = new String[mLstStoreHouses.size()];
                for (int i = 0; i < mLstStoreHouses.size(); i++) {
                    items[i] = mLstStoreHouses.get(i).getName();
                }
                initDialog("仓库", items, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mStoreHouse = mLstStoreHouses.get(position);
                        mobileQueryAioConfig(mStoreHouse.getLesseeId(), mStoreHouse.getId());
                        tvSettingWarehouse.setText(text);
                    }
                });
            }
        });
    }


    /**
     * 查询仓库一体机参数配置
     */
    public void mobileQueryAioConfig(long lessessId, long storeHouseId) {
        mainRepository.mobileQueryAioConfig(lessessId, storeHouseId)
                .subscribe(new ResponseObserver<List<StoreHouseAioConfig>>() {
                    @Override
                    public void handleData(@NotNull List<StoreHouseAioConfig> data) {
                        mainLocalSource.saveAioConfig(data.get(0));
                    }

                    @Override
                    protected void showLoadingProgress() {
                        mSaveProgress.show();
                    }

                    @Override
                    protected void closeLoadingProgress() {
                        mSaveProgress.dismiss();
                    }
                });
    }

    /**
     * 初始化选择对话框
     *
     * @param title
     * @param items
     * @param callback
     */
    private void initDialog(String title, String[] items, MaterialDialog.ListCallback callback) {
        if (title.equals("租户")) {
            if (mlstLessessDialog == null) {
                mlstLessessDialog = new MaterialDialog.Builder(mContext)
                        .title(title)
                        .itemsCallback(callback)
                        .positiveText(R.string.main_cancel)
                        .items(items)
                        .build();
            }
            mlstLessessDialog.setItems(items);
        }
        if (title.equals("仓库")) {
            if (mlstStoreHouseDialog == null) {
                mlstStoreHouseDialog = new MaterialDialog.Builder(mContext)
                        .title(title)
                        .itemsCallback(callback)
                        .positiveText(R.string.main_cancel)
                        .items(items)
                        .build();
            }
            mlstStoreHouseDialog.setItems(items);
        }
    }

}
