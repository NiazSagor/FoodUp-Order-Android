package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.BeefFragment;
import com.angik.android.foodup_order.BiryaniFragment;
import com.angik.android.foodup_order.MejbaniFragment;
import com.angik.android.foodup_order.RiceFragment;

@SuppressWarnings("ALL")
public class MyAdapter_3 extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapter_3(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BeefFragment();
            case 1:
                return new RiceFragment();
            case 2:
                return new MejbaniFragment();
            case 3:
                return new BiryaniFragment();
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