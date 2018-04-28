package com.xmrbi.warehouse.module.deliver.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.module.deliver.adapter.PlaceShevesAdapter;
import com.xmrbi.warehouse.module.deliver.fragment.PlaceShelvesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wzn on 2018/4/27.
 */

public class PlaceShelvesActivity extends BaseActivity {

    @BindView(R.id.tlPlaceSheves)
    TabLayout tlPlaceSheves;
    @BindView(R.id.vpPlaceShevesList)
    ViewPager vpPlaceShevesList;
    PlaceShevesAdapter mAdapter;

    private String[] mLstTabs=new String[]{"未上架","已上架"};
    private List<PlaceShelvesFragment> mLstFragments;

    @Override
    protected int getLayout() {
        return R.layout.deliver_activity_place_shelves;
    }

    @Override
    protected void onViewCreated() {
        setActionBarTitle("设备上架");
        mLstFragments=new ArrayList<>();
        mLstFragments.add(new PlaceShelvesFragment());
        mLstFragments.add(new PlaceShelvesFragment());
        mAdapter = new PlaceShevesAdapter(getFragmentManager(),mLstTabs,mLstFragments);
        vpPlaceShevesList.setAdapter(mAdapter);
        tlPlaceSheves.setupWithViewPager(vpPlaceShevesList);
    }

    @Override
    protected void initEventAndData() {

    }

}
