package com.xmrbi.warehouse.module.pick.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;

import java.util.List;

/**
 * Created by wzn on 2018/4/26.
 */

public class PickDeviceRfidAdapter extends BaseQuickAdapter<PickListDetail, BaseViewHolder> {
    public PickDeviceRfidAdapter(@Nullable List<PickListDetail> data) {
        super(R.layout.pick_item_device_mate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PickListDetail item) {
        helper.setText(R.id.tvitemDeviceMateRfidCode, item.getRfidTag());
        helper.setText(R.id.tvitemDeviceMateNum, item.getNum());
        if (!StringUtils.isEmpty(item.getRfid())&&item.getRfid().contains(item.getRfidTag())) {
            helper.setVisible(R.id.ivItemDeviceMate, true);
        } else {
            helper.setVisible(R.id.ivItemDeviceMate, false);
        }
    }
}
