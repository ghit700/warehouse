package com.xmrbi.warehouse.module.deliver.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;

import com.xmrbi.warehouse.component.view.FragmentPagerAdapter.FragmentPagerAdapter;
import com.xmrbi.warehouse.module.deliver.fragment.PlaceShelvesFragment;

import java.util.List;

/**
 * Created by wzn on 2018/4/28.
 */

public class PlaceShevesAdapter extends FragmentPagerAdapter {
    private String[] mLstTabs;
    private List<PlaceShelvesFragment> mLstFragments;


    public PlaceShevesAdapter(FragmentManager fm, String[] mLstTabs, List<PlaceShelvesFragment> mLstFragments) {
        super(fm);
        this.mLstTabs = mLstTabs;
        this.mLstFragments = mLstFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mLstFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mLstTabs[position];
    }

    @Override
    public int getCount() {
        return mLstTabs.length;
    }
}
