package com.xmrbi.warehouse.data.local;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.application.WareHouseApplication;
import com.xmrbi.warehouse.component.greendao.StoreHouseAioConfigDao;
import com.xmrbi.warehouse.component.greendao.StoreHouseDao;
import com.xmrbi.warehouse.component.greendao.UseunitDao;
import com.xmrbi.warehouse.data.entity.main.StoreHouse;
import com.xmrbi.warehouse.data.entity.main.StoreHouseAioConfig;
import com.xmrbi.warehouse.data.entity.main.Useunit;

/**
 * Created by wzn on 2018/4/17.
 */

public class MainLocalSource {
    private StoreHouse mStoreHouse;
    private StoreHouseDao mStoreHouseDao;
    private StoreHouseAioConfigDao mStoreHouseAioConfigDao;
    private UseunitDao mUseunitDao;

    public MainLocalSource() {
        mStoreHouseDao = WareHouseApplication.getInstances().getDaoSession().getStoreHouseDao();
        mStoreHouseAioConfigDao= WareHouseApplication.getInstances().getDaoSession().getStoreHouseAioConfigDao();
        mUseunitDao=WareHouseApplication.getInstances().getDaoSession().getUseunitDao();
    }

    /**
     * 获取仓库数据
     *
     * @return
     */
    public  StoreHouse getStoreHouse() {
        if (mStoreHouse == null) {
            if (WareHouseApplication.getInstances().getDaoSession().getStoreHouseDao().count() > 0) {
                //只保存当前仓库的数据
                mStoreHouse = WareHouseApplication.getInstances().getDaoSession().getStoreHouseDao().loadAll().get(0);
            } else {
                ToastUtils.showLong(R.string.no_find_store_house_data);
            }
        }
        return mStoreHouse;
    }
    public void saveStoreHouse(StoreHouse house){
        mStoreHouseDao.insertOrReplace(house);
    }
    public void saveUseunit(Useunit useunit){
        mUseunitDao.insertOrReplace(useunit);
    }
    public Useunit getUseunit(Long useunitId){
        return mUseunitDao.loadByRowId(useunitId);
    }

    /**
     * 保存仓库硬件配置
     */
    public void saveAioConfig(StoreHouseAioConfig config){
        mStoreHouseAioConfigDao.insertOrReplace(config);
    }


}
