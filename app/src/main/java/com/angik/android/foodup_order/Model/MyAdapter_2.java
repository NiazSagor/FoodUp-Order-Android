package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.BurgerFragment;
import com.angik.android.foodup_order.FastFoodFragment;
import com.angik.android.foodup_order.POMFragment;
import com.angik.android.foodup_order.RollFragment;
import com.angik.android.foodup_order.Samusa;

@SuppressWarnings("ALL")
public class MyAdapter_2 extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapter_2(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BurgerFragment();
            case 1:
                return new FastFoodFragment();
            case 2:
                return new POMFragment();
            case 3:
                return new RollFragment();
            case 4:
                return new Samusa();
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