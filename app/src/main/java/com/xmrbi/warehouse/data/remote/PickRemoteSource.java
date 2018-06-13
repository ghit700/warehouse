package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;
import com.xmrbi.warehouse.data.entity.pick.PickListDetailNew;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wzn on 2018/4/26.
 */

public interface PickRemoteSource {
    /**
     * 根据领料单号查询设备
     *
     * @param PickListId
     * @return
     */
    @GET("gmms/modules/device/device!getPickListById.action")
    Observable<Response<List<PickListDetail>>> getPickListById(@Query("PickListId") long PickListId);

    /**
     * 获取领料单明细
     *
     * @param PickListId
     * @param deviceId
     * @return
     */
    @GET("gmms/modules/device/device!getPickListDetail.action")
    Observable<Response<List<PickListDetail>>> getPickListDetail(@Query("PickListId") long PickListId, @Query("deviceId") long deviceId);

    /**
     * 获取领料单明细
     *
     * @return
     */
    @GET("getPickListDetail.action")
    Observable<Response<List<PickListDetailNew>>> getPickListDetail();

    /**
     * 更新设备的rifd码扫描情况（也就是领料情况）
     *
     * @param rfid
     * @param deviceId
     * @return
     */
    @POST("gmms/modules/device/device!updatePickListRfid.action")
    Observable<String>  updatePickListRfid(@Query("rfid") String rfid, @Query("deviceId") long deviceId, @Query("pickListId") long pickListId);
}
