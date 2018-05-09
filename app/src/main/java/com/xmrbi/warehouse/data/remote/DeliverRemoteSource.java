package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.deliver.PlaceShavesEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidAlreadyCardEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidIsExistStoreDeviceEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.iflytek.sunflower.config.a.t;

/**
 * Created by wzn on 2018/4/20.
 */

public interface DeliverRemoteSource {
    /**
     * 从所有经办人保管设备中选择
     *
     * @param transactUserId
     * @param lesseeId
     * @param storeId
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("gmms/modules/device/in-out-order!selectAllUserDeviceList.action")
    Observable<RfidUserDeviceEntity> selectAllUserDeviceList(@Query("transactUserId") long transactUserId, @Query("lesseeId") long lesseeId,
                                                             @Query("storeId") long storeId, @Query("name") String name,
                                                             @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 保存rfid码和设备关联
     *
     * @param deviceId
     * @param rfid
     * @param userId
     * @param lesseeId
     * @param StoreHouseId
     * @param amount       原数量
     * @return
     */
    @POST("gmms/modules/device/device!saveModelRfid.action")
    Observable<String> saveModelRfid(@Query("deviceId") long deviceId, @Query("rfid") String rfid, @Query("userId") long userId,
                                     @Query("lesseeId") long lesseeId, @Query("StoreHouseId") long StoreHouseId,
                                     @Query("amount") int amount);

    /**
     * 库存设备中未贴卡的
     *
     * @param lesseeId
     * @param storeId
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("gmms/modules/device/in-out-order!selectIsExistStoreDevice.action")
    Observable<RfidIsExistStoreDeviceEntity> selectIsExistStoreDevice(@Query("lesseeId") long lesseeId, @Query("storeId") long storeId,
                                                                      @Query("name") String name, @Query("pageNo") int pageNo,
                                                                      @Query("pageSize") int pageSize);

    /**
     * 查询所有已贴卡设备(Device)
     *
     * @param lesseeId
     * @param storeId
     * @param name
     * @param pageNo
     * @param card
     * @param pageSize
     * @return
     */
    @GET("gmms/modules/device/in-out-order!selectAlreadyRfidDevice.action")
    Observable<RfidAlreadyCardEntity> selectAlreadyRfidDevice(@Query("lesseeId") long lesseeId, @Query("storeId") long storeId,
                                                              @Query("name") String name, @Query("pageNo") int pageNo,
                                                              @Query("card") int card, @Query("pageSize") int pageSize);

    /**
     * 查询此rfid是否已经使用
     *
     * @param rfids (,隔开的rfid数组)
     * @return
     */
    @GET("gmms/modules/device/device!queryExitsRfid.action")
    Observable<String> queryExitsRfid(@Query("rfid") String rfids);

    /**
     * 通过设备id获取设备的rfid码
     *
     * @param deviceId
     * @return
     */
    @GET("gmms/modules/device/device!findRfidBydeviceId.action")
    Observable<String> findRfidBydeviceId(@Query("deviceId") long deviceId);


    /**
     * 查询出在库设备上架情况
     *
     * @param content  搜索内容
     * @param shelves  如果此参数为null 则查询未上架设备（一般为1）
     * @param storeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("gmms/modules/device/in-out-order!queryStoreDeviceNoShelves.action")
    Observable<String> queryStoreDeviceNoShelves(@Query("content") String content, @Query("shelves") String shelves, @Query("storeId") long storeId, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    /**
     * 查询货架
     *
     * @param classNameId 货架类别
     * @param storeId
     * @return
     */
    @GET("gmms/modules/device/in-out-order!queryDrawerNeedClass.action")
    Observable<String> queryDrawerNeedClass(@Query("classNameId") long classNameId, @Query("storeId") long storeId);

    /**
     * 更新货架
     *
     * @param deviceId
     * @param storeId
     * @param drawerNames
     * @return
     */
    @POST("gmms/modules/device/device!updateDeviceDrawer.action")
    Observable<String> updateDeviceDrawer(@Query("deviceId") long deviceId, @Query("storeId") long storeId, @Query("drawerNames") String drawerNames);
}
