package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.Kaftan.BiryaniFragment;
import com.angik.android.foodup_order.Kaftan.KhichuriFragment;
import com.angik.android.foodup_order.Kaftan.PolaoFragment;
import com.angik.android.foodup_order.Kaftan.RiceFragment;

public class MyAdapterKaftanLunch extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapterKaftanLunch(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BiryaniFragment();
            case 1:
                return new KhichuriFragment();
            case 2:
                return new RiceFragment();
            case 3:
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
