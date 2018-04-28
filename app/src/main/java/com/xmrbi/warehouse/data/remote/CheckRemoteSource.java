package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
}
