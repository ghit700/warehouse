package com.xmrbi.warehouse.module.check.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;

import java.util.List;

/**
 * Created by wzn on 2018/4/26.
 */

public class CheckStoreDeviceListAdapter extends BaseQuickAdapter<RfidNewInventoryEntity, BaseViewHolder> {
    public CheckStoreDeviceListAdapter(@Nullable List<RfidNewInventoryEntity> data) {
        super(R.layout.check_item_store_device_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RfidNewInventoryEntity item) {
        helper.setText(R.id.tvItemCheckPlace, item.getDrawerName());
        helper.addOnClickListener(R.id.btnItemAutoCheck);
        helper.setText(R.id.tvItemCheckNum, "已盘点" + item.getCheck() + "件" + ",未盘点" + item.getNoCheck() + "件");
    }
}
