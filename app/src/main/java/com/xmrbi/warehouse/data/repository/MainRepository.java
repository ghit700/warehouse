package com.xmrbi.warehouse.data.repository;


import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.main.StoreHouseAioConfig;
import com.xmrbi.warehouse.data.entity.main.Useunit;
import com.xmrbi.warehouse.data.remote.MainRemoteSource;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by wzn on 2018/4/17.
 */

public class MainRepository {
    private MainRemoteSource mainRemoteSource;

    public MainRepository() {
        mainRemoteSource = RetrofitHelper.getInstance(MainRemoteSource.class);
    }

    public Observable<Response<List<Useunit>>> mobileLesseeIdStoreHouse() {
        return mainRemoteSource.mobileLesseeIdStoreHouse();
    }

    public Observable<Response<List<StoreHouseAioConfig>>> mobileQueryAioConfig(long lesessId, long storeHouseId) {
        return mainRemoteSource.mobileQueryAioConfig(lesessId, storeHouseId);
    }
}
