package com.angik.android.foodup_order;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.angik.android.foodup_order.Model.MyAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SBFragment.OnFragmentInteractionListener, LBFragment.OnFragmentInteractionListener,
        BBAFragment.OnFragmentInteractionListener, AdminFragment.OnFragmentInteractionListener,
        AcadFragment.OnFragmentInteractionListener, ShariahFragment.OnFragmentInteractionListener, SelfPickUpFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private SharedPreferences sp_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        sp_vendor = getSharedPreferences("vendor", MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Today's Order");

        viewPager = findViewById(R.id.view_pager);
        int defaultValue = 0;
        int page = getIntent().getIntExtra("TAB", defaultValue);
        viewPager.setCurrentItem(page);

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
        //Tab titles

        if (Objects.equals(sp_vendor.getString("currentVendor", null), "Khan")) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab());
        } else {
            tabLayout.addTab(tabLayout.newTab());
        }

        setupTabIcons(tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//Gravity of the titles
        tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorPrimary));
        //tabLayout.setTabRippleColorResource(R.color.lightGrey);//Adds ripple effect
        tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.lightGrey)));

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
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

    private void setupTabIcons(TabLayout mTabLayout) {
        String[] tabTitles;

        if (mTabLayout.getTabCount() == 1) {
            tabTitles = new String[]{"SELF"};
            Objects.requireNonNull(mTabLayout.getTabAt(0)).setCustomView(prepareTabView1(0));
        } else {
            tabTitles = new String[]{"SELF", "SB", "LB", "BBA", "ADM", "ACA", "SHARIAH"};
            for (int i = 0; i < tabTitles.length; i++) {
                Objects.requireNonNull(mTabLayout.getTabAt(i)).setCustomView(prepareTabView(i));
            }
        }
    }

    private View prepareTabView1(int pos) {
        String[] tabTitles = {"Self"};
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.tab_custom_view, null);
        TextView tv_title = view.findViewById(R.id.name);
        TextView tv_count = view.findViewById(R.id.count);
        tv_title.setText(tabTitles[pos]);
        setNumber1(tv_count, tabTitles[pos]);
        return view;
    }

    private View prepareTabView(int pos) {
        String[] tabTitles = {"Self", "Science", "Law", "BBA", "Admin", "Academic", "Shariah"};
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.tab_custom_view, null);
        TextView tv_title = view.findViewById(R.id.name);
        TextView tv_count = view.findViewById(R.id.count);
        tv_title.setText(tabTitles[pos]);
        setNumber(tv_count, tabTitles[pos]);
        return view;
    }

    private void setNumber1(final TextView textView, String name) {
        DatabaseReference databaseReference;

        switch (Objects.requireNonNull(sp_vendor.getString("currentVendor", null))) {
            case "Shaowal":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Shaowal");
                break;
            case "Kaftan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Kaftan");
                break;
            case "Khan":
                databaseReference = FirebaseDatabase.getInstance().getReference("Building");
                break;
            case "Kutub":
                databaseReference = FirebaseDatabase.getInstance().getReference("Order Kutub");
                break;

            default:
                databaseReference = FirebaseDatabase.getInstance().getReference("Building");
        }

        databaseReference.child("Pick Up From Store").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                    textView.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setNumber(final TextView textView, String name) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Building");

        switch (name) {
            case "Self":
                databaseReference.child("Pick Up From Store").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Science":
                databaseReference.child("Science Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Law":
                databaseReference.child("Law Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "BBA":
                databaseReference.child("BBA Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Admin":
                databaseReference.child("Admin Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Academic":
                databaseReference.child("Academic Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Shariah":
                databaseReference.child("Shariah Building").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String s = " " + "(" + dataSnapshot.getChildrenCount() + ")";
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}