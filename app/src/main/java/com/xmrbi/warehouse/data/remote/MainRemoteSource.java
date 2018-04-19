package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.main.StoreHouseAioConfig;
import com.xmrbi.warehouse.data.entity.main.Useunit;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wzn on 2018/4/17.
 */

public interface MainRemoteSource {
    /**
     * 查询租户及现场位置
     *
     * @return
     */
    @GET("/storehouse/main/mobileLesseeIdStoreHouse")
    Observable<Response<List<Useunit>>> mobileLesseeIdStoreHouse();

    @GET("/storehouse/main/mobileQueryAioConfig")
    Observable<Response<List<StoreHouseAioConfig>>> mobileQueryAioConfig(@Query("lesseeId") long lesseeId, @Query("storeHouseId") long storeHouseId);
}
