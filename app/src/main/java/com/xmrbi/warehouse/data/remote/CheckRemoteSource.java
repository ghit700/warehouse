package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.entity.check.RfidUpdateAutoCheckingEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wzn on 2018/4/26.
 */

public interface CheckRemoteSource {

    /**
     * 获取盘点单数据
     *
     * @param checkId
     * @return
     */
    @GET("gmms/modules/device/check-store-device!countCheckStoreDeviceItemOrRfid.action")
    Observable<Response<List<RfidNewInventoryEntity>>> countCheckStoreDeviceItemOrRfid(@Query("checkId") long checkId);

    /**
     * 扫描枪下载盘点明细
     *
     * @param checkId    盘点单
     * @param drawerName 货架名
     * @param isCheck
     * @return
     */
    @GET("gmms/modules/device/check-store-device!downloadCheckStoreDeviceItemOrRfidByDrawer.action")
    Observable<RfidNewCheckingEntity> downloadCheckStoreDeviceItemOrRfidByDrawer(@Query("checkId") long checkId,
                                                                                 @Query("drawerName") String drawerName, @Query("isCheck") boolean isCheck, @QueryMap Map<String, String> map);

    /**
     * 扫描枪自动盘点更新扫描的rifdj进行自动盘点
     *
     * @param checkId
     * @param rfids
     * @param drawerName
     * @return
     */
    @GET("gmms/modules/device/check-store-device!autoCheckStoreRfid.action")
    Observable<RfidUpdateAutoCheckingEntity> autoCheckStoreRfid(@Query("checkId") long checkId, @Query("rfids") String rfids, @Query("drawerName") String drawerName);

    @POST("gmms/modules/device/check-store-device!manualCheckStoreDeviceItemOrRfid.action")
    Observable<String> manualCheckStoreDeviceItemOrRfid(@QueryMap Map<String, String> map);
}
