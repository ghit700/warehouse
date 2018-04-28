package com.xmrbi.warehouse.event;

import com.speedata.libuhf.bean.Tag_Data;

import java.util.List;

/**
 * Created by wzn on 2018/4/23.
 */

public class RfidScanEvent {
    List<Tag_Data> lstTagDatas;

    public RfidScanEvent(List<Tag_Data> lstTagDatas) {
        this.lstTagDatas = lstTagDatas;
    }

    public List<Tag_Data> getLstTagDatas() {
        return lstTagDatas;
    }

}
