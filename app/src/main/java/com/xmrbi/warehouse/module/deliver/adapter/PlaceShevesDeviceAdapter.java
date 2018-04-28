package com.xmrbi.warehouse.module.deliver.adapter;

import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.deliver.PlaceShavesEntity;
import com.xmrbi.warehouse.module.deliver.fragment.PlaceShelvesFragment;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.iflytek.sunflower.config.a.u;

/**
 * Created by wzn on 2018/4/28.
 */

public class PlaceShevesDeviceAdapter extends BaseQuickAdapter<PlaceShavesEntity, BaseViewHolder> {
    private int mType;

    public PlaceShevesDeviceAdapter(@Nullable List<PlaceShavesEntity> data, int type) {
        super(R.layout.deliver_item_place_shelves, data);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, PlaceShavesEntity item) {
        String ContractNo = item.getAssetCode();
        if (StringUtils.isEmpty(ContractNo)) {
            ContractNo = "暂无合同编号";
        }
        helper.setText(R.id.tvDeliverItemPlaceContractNo, ContractNo);
        helper.setText(R.id.tvDeliverItemPlaceNeedClass, item.getNeedClassName().split("->")[0]);
        helper.setText(R.id.tvDeliverItemPlaceBrand, item.getBrand());
        helper.setText(R.id.tvDeliverItemPlaceModel, item.getModel());
        helper.setText(R.id.tvDeliverItemPlaceName, item.getName());
        String unit = !StringUtils.isEmpty(item.getUnit()) ? item.getUnit() : "个";
        helper.setText(R.id.tvDeliverItemPlaceNum, String.valueOf(item.getAmount()) + unit);
        if (mType == PlaceShelvesFragment.TYPE_NONE_PLACE) {
            //未上架
            if (item.getRfidTime() != null) {
                helper.setText(R.id.tvDeliverItemPlaceCardTime, TimeUtils.millis2String(item.getRfidTime(), new SimpleDateFormat("yyyy.MM.dd")) + "贴卡");
            }
            helper.addOnClickListener(R.id.btnDeliverItemPlacePost);

        } else if (mType == PlaceShelvesFragment.TYPE_PLACE) {
            //已上架
            helper.setText(R.id.btnDeliverItemPlacePost, "修改货架")
                    .addOnClickListener(R.id.btnDeliverItemPlacePost);
            helper.setText(R.id.tvDeliverItemPlaceCardTime, item.getDrawerName());

        }
    }
}
