package com.xmrbi.warehouse.module.check.adapter;


import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.check.CheckStoreRfid;

import java.util.List;

/**
 * Created by wzn on 2018/5/3.
 */

public class ManualCheckDeviceAdapter extends BaseQuickAdapter<CheckStoreRfid, BaseViewHolder> {
    public ManualCheckDeviceAdapter(@Nullable List<CheckStoreRfid> data) {
        super(R.layout.check_item_manual_check_rfid_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckStoreRfid item) {
        helper.setText(R.id.tvItemCheckRfid, item.getCode());
        helper.setText(R.id.tvItemCheckAppNum, item.getAmount().toString());
        helper.setText(R.id.tvItemCheckFactNum, item.getFactAmount().toString());
        helper.addOnClickListener(R.id.btnItemCheck);
        //增加实际数量
        helper.getView(R.id.tvItemFactNumAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num = item.getFactAmount();
                if (num < item.getAmount()) {
                    item.setFactAmount(++num);
                }
                notifyDataSetChanged();
            }
        });
        //减少实际数量
        helper.getView(R.id.tvItemFactNumReduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num = item.getFactAmount();
                if (num > 0) {
                    item.setFactAmount(--num);
                }
                notifyDataSetChanged();
            }
        });
    }
}
