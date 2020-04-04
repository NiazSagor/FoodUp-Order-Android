package com.angik.android.foodup_order;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.angik.android.foodup_order.Model.MyAdapter_3;

import java.util.Objects;

@SuppressWarnings("ALL")
public class SelectLunchActivity extends AppCompatActivity implements BeefFragment.OnFragmentInteractionListener,
        RiceFragment.OnFragmentInteractionListener, MejbaniFragment.OnFragmentInteractionListener, BiryaniFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;//Tab layout
    private Toolbar toolbar;//Toolbar where title is
    private ViewPager viewPager;//View pager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lunch);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Select Available Items");


        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Beef"));
        tabLayout.addTab(tabLayout.newTab().setText("Rice"));
        tabLayout.addTab(tabLayout.newTab().setText("Mejbani"));
        tabLayout.addTab(tabLayout.newTab().setText("Biryani"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//Gravity of the titles
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        //This defines the color for selected and unselected state
        tabLayout.setTabTextColors(Color.parseColor("#757575"), Color.parseColor("#03A9F4"));
        tabLayout.setTabRippleColorResource(R.color.lightGrey);//Adds ripple effect

        viewPager = findViewById(R.id.view_pager);
        int defaultValue = 0;
        int page = getIntent().getIntExtra("TAB", defaultValue);
        viewPager.setCurrentItem(page);

        final MyAdapter_3 adapter = new MyAdapter_3(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
