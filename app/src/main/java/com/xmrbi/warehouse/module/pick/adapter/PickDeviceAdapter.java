package com.xmrbi.warehouse.module.pick.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;

import java.util.List;

/**
 * Created by wzn on 2018/4/26.
 */

public class PickDeviceAdapter extends BaseQuickAdapter<PickListDetail, BaseViewHolder> {

    public PickDeviceAdapter(@Nullable List<PickListDetail> data) {
        super(R.layout.pick_item_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PickListDetail item) {
        helper.setText(R.id.tvItemDevicePlace, item.getStorage());
        helper.setText(R.id.tvitemDeviceName, item.getName());
        helper.setText(R.id.tvItemDeviceNum, "请领数量" + item.getNum() + "个");
        helper.setText(R.id.tvItemDeviceBrand, item.getBrand());
        helper.setText(R.id.tvItemDeviceModel, item.getModel());
        helper.setText(R.id.tvItemDeviceRfid, item.getRfid());
        if (item.getMate().equals("1")) {
            helper.setVisible(R.id.btnItemDevicePost, false);
            helper.setVisible(R.id.ivItemDevicePicked, true);
            helper.setVisible(R.id.textview1, true);
            helper.setVisible(R.id.tvItemDeviceRfid, true);
        } else {
            helper.setVisible(R.id.textview1, false);
            helper.setVisible(R.id.tvItemDeviceRfid, false);
            helper.setVisible(R.id.ivItemDevicePicked, false);
            helper.setVisible(R.id.btnItemDevicePost, true);

        }
        helper.addOnClickListener(R.id.btnItemDevicePost);
    }
}
