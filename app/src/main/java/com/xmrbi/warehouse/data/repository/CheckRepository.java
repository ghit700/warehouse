package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.check.RfidNewCheckingEntity;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.entity.check.RfidUpdateAutoCheckingEntity;
import com.xmrbi.warehouse.data.remote.CheckRemoteSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wzn on 2018/4/26.
 */

public class CheckRepository extends BaseRepository {
    private CheckRemoteSource checkRemoteSource;

    public CheckRepository(BaseActivity mBaseActivity) {
        super(mBaseActivity);
        checkRemoteSource = RetrofitHelper.getInstance(CheckRemoteSource.class, Config.Http.SERVER_GMMS);
    }

    public Observable<Response<List<RfidNewInventoryEntity>>> countCheckStoreDeviceItemOrRfid(long checkId) {
        return checkRemoteSource.countCheckStoreDeviceItemOrRfid(checkId)
                .compose(new IOTransformer<Response<List<RfidNewInventoryEntity>>>(mBaseActivity));
    }

    public Observable<RfidNewCheckingEntity> downloadCheckStoreDeviceItemOrRfidByDrawer(long checkId, String drawerName, boolean isCheck) {
        return checkRemoteSource.downloadCheckStoreDeviceItemOrRfidByDrawer(checkId, drawerName, isCheck, new HashMap<String, String>())
                .compose(new IOTransformer<RfidNewCheckingEntity>(mBaseActivity));
    }

    public Observable<RfidNewCheckingEntity> downloadCheckStoreDeviceItemOrRfidByDrawer(long checkId, String drawerName, boolean isCheck, Map<String, String> map) {
        return checkRemoteSource.downloadCheckStoreDeviceItemOrRfidByDrawer(checkId, drawerName, isCheck, map)
                .compose(new IOTransformer<RfidNewCheckingEntity>(mBaseActivity));
    }

    public Observable<RfidUpdateAutoCheckingEntity> autoCheckStoreRfid(long checkId, String rfids, String drawerName) {
        return checkRemoteSource.autoCheckStoreRfid(checkId, rfids, drawerName)
                .compose(new IOTransformer<RfidUpdateAutoCheckingEntity>(mBaseActivity));
    }

    public Observable<String> manualCheckStoreDeviceItemOrRfid(Map<String, String> map) {
        return checkRemoteSource.manualCheckStoreDeviceItemOrRfid(map)
                .compose(new IOTransformer<String>(mBaseActivity));
    }


}
