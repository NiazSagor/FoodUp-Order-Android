package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.BreakfastFragment;
import com.angik.android.foodup_order.LunchFragment;

public class MyAdapter_4 extends FragmentPagerAdapter {

    private Context mContext;
    private int mTotalTabs;

    public MyAdapter_4(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        mContext = context;
        mTotalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BreakfastFragment();
            case 1:
                return new LunchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}
