package com.xmrbi.warehouse.event;

import com.xmrbi.warehouse.data.entity.pick.PickListDetail;

/**
 * Created by wzn on 2018/4/27.
 */

public class UpdatePickListDetailEvent {
    private int position;
    private PickListDetail pickListDetail;

    public UpdatePickListDetailEvent(int position, PickListDetail pickListDetail) {
        this.position = position;
        this.pickListDetail = pickListDetail;
    }

    public int getPosition() {
        return position;
    }

    public PickListDetail getPickListDetail() {
        return pickListDetail;
    }
}
