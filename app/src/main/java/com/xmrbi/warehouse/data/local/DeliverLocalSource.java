package com.xmrbi.warehouse.data.local;

import com.iflytek.cloud.util.ResourceUtil;
import com.xmrbi.warehouse.application.WareHouseApplication;
import com.xmrbi.warehouse.component.greendao.RfidSearchHistoryDao;
import com.xmrbi.warehouse.data.entity.deliver.RfidSearchHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzn on 2018/4/22.
 */

public class DeliverLocalSource {
    RfidSearchHistoryDao rfidSearchHistoryDao;

    public DeliverLocalSource() {
        rfidSearchHistoryDao = WareHouseApplication.getInstances().getDaoSession().getRfidSearchHistoryDao();
    }

    /**
     * 获取全部的rfid搜索内容
     *
     * @return
     */
    public List<RfidSearchHistory> queryAllRfidSearchHistory() {
        return rfidSearchHistoryDao.loadAll();
    }

    public void saveRfidSearchHistory(RfidSearchHistory history) {
        List<RfidSearchHistory> lstHistories = rfidSearchHistoryDao.queryRaw(" where T.content =? ",history.getContent());
        if (lstHistories.isEmpty()) {
            rfidSearchHistoryDao.insertOrReplace(history);
        }
    }

    /**
     * 清空全部
     */
    public void deleteAllRfidSearchHistoty() {
        rfidSearchHistoryDao.deleteAll();
    }


}
