package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.deliver.RfidUserDeviceEntity;
import com.xmrbi.warehouse.data.remote.DeliverRemoteSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.http.Query;

import static com.xmrbi.warehouse.base.Config.Http.SERVER_GMMS;

/**
 * Created by wzn on 2018/4/20.
 */

public class DeliverRepository {
    DeliverRemoteSource deliverRemoteSource;

    public DeliverRepository() {
        deliverRemoteSource = RetrofitHelper.getInstance(DeliverRemoteSource.class, SERVER_GMMS);
    }

    public Observable<RfidUserDeviceEntity> selectAllUserDeviceList(long transactUserId, long lesseeId, long storeId, String name, int pageNo, int pageSize) {
        return deliverRemoteSource
                .selectAllUserDeviceList(transactUserId, lesseeId, storeId, name, pageNo, pageSize);


    }
}
