package com.xmrbi.warehouse.module.search.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.speedata.libuhf.bean.Tag_Data;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.component.http.ExceptionHandle;
import com.xmrbi.warehouse.component.http.ResponseObserver;
import com.xmrbi.warehouse.component.rfid.RfidUtils;
import com.xmrbi.warehouse.data.entity.deliver.Rfid;
import com.xmrbi.warehouse.data.repository.SearchRepository;
import com.xmrbi.warehouse.event.RfidScanEvent;
import com.xmrbi.warehouse.module.search.adapter.RfidMessageAdapter;
import com.xmrbi.warehouse.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by wzn on 2018/5/7.
 */

public class RfidSearchActivity extends BaseActivity {
    @BindView(R.id.listSearchRfid)
    RecyclerView listSearchRfid;

    private RfidMessageAdapter mAdapter;
    private List<Rfid> mLstRfids;
    private SearchRepository searchRepository;
    private boolean isScan = false;
    private MaterialDialog mScanDialog;

    @Override
    protected int getLayout() {
        return R.layout.search_activity_rfid_message;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("标签查询");
        listSearchRfid.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLstRfids = new ArrayList<>();
        mAdapter = new RfidMessageAdapter(mLstRfids);
        listSearchRfid.setAdapter(mAdapter);
        View emptyView= getLayoutInflater().inflate(R.layout.empty_view,listSearchRfid,false);
        ((TextView)emptyView.findViewById(R.id.tvEmptyContent)).setText("对准设备Rfid,读取设备信息");
        mAdapter.setEmptyView(emptyView);
    }

    @Override
    protected void initEventAndData() {
        searchRepository = new SearchRepository(this);
        RxBus.getDefault().toObservable(RfidScanEvent.class)
                .subscribe(new Consumer<RfidScanEvent>() {
                    @Override
                    public void accept(RfidScanEvent rfidScanEvent) throws Exception {
                        RfidUtils.stop();
                        List<Tag_Data> lstTagData = rfidScanEvent.getLstTagDatas();
                        if (!lstTagData.isEmpty()) {
                            StringBuffer rfids = new StringBuffer();
                            for (Tag_Data td :
                                    lstTagData) {
                                if (RfidUtils.isAccord(td.epc)) {
                                    rfids.append(",").append("'").append(td.epc).append("'");
                                }
                            }
                            if (rfids.length() > 0) {
                                queryRfidMsg(rfids.substring(1));
                            } else {
                                isScan = false;
                                mScanDialog.dismiss();
                            }
                        } else {
                            isScan = false;
                            mScanDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        RfidUtils.stop();
    }

    private void queryRfidMsg(String rfids) {
        mLstRfids.clear();
        searchRepository.queryRfidMsg(rfids)
                .subscribe(new ResponseObserver<List<Rfid>>() {
                    @Override
                    public void handleData(List<Rfid> data) {
                        mLstRfids.addAll(data);
                    }
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        isScan = false;
                        mScanDialog.dismiss();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 判断是否按压扫描按键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 134) {
            if (!isScan) {
                if (RfidUtils.getIuhfService() != null) {
                    isScan = true;
                    RfidUtils.start();
                    if (mScanDialog == null) {
                        mScanDialog = new MaterialDialog.Builder(mContext)
                                .content(R.string.post_rfid_card_scaning)
                                .progress(true, 0)
                                .progressIndeterminateStyle(false)
                                .show();
                    } else {
                        mScanDialog.show();
                    }
                } else {
                    ToastUtils.showLong(R.string.post_rfid_card_initing);
                }
            } else {
                ToastUtils.showLong(R.string.post_rfid_card_scaning);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
