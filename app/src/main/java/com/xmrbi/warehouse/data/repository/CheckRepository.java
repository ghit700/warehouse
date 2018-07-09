package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.check.CheckStroeDeviceItem;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.remote.CheckRemoteSource;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by wzn on 2018/4/26.
 */

public class CheckRepository extends BaseRepository {
    private CheckRemoteSource mCheckRemoteSource;

    public CheckRepository(BaseActivity mBaseActivity) {
        super(mBaseActivity);
        mCheckRemoteSource = RetrofitHelper.getInstance(CheckRemoteSource.class);
    }

    public Observable<Response<List<RfidNewInventoryEntity>>> mobileCountCheckStoreDeviceItem(long checkId) {
        return mCheckRemoteSource.mobileCountCheckStoreDeviceItem(checkId)
                .compose(new IOTransformer<Response<List<RfidNewInventoryEntity>>>(mBaseActivity));
    }

    public Observable<Response<List<CheckStroeDeviceItem>>> mobileCountCheckStoreDeviceItemDetail(long checkId, String drawerName) {
        return mCheckRemoteSource.mobileCountCheckStoreDeviceItemDetail(checkId, drawerName)
                .compose(new IOTransformer<Response<List<CheckStroeDeviceItem>>>(mBaseActivity));
    }

    public Observable<Response<List<CheckStroeDeviceItem>>> mobileUnCheckStoreDeviceItemList(long checkId, String drawerName) {
        return mCheckRemoteSource.mobileUnCheckStoreDeviceItemList(checkId, drawerName)
                .compose(new IOTransformer<Response<List<CheckStroeDeviceItem>>>(mBaseActivity));
    }

    public Observable<Response<CheckStroeDeviceItem>> mobileUnCheckStoreDeviceItemDetail(long checkStroeDeviceItemId) {
        return mCheckRemoteSource.mobileUnCheckStoreDeviceItemDetail(checkStroeDeviceItemId)
                .compose(new IOTransformer<Response<CheckStroeDeviceItem>>(mBaseActivity));
    }

    public Observable<Response<String>> mobileAutoCheckStoreRfid(long checkId, String rfids) {
        return mCheckRemoteSource.mobileAutoCheckStoreRfid(checkId, rfids)
                .compose(new IOTransformer<Response<String>>(mBaseActivity));
    }

    public Observable<Response<String>> mobileSubmitCheckStroeDeviceItem(long CheckStroeDeviceItemId, Integer factAmount,String code) {
        return mCheckRemoteSource.mobileSubmitCheckStroeDeviceItem(CheckStroeDeviceItemId,factAmount,code)
                .compose(new IOTransformer<Response<String>>(mBaseActivity));
    }


}
