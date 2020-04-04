package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.FaisalUncle.LunchFragmentFaisal;

public class MyAdapterFaisalLunch extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapterFaisalLunch(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LunchFragmentFaisal();
        }
        return null;
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}