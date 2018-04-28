package com.xmrbi.warehouse.module.deliver.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.solver.SolverVariable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.deliver.RfidAlreadyCardEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidIsExistStoreDeviceEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity.RowsBean;
import com.xmrbi.warehouse.module.deliver.activity.PostCardDeliverActivity;

import java.util.List;

/**
 * Created by wzn on 2018/4/20.
 */

public class PostCardDeliverAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private int mType;

    public PostCardDeliverAdapter(@Nullable List<T> data, int type) {
        super(R.layout.deliver_item_post_card_deliver, data);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (mType == PostCardDeliverActivity.POST_CARD) {
            RowsBean entity = (RowsBean) item;
            helper.setText(R.id.tvDeliverItemContractNo, entity.getDevice().getAssetCode());
            if (entity.getDevice().getModelExtend() != null && entity.getDevice().getModelExtend().getNeedClass().getParent() != null) {
                helper.setText(R.id.tvDeliverItemNeedClass, entity.getDevice().getModelExtend().getNeedClass().getParent().getClassName());
            }
            helper.setText(R.id.tvDeliverItemName, entity.getDevice().getModel().getComponent().getName());
            String unit = entity.getDevice().getUnit() == null ? "个" : entity.getDevice().getUnit();
            helper.setText(R.id.tvDeliverItemNum, String.valueOf(entity.getDevice().getAmount()) + unit);
            helper.setText(R.id.tvDeliverItemModel, entity.getDevice().getModel().getName());
            if (entity.getDevice().getModel().getBrand() != null) {
                helper.setText(R.id.tvDeliverItemBrand, entity.getDevice().getModel().getBrand().getName());
            }
            helper.setText(R.id.tvDeliverItemSequenceCode, entity.getDevice().getSequenceCode());
            helper.setText(R.id.tvDeliverItemSuplier, entity.getDevice().getContractName() == null ? "" : entity.getDevice().getContractName());
            helper.addOnClickListener(R.id.btnDeliverItemPost);
        }
        if (mType == PostCardDeliverActivity.POST_STORE) {
            RfidIsExistStoreDeviceEntity.RowsBean entity = (RfidIsExistStoreDeviceEntity.RowsBean) item;
            helper.setText(R.id.tvDeliverItemContractNo, entity.getDevice().getAssetCode());
            if (entity.getDevice().getModelExtend() != null && entity.getDevice().getModelExtend().getNeedClass().getParent() != null) {
                helper.setText(R.id.tvDeliverItemNeedClass, entity.getDevice().getModelExtend().getNeedClass().getParent().getClassName());
            }
            helper.setText(R.id.tvDeliverItemName, entity.getDevice().getModel().getComponent().getName());
            String unit = entity.getDevice().getUnit() == null ? "个" : entity.getDevice().getUnit();
            helper.setText(R.id.tvDeliverItemNum, String.valueOf(entity.getDevice().getAmount()) + unit);
            helper.setText(R.id.tvDeliverItemModel, entity.getDevice().getModel().getName());
            if (entity.getDevice().getModel().getBrand() != null) {
                helper.setText(R.id.tvDeliverItemBrand, entity.getDevice().getModel().getBrand().getName());
            }
            helper.setText(R.id.tvDeliverItemSequenceCode, entity.getDevice().getSequenceCode());
            helper.setText(R.id.tvDeliverItemSuplier, entity.getDevice().getContractName() == null ? "" : entity.getDevice().getContractName());
            helper.addOnClickListener(R.id.btnDeliverItemPost);
        }
        if (mType == PostCardDeliverActivity.POST_MANAGE) {
            RfidAlreadyCardEntity.RowsBean entity = (RfidAlreadyCardEntity.RowsBean) item;
            helper.setText(R.id.tvDeliverItemContractNo, entity.getAssetCode());
            if (entity.getModelExtend() != null && entity.getModelExtend().getNeedClass().getParent() != null) {
                helper.setText(R.id.tvDeliverItemNeedClass, entity.getModelExtend().getNeedClass().getParent().getClassName());
            }
            helper.setText(R.id.tvDeliverItemName, entity.getModel().getComponent().getName());
            String unit = entity.getUnit() == null ? "个" : entity.getUnit();
            helper.setText(R.id.tvDeliverItemNum, String.valueOf(entity.getAmount()) + unit);
            helper.setText(R.id.tvDeliverItemModel, entity.getModel().getName());
            if (entity.getModel().getBrand() != null) {
                helper.setText(R.id.tvDeliverItemBrand, entity.getModel().getBrand().getName());
            }
            helper.setText(R.id.tvDeliverItemSequenceCode, entity.getSequenceCode());
            helper.setText(R.id.tvDeliverItemSuplier, entity.getContractName() == null ? "" : entity.getContractName());
            helper.setText(R.id.btnDeliverItemPost, "修改")
                    .addOnClickListener(R.id.btnDeliverItemPost);
        }


    }
}
