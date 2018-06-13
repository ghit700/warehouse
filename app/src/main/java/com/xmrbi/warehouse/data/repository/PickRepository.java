package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.pick.PickListDetail;
import com.xmrbi.warehouse.data.entity.pick.PickListDetailNew;
import com.xmrbi.warehouse.data.remote.PickRemoteSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wzn on 2018/4/26.
 */

public class PickRepository extends BaseRepository {
    private PickRemoteSource pickRemoteSource;


    public PickRepository(BaseActivity baseActivity) {
        super(baseActivity);
        pickRemoteSource = RetrofitHelper.getInstance(PickRemoteSource.class, Config.Http.SERVER_GMMS);
    }

    public PickRepository(BaseActivity baseActivity, String baseUrl) {
        super(baseActivity);
        pickRemoteSource = RetrofitHelper.getInstance(PickRemoteSource.class, baseUrl);
    }

    public Observable<Response<List<PickListDetail>>> getPickListById(long pickListId) {
        return pickRemoteSource.getPickListById(pickListId)
                .compose(new IOTransformer<Response<List<PickListDetail>>>(mBaseActivity));
    }

    public Observable<Response<List<PickListDetail>>> getPickListDetail(long deviceId, long PickListId) {
        return pickRemoteSource.getPickListDetail(PickListId, deviceId)
                .compose(new IOTransformer<Response<List<PickListDetail>>>(mBaseActivity));
    }

    public Observable<Response<List<PickListDetailNew>>> getPickListDetail() {
        return pickRemoteSource.getPickListDetail()
                .compose(new IOTransformer<Response<List<PickListDetailNew>>>(mBaseActivity));
    }

    public Observable<String> updatePickListRfid(String rfid, long deviceId, long pickListId) {
        return pickRemoteSource.updatePickListRfid(rfid, deviceId, pickListId)
                .compose(new IOTransformer<String>(mBaseActivity));
    }
}
