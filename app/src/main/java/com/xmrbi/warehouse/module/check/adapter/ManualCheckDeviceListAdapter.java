package com.xmrbi.warehouse.module.check.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;

import java.util.List;


/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceListAdapter extends BaseQuickAdapter<CheckStroeDeviceItem ,BaseViewHolder> {
    public ManualCheckDeviceListAdapter(@Nullable List<CheckStroeDeviceItem> data) {
        super(R.layout.check_item_manual_check_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckStroeDeviceItem item) {
        helper.setText(R.id.tvItemCheckDevicePlace, item.getDrawerNames());
        helper.setText(R.id.tvItemCheckDeviceName, item.getComponentName());
        helper.setText(R.id.tvItemCheckDeviceBrand, item.getBrandName());
        helper.setText(R.id.tvItemCheckDeviceModel, item.getModelName());
        if(item.getFactNum()!=null&&item.getFactNum()!=item.getBookValue()){
            helper.setText(R.id.tvItemCheckDeviceNum, "账面数量:" + item.getBookValue()+" 实际数量："+item.getFactNum());
        }else if (item.getBookValue()!=null){
            helper.setText(R.id.tvItemCheckDeviceNum, "账面数量:" + item.getBookValue());
        }
        helper.addOnClickListener(R.id.btnItemCheckDevice);
    }
}
