package com.xmrbi.warehouse.data.remote;

import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.data.entity.deliver.Rfid;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wzn on 2018/5/9.
 */

public interface SearchRemoteSource {
    /**
     * 查询rfid码对应的设备信息
     * @param rfids
     * @return
     */
    @GET("/gmms/modules/device/check-store-device!queryRfidMsg.action")
    Observable<Response<List<Rfid>>> queryRfidMsg(@Query("rfids") String rfids);
}
