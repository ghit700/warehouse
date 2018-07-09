package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.entity.check.RfidUpdateAutoCheckingEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
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
    @GET("storehouse/checkstore/checkstore/mobileCountCheckStoreDeviceItem")
    Observable<Response<List<RfidNewInventoryEntity>>> mobileCountCheckStoreDeviceItem(@Query("checkId") long checkId);

    /**
     * 扫描枪下载盘点明细统计
     *
     * @param checkId    盘点单
     * @param drawerName 货架名
     * @return
     */
    @GET("storehouse/checkstore/checkstore/mobileCountCheckStoreDeviceItemDetail")
    Observable<Response<List<CheckStroeDeviceItem>>> mobileCountCheckStoreDeviceItemDetail(@Query("checkId") long checkId,
                                                                                           @Query("drawerName") String drawerName);

    /**
     * 获取该货架未盘点的盘点单明细
     *
     * @param checkId
     * @param drawerName
     * @return
     */
    @GET("storehouse/checkstore/checkstore/mobileUnCheckStoreDeviceItemList")
    Observable<Response<List<CheckStroeDeviceItem>>> mobileUnCheckStoreDeviceItemList(@Query("checkId") long checkId, @Query("drawerName") String drawerName);


    /**
     * 获取未盘点的设备的设备详情
     *
     * @param checkStroeDeviceItemI
     * @return
     */
    @GET("storehouse/checkstore/checkstore/mobileUnCheckStoreDeviceItemDetail")
    Observable<Response<CheckStroeDeviceItem>> mobileUnCheckStoreDeviceItemDetail(@Query("checkStroeDeviceItemId") long checkStroeDeviceItemI);

    /**
     * 扫描枪自动盘点更新扫描的rifdj进行自动盘点
     *
     * @param checkId
     * @param rfids
     * @return
     */
    @GET("storehouse/checkstore/checkstore/mobileAutoCheckStoreRfid")
    Observable<Response<String>> mobileAutoCheckStoreRfid(@Query("checkId") long checkId, @Query("codes") String rfids);

    /**
     * 人工盘点提交盘点单明细
     *
     * @param CheckStroeDeviceItemId
     * @param factAmount
     * @param code
     * @return
     */
    @POST("storehouse/checkstore/checkstore/mobileSubmitCheckStroeDeviceItem")
    Observable<Response<String>> mobileSubmitCheckStroeDeviceItem(@Query("CheckStroeDeviceItemId") long CheckStroeDeviceItemId,
                                                        @Query("factAmount") Integer factAmount, @Query("code") String code);
}
