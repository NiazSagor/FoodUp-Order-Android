package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.Shaowal.PolaoFragment;
import com.angik.android.foodup_order.Shaowal.RiceFragment;

public class MyAdapterShaowalLunch extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapterShaowalLunch(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RiceFragment();
            case 1:
                return new PolaoFragment();
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
