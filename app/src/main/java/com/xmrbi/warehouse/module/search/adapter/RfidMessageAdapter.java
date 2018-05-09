package com.xmrbi.warehouse.module.search.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.data.entity.deliver.Rfid;

import java.util.List;

/**
 * Created by wzn on 2018/5/7.
 */

public class RfidMessageAdapter extends BaseQuickAdapter<Rfid, BaseViewHolder> {


    public RfidMessageAdapter(@Nullable List<Rfid> data) {
        super(R.layout.search_item_rfid_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Rfid item) {
        helper.setText(R.id.tvItemSearchRfidAmount, item.getAmount());
        helper.setText(R.id.tvItemSearchRfidModel, item.getModel());
        helper.setText(R.id.tvItemSearchRfidName, item.getName());
        helper.setText(R.id.tvItemSearchRfidCode, item.getCode());
    }
}
