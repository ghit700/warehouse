package com.xmrbi.warehouse.module.check.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;

import java.util.List;

/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceListAdapter extends BaseQuickAdapter<RfidNewCheckingEntity.DataBean, BaseViewHolder> {
    public ManualCheckDeviceListAdapter(@Nullable List<RfidNewCheckingEntity.DataBean> data) {
        super(R.layout.check_item_manual_check_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RfidNewCheckingEntity.DataBean item) {
        helper.setText(R.id.tvItemCheckDevicePlace, item.getDrawerName());
        helper.setText(R.id.tvItemCheckDeviceName, item.getName());
        helper.setText(R.id.tvItemCheckDeviceBrand, item.getBrand());
        helper.setText(R.id.tvItemCheckDeviceModel, item.getModel());
        if (!StringUtils.isEmpty(item.getBookValues())) {
            String[] bookValues = item.getBookValues().split(",");
            Double bookValue = 0d;
            for (String book :
                    bookValues) {
                bookValue += Double.parseDouble(book);
            }
            helper.setText(R.id.tvItemCheckDeviceNum, "账面数量:" + bookValue.toString());
        }
        if (item.getBookValue()!=null) {
            helper.setText(R.id.tvItemCheckDeviceNum, "账面数量:" + item.getBookValue());
        }
        helper.addOnClickListener(R.id.btnItemCheckDevice);
    }
}
