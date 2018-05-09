package com.xmrbi.warehouse.data.repository;

import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.Response;
import com.xmrbi.warehouse.component.http.RetrofitHelper;
import com.xmrbi.warehouse.data.entity.deliver.Rfid;
import com.xmrbi.warehouse.data.remote.SearchRemoteSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wzn on 2018/5/9.
 */

public class SearchRepository extends BaseRepository {
    SearchRemoteSource searchRemoteSource;

    public SearchRepository(BaseActivity mBaseActivity) {
        super(mBaseActivity);
        searchRemoteSource = RetrofitHelper.getInstance(SearchRemoteSource.class, Config.Http.SERVER_GMMS);
    }

    public Observable<Response<List<Rfid>>> queryRfidMsg(String rfids) {
        return searchRemoteSource.queryRfidMsg(rfids)
                .compose(new IOTransformer<Response<List<Rfid>>>(mBaseActivity));
    }
}
