package com.xmrbi.warehouse.module.check.adapter;


import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;

import java.util.List;

/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceAdapter extends BaseQuickAdapter<RfidNewCheckingEntity.DataBean, BaseViewHolder> {
    public ManualCheckDeviceAdapter(@Nullable List<RfidNewCheckingEntity.DataBean> data) {
        super(R.layout.check_item_manual_check_rfid_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RfidNewCheckingEntity.DataBean item) {
        helper.setText(R.id.tvItemCheckRfid, item.getRfid());
        helper.setText(R.id.tvItemCheckAppNum, item.getBookValue().toString());
        helper.setText(R.id.tvItemCheckFactNum , item.getBookValue().toString());
        helper.addOnClickListener(R.id.btnItemCheck);
        //增加实际数量
        helper.getView(R.id.tvItemFactNumAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num = item.getBookValue();
                if (num < item.getFactNum()) {
                    item.setBookValue(++num);
                }
                notifyDataSetChanged();
            }
        });
        //减少实际数量
        helper.getView(R.id.tvItemFactNumReduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num = item.getBookValue();
                if (num > 0) {
                    item.setBookValue(--num);
                }
                notifyDataSetChanged();
            }
        });
    }
}
