package com.angik.android.foodup_order.Model;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.angik.android.foodup_order.AcadFragment;
import com.angik.android.foodup_order.AdminFragment;
import com.angik.android.foodup_order.BBAFragment;
import com.angik.android.foodup_order.LBFragment;
import com.angik.android.foodup_order.SBFragment;
import com.angik.android.foodup_order.SelfPickUpFragment;
import com.angik.android.foodup_order.ShariahFragment;

@SuppressWarnings("ALL")
public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    private int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SelfPickUpFragment();
            case 1:
                return new SBFragment();
            case 2:
                return new LBFragment();
            case 3:
                return new BBAFragment();
            case 4:
                return new AdminFragment();
            case 5:
                return new AcadFragment();
            case 6:
                return new ShariahFragment();
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
