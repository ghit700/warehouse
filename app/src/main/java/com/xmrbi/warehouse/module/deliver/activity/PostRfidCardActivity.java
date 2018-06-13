package com.xmrbi.warehouse.module.deliver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.speedata.libuhf.bean.Tag_Data;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.BaseObserver;
import com.xmrbi.warehouse.component.http.ExceptionHandle;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.data.entity.deliver.Rfid;
import com.xmrbi.warehouse.data.entity.deliver.RfidAlreadyCardEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidIsExistStoreDeviceEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.local.MainLocalSource;
import com.xmrbi.warehouse.data.repository.DeliverRepository;
import com.xmrbi.warehouse.event.RfidPostSucessEvent;
import com.xmrbi.warehouse.event.RfidScanEvent;
import com.xmrbi.warehouse.utils.RxBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.xmrbi.warehouse.component.rfid.RfidUtils.isAccord;


/**
 * 设备贴卡
 * Created by wzn on 2018/4/23.
 */

public class PostRfidCardActivity extends BaseActivity {

    public static void lauch(Context context, Object rowsBean, int type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("rowsBean", (Serializable) rowsBean);
        bundle.putInt("type", type);
        Intent intent = new Intent(context, PostRfidCardActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.tvPostRfidCardName)
    TextView tvPostRfidCardName;
    @BindView(R.id.tvPostRfidCardNum)
    TextView tvPostRfidCardNum;
    @BindView(R.id.tvPostRfidCardBrand)
    TextView tvPostRfidCardBrand;
    @BindView(R.id.tvPostRfidCardModel)
    TextView tvPostRfidCardModel;
    @BindView(R.id.tvPostRfidCardContractNo)
    TextView tvPostRfidCardContractNo;
    @BindView(R.id.tvPostRfidCardNeedClass)
    TextView tvPostRfidCardNeedClass;
    @BindView(R.id.btnRfidPostCardSubmit)
    Button btnRfidPostCardSubmit;
    @BindView(R.id.listRfidPostCard)
    RecyclerView listRfidPostCard;

    /**
     * rfid设备信息(传递不同的值)
     */
    private Object mRowsBean;
    /**
     * 是否在扫描
     */
    private boolean isScan;
    /**
     * 扫描得到的rfid列表
     */
    private List<Rfid> mLstEpcs;
    private BaseQuickAdapter<Rfid, BaseViewHolder> mAdapter;
    private DeliverRepository deliverRepository;
    private MainLocalSource mainLocalSource;
    private StoreHouse mStoreHouse;
    private MaterialDialog mScanDialog;
    private MaterialDialog mSubmitDialog;
    /**
     * 类型
     */
    private int mType;


    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_post_rfid_card;
    }

    @Override
    protected void onViewCreated() {
        mType = mBundle.getInt("type");
        setActionBarTitle("贴卡");
        mRowsBean = mBundle.getSerializable("rowsBean");
        if (mType == PostCardDeliverActivity.POST_CARD) {
            RfidUserDeviceEntity.RowsBean userEntity = (RfidUserDeviceEntity.RowsBean) mRowsBean;
            tvPostRfidCardContractNo.setText(userEntity.getDevice().getAssetCode());
            if (userEntity.getDevice().getModelExtend() != null && userEntity.getDevice().getModelExtend().getNeedClass().getParent() != null) {
                tvPostRfidCardNeedClass.setText(userEntity.getDevice().getModelExtend().getNeedClass().getParent().getClassName());
            }
            tvPostRfidCardName.setText(userEntity.getDevice().getModel().getComponent().getName());
            String unit = userEntity.getDevice().getUnit() == null ? "个" : userEntity.getDevice().getUnit();
            tvPostRfidCardNum.setText(String.valueOf(userEntity.getDevice().getAmount()) + unit);
            tvPostRfidCardModel.setText(userEntity.getDevice().getModel().getName());
            if (userEntity.getDevice().getModel().getBrand() != null) {
                tvPostRfidCardBrand.setText(userEntity.getDevice().getModel().getBrand().getName());
            }
        }
        if (mType == PostCardDeliverActivity.POST_STORE) {
            RfidIsExistStoreDeviceEntity.RowsBean entity = (RfidIsExistStoreDeviceEntity.RowsBean) mRowsBean;
            tvPostRfidCardContractNo.setText(entity.getDevice().getAssetCode());
            if (entity.getDevice().getModelExtend() != null && entity.getDevice().getModelExtend().getNeedClass().getParent() != null) {
                tvPostRfidCardNeedClass.setText(entity.getDevice().getModelExtend().getNeedClass().getParent().getClassName());
            }
            tvPostRfidCardName.setText(entity.getDevice().getModel().getComponent().getName());
            String unit = entity.getDevice().getUnit() == null ? "个" : entity.getDevice().getUnit();
            tvPostRfidCardNum.setText(String.valueOf(entity.getAmount()) + unit);
            tvPostRfidCardModel.setText(entity.getDevice().getModel().getName());
            if (entity.getDevice().getModel().getBrand() != null) {
                tvPostRfidCardBrand.setText(entity.getDevice().getModel().getBrand().getName());
            }
        }
        if (mType == PostCardDeliverActivity.POST_MANAGE) {
            RfidAlreadyCardEntity.RowsBean entity = (RfidAlreadyCardEntity.RowsBean) mRowsBean;
            tvPostRfidCardContractNo.setText(entity.getAssetCode());
            if (entity.getModelExtend() != null && entity.getModelExtend().getNeedClass().getParent() != null) {
                tvPostRfidCardNeedClass.setText(entity.getModelExtend().getNeedClass().getParent().getClassName());
            }
            tvPostRfidCardName.setText(entity.getModel().getComponent().getName());
            String unit = entity.getUnit() == null ? "个" : entity.getUnit();
            tvPostRfidCardNum.setText(String.valueOf(entity.getAmount()) + unit);
            tvPostRfidCardModel.setText(entity.getModel().getName());
            if (entity.getModel().getBrand() != null) {
                tvPostRfidCardBrand.setText(entity.getModel().getBrand().getName());
            }
        }
        //rfidlist
        listRfidPostCard.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLstEpcs = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<Rfid, BaseViewHolder>(R.layout.deliver_item_post_rfid_card, mLstEpcs) {
            @Override
            protected void convert(BaseViewHolder helper, Rfid item) {
                helper.setText(R.id.tvPostRfidCardEPC, item.getCode());
                helper.setText(R.id.etPostRfidCardNum, item.getAmount());
                //不显示输入法
                EditText etPostRfidCardNum = (EditText) helper.getView(R.id.etPostRfidCardNum);
                etPostRfidCardNum.setInputType(InputType.TYPE_NULL);
                etPostRfidCardNum.requestFocus();
                if (etPostRfidCardNum.getText().toString().trim().length() > 0) {
                    etPostRfidCardNum.setSelection(etPostRfidCardNum.getText().toString().trim().length() );
                }
                helper.addOnClickListener(R.id.ivPostRfidCardDelete);

            }
        };
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mLstEpcs.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        listRfidPostCard.setAdapter(mAdapter);
    }

    @Override
    protected void initEventAndData() {
        deliverRepository = new DeliverRepository(this);
        mainLocalSource = new MainLocalSource();
        mStoreHouse = mainLocalSource.getStoreHouse();

        //接收搜索得到的rfid码
        RxBus.getDefault().toObservable(RfidScanEvent.class)
                .compose(this.<RfidScanEvent>bindToLifecycle())
                .subscribe(new Consumer<RfidScanEvent>() {
                    @Override
                    public void accept(RfidScanEvent rfidScanEvent) throws Exception {
                        RfidUtils.stop();
                        List<Tag_Data> lstDatas = rfidScanEvent.getLstTagDatas();
                        if (!lstDatas.isEmpty()) {
                            StringBuffer rfids = new StringBuffer();
                            Rfid rfid = new Rfid();
                            final List<Rfid> tempLstRfid = new ArrayList<>();
                            for (Tag_Data td : lstDatas) {
                                if (RfidUtils.isAccord(td.epc)) {
                                    rfid.setCode(td.epc);
                                    if (!mLstEpcs.contains(rfid)) {
                                        rfids.append(",").append(td.epc);
                                        tempLstRfid.add(new Rfid(td.epc, ""));
                                    } else {
                                        ToastUtils.showLong(td.epc + "请勿重复添加");
                                    }
                                } else {
                                    ToastUtils.showLong(td.epc + "不符合规定");
                                }
                            }
                            if (rfids.length() > 0) {
                                deliverRepository.queryExitsRfid(rfids.substring(1))
                                        .subscribe(new BaseObserver<String>() {
                                            @Override
                                            public void onNext(@NonNull String result) {
                                                if (result.contains("fail")) {
                                                    Iterator<Rfid> iterators = tempLstRfid.iterator();
                                                    while (iterators.hasNext()) {
                                                        Rfid rfid = iterators.next();
                                                        if (result.contains(rfid.getCode())) {
                                                            iterators.remove();
                                                        }
                                                    }
                                                    ToastUtils.showLong(result.substring(4, result.length()) + "已被使用");
                                                }
                                                mLstEpcs.addAll(tempLstRfid);
                                                //扫码出来的rfid按照编码排序
                                                Collections.sort(mLstEpcs, new Comparator<Rfid>() {
                                                    @Override
                                                    public int compare(Rfid o1, Rfid o2) {
                                                        return o1.getCode().compareTo(o2.getCode());
                                                    }
                                                });
                                                mAdapter.notifyDataSetChanged();
                                                mScanDialog.dismiss();
                                                isScan = false;
                                            }

                                            @Override
                                            protected void onError(ExceptionHandle.ResponeThrowable e) {
                                                super.onError(e);
                                                mScanDialog.dismiss();
                                                isScan = false;
                                            }
                                        });
                            } else {
                                if (mScanDialog != null) {
                                    mScanDialog.dismiss();
                                }
                                isScan = false;
                            }
                        } else {
                            if (mScanDialog != null) {
                                mScanDialog.dismiss();
                            }
                            isScan = false;
                        }

                    }
                });
        if (mType == PostCardDeliverActivity.POST_MANAGE) {
            findRfidBydeviceId();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        RfidUtils.stop();
    }

    /**
     * 提交rfid填写结果
     */
    @OnClick(R.id.btnRfidPostCardSubmit)
    public void onClick() {
        if (listRfidPostCard.getChildCount() == 0) {
            ToastUtils.showLong("请扫描rfid码");
            return;
        }
        StringBuffer sbTJ = new StringBuffer();
        StringBuffer sbEN = new StringBuffer();
        for (int i = 0; i < listRfidPostCard.getChildCount(); i++) {
            View child = listRfidPostCard.getChildAt(i);
            String num = ((EditText) child.findViewById(R.id.etPostRfidCardNum)).getText().toString().trim();
            String epc = ((TextView) child.findViewById(R.id.tvPostRfidCardEPC)).getText().toString().trim();
            if (StringUtils.isEmpty(num) || num.equals("0")) {
                ToastUtils.showLong(epc + "请填写数量");
                return;
            }
            sbTJ.append(epc + ",");
            sbEN.append(num + ",");
        }
        String result =
                sbTJ.toString().substring(0, sbTJ.toString().length() - 1)
                        + "|"
                        + sbEN.toString().substring(0, sbEN.toString().length() - 1);
        if (mSubmitDialog == null) {
            mSubmitDialog = new MaterialDialog.Builder(mContext)
                    .content(R.string.post_rfid_card_submiting)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .build();
        }
        mSubmitDialog.show();
        long deviceId = 0;
        int amount = 0;
        switch (mType) {
            case PostCardDeliverActivity.POST_CARD:
                RfidUserDeviceEntity.RowsBean rowsBean = (RfidUserDeviceEntity.RowsBean) mRowsBean;
                deviceId = rowsBean.getDevice().getId();
                amount = rowsBean.getDevice().getAmount();
                break;
            case PostCardDeliverActivity.POST_STORE:
                RfidIsExistStoreDeviceEntity.RowsBean storeRowsBean = (RfidIsExistStoreDeviceEntity.RowsBean) mRowsBean;
                deviceId = storeRowsBean.getDevice().getId();
                amount = storeRowsBean.getDevice().getAmount().intValue();
                break;
            case PostCardDeliverActivity.POST_MANAGE:
                RfidAlreadyCardEntity.RowsBean readyRowsBean = (RfidAlreadyCardEntity.RowsBean) mRowsBean;
                deviceId = readyRowsBean.getId();
                amount = readyRowsBean.getAmount().intValue();
                break;

        }

        deliverRepository
                .saveModelRfid(deviceId, result, 3L, mStoreHouse.getLesseeId(), mStoreHouse.getId(), amount)
                .subscribe(new BaseObserver<String>(mContext, true, false) {
                    @Override
                    public void onNext(@NonNull String result) {
                        //显示上传结果
                        if (result.equals("success")) {
                            ToastUtils.showLong("提交成功");
                            finish();
                            RxBus.getDefault().post(new RfidPostSucessEvent(mRowsBean));
                        } else {
                            ToastUtils.showLong(result);
                        }
                        mSubmitDialog.dismiss();
                    }
                });

    }

    /**
     * 获取设备的rfid码(贴卡管理)
     */
    public void findRfidBydeviceId() {
        RfidAlreadyCardEntity.RowsBean readyRowsBean = (RfidAlreadyCardEntity.RowsBean) mRowsBean;
        long deviceId = readyRowsBean.getId();
        deliverRepository.findRfidBydeviceId(deviceId)
                .subscribe(new BaseObserver<String>(mContext) {
                    @Override
                    public void onNext(@NonNull String result) {
                        if (result.contains("fail")) {
                            ToastUtils.showLong(result.substring(5));
                        } else {
                            String[] epcs = result.substring(7, result.length() - 1).split("\\|");
                            if (epcs.length == 2) {
                                String[] lstRfidCodes = epcs[0].split(",");
                                String[] lstRfidAmounts = epcs[1].split(",");
                                for (int i = 0; i < lstRfidCodes.length; i++) {
                                    mLstEpcs.add(new Rfid(lstRfidCodes[i], lstRfidAmounts[i]));
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 判断是否按压扫描按键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 134) {
            if (!isScan) {
                if (RfidUtils.getIuhfService() != null) {
                    isScan = true;
                    RfidUtils.start();
                    if (mScanDialog == null) {
                        mScanDialog = new MaterialDialog.Builder(mContext)
                                .content(R.string.post_rfid_card_scaning)
                                .progress(true, 0)
                                .progressIndeterminateStyle(false)
                                .show();
                    } else {
                        mScanDialog.show();
                    }
                } else {
                    ToastUtils.showLong(R.string.post_rfid_card_initing);
                }
            } else {
                ToastUtils.showLong(R.string.post_rfid_card_scaning);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
