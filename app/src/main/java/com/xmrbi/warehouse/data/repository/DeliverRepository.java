package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.deliver.PlaceShavesEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidAlreadyCardEntity;
import com.xmrbi.warehouse.data.entity.deliver.RfidIsExistStoreDeviceEntity;
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

public class DeliverRepository extends BaseRepository {
    DeliverRemoteSource deliverRemoteSource;

    public DeliverRepository(BaseActivity activity) {
        super(activity);
        deliverRemoteSource = RetrofitHelper.getInstance(DeliverRemoteSource.class, SERVER_GMMS);
    }

    public Observable<RfidUserDeviceEntity> selectAllUserDeviceList(long transactUserId, long lesseeId, long storeId, String name, int pageNo, int pageSize) {
        return deliverRemoteSource
                .selectAllUserDeviceList(transactUserId, lesseeId, storeId, name, pageNo, pageSize)
                .compose(new IOTransformer<RfidUserDeviceEntity>(mBaseActivity));
    }

    public Observable<String> saveModelRfid(long deviceId, String rfid, long userId, long lesseeId, long StoreHouseId, int amount) {
        return deliverRemoteSource.saveModelRfid(deviceId, rfid, userId, lesseeId, StoreHouseId, amount)
                .compose(new IOTransformer<String>(mBaseActivity));
    }

    public Observable<String> queryExitsRfid(String rfids) {
        return deliverRemoteSource.queryExitsRfid(rfids)
                .compose(new IOTransformer<String>(mBaseActivity));
    }

    public Observable<RfidIsExistStoreDeviceEntity> selectIsExistStoreDevice(long lesseeId, long storeId,
                                                                             String name, int pageNo,
                                                                             int pageSize) {
        return deliverRemoteSource.selectIsExistStoreDevice(lesseeId, storeId, name, pageNo, pageSize)
                .compose(new IOTransformer<RfidIsExistStoreDeviceEntity>(mBaseActivity));
    }

    public Observable<RfidAlreadyCardEntity> selectAlreadyRfidDevice(long lesseeId, long storeId,
                                                                     String name, int card, int pageNo,
                                                                     int pageSize) {
        return deliverRemoteSource.selectAlreadyRfidDevice(lesseeId, storeId, name, card, pageNo, pageSize)
                .compose(new IOTransformer<RfidAlreadyCardEntity>(mBaseActivity));
    }

    public Observable<String> findRfidBydeviceId(long deviceId) {
        return deliverRemoteSource.findRfidBydeviceId(deviceId)
                .compose(new IOTransformer<String>(mBaseActivity));
    }

    public Observable<String> queryStoreDeviceNoShelves(String content, String shelves, long storeId, int pageNo, int pageSize) {
        return deliverRemoteSource.queryStoreDeviceNoShelves(content, shelves, storeId, pageNo, pageSize)
                .compose(new IOTransformer<String>(mBaseActivity));
    }

    public Observable<String> queryDrawerNeedClass(long classNameId, long storeId) {
        return deliverRemoteSource.queryDrawerNeedClass(classNameId, storeId)
                .compose(new IOTransformer<String>(mBaseActivity));
    }

    public Observable<String> updateDeviceDrawer(long deviceId, long storeId, String drawerNames) {
        return deliverRemoteSource.updateDeviceDrawer(deviceId, storeId, drawerNames)
                .compose(new IOTransformer<String>(mBaseActivity));
    }
    public Observable<String> deleteRfid(String code) {
        return deliverRemoteSource.deleteRfid(code)
                .compose(new IOTransformer<String>(mBaseActivity));
    }
}
