package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.FaisalUncle.FastFoodFragmentFaisal;
import com.angik.android.foodup_order.FaisalUncle.POMFragmentFaisal;
import com.angik.android.foodup_order.FaisalUncle.PithaFragmentFaisal;
import com.angik.android.foodup_order.FaisalUncle.PorotaFragmentFaisal;

@SuppressWarnings("ALL")
public class MyAdapterFaisal extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapterFaisal(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FastFoodFragmentFaisal();
            case 1:
                return new PorotaFragmentFaisal();
            case 2:
                return new PithaFragmentFaisal();
            case 3:
                return new POMFragmentFaisal();
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