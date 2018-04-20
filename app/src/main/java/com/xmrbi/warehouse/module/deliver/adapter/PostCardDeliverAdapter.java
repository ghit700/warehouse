package com.xmrbi.warehouse.module.deliver.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity.RowsBean;

import java.util.List;

/**
 * Created by wzn on 2018/4/20.
 */

public class PostCardDeliverAdapter extends BaseQuickAdapter<RowsBean, BaseViewHolder> {

    public PostCardDeliverAdapter(@Nullable List<RowsBean> data) {
        super(R.layout.deliver_item_post_card_deliver, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RowsBean item) {
        helper.setText(R.id.tvDeliverItemContractNo, item.getDevice().getAssetCode());
        if (item.getDevice().getModelExtend() != null && item.getDevice().getModelExtend().getNeedClass().getParent() != null) {
            helper.setText(R.id.tvDeliverItemNeedClass, item.getDevice().getModelExtend().getNeedClass().getParent().getClassName());
        }
        helper.setText(R.id.tvDeliverItemName, item.getDevice().getModel().getComponent().getName());
        String unit = item.getDevice().getUnit() == null ? "个" : item.getDevice().getUnit();
        helper.setText(R.id.tvDeliverItemNum, String.valueOf(item.getDevice().getAmount()) + unit);
        helper.setText(R.id.tvDeliverItemModel, item.getDevice().getModel().getName());
        if (item.getDevice().getModel().getBrand() != null) {
            helper.setText(R.id.tvDeliverItemBrand, item.getDevice().getModel().getBrand().getName());
        }
        helper.setText(R.id.tvDeliverItemSequenceCode, item.getDevice().getSequenceCode());
        helper.setText(R.id.tvDeliverItemSuplier, item.getDevice().getContractName() == null ? "" : item.getDevice().getContractName());
        helper.addOnClickListener(R.id.btnDeliverItemPost);

    }
}
