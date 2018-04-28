package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.check.RfidNewInventoryEntity;
import com.xmrbi.warehouse.data.remote.CheckRemoteSource;

import java.util.List;

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


}
