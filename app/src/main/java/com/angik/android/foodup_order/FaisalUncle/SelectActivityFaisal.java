package com.angik.android.foodup_order.FaisalUncle;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.angik.android.foodup_order.Model.MyAdapterFaisal;
import com.angik.android.foodup_order.R;

import java.util.Objects;

public class SelectActivityFaisal extends AppCompatActivity implements FastFoodFragmentFaisal.OnFragmentInteractionListener,
        POMFragmentFaisal.OnFragmentInteractionListener, PithaFragmentFaisal.OnFragmentInteractionListener, PorotaFragmentFaisal.OnFragmentInteractionListener {

    private TabLayout tabLayout;//Tab layout
    private Toolbar toolbar;//Toolbar where title is
    private ViewPager viewPager;//View pager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_faisal);

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
        tabLayout.addTab(tabLayout.newTab().setText("Fast Food"));
        tabLayout.addTab(tabLayout.newTab().setText("Porota"));
        tabLayout.addTab(tabLayout.newTab().setText("Pitha"));
        tabLayout.addTab(tabLayout.newTab().setText("Part Of Meal"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//Gravity of the titles
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        //This defines the color for selected and unselected state
        tabLayout.setTabTextColors(Color.parseColor("#757575"), Color.parseColor("#03A9F4"));
        tabLayout.setTabRippleColorResource(R.color.lightGrey);//Adds ripple effect

        viewPager = findViewById(R.id.viewpager);
        int defaultValue = 0;
        int page = getIntent().getIntExtra("TAB", defaultValue);
        viewPager.setCurrentItem(page);

        final MyAdapterFaisal adapter = new MyAdapterFaisal(this, getSupportFragmentManager(), tabLayout.getTabCount());
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
