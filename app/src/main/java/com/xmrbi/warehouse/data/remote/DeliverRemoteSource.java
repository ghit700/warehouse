package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wzn on 2018/4/20.
 */

public interface DeliverRemoteSource {
    /**
     * 从所有经办人保管设备中选择
     * @param transactUserId
     * @param lesseeId
     * @param storeId
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("modules/device/in-out-order!selectAllUserDeviceList.action")
    Observable<RfidUserDeviceEntity> selectAllUserDeviceList(@Query("transactUserId") long transactUserId, @Query("lesseeId") long lesseeId,
                                                             @Query("storeId") long storeId, @Query("name") String name,
                                                             @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);
}
