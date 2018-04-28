package com.xmrbi.warehouse.event;

import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;

/**
 * rfid贴码成功
 * Created by wzn on 2018/4/24.
 */

public class RfidPostSucessEvent {
    private Object rowBean;

    public RfidPostSucessEvent(Object userRowsBean) {
        this.rowBean = userRowsBean;
    }


    public Object getRowBean() {
        return rowBean;
    }

}
