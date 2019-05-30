package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.view.adapter.SeniorStudySetupAdapter;
import com.hrfid.acs.view.adapter.StaffItemAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 2019-05-30.
 */
public class SeniorStudySetupActivity extends AppCompatActivity {

    private static final String TAG = "SeniorStaffHOME";
    GridView gridView;
    ArrayList<StaffItem> staffItemList=new ArrayList<>();
    private TextView txtViewCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_study_setup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Study Setup");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        initializeUI();
        //getNotification();

    }

    private void initializeUI() {

/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.label_w_rfid));
        getSupportActionBar().setTitle(getResources().getString(R.string.study_setup));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        //Tab Layout for Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Create Study"));
        tabLayout.addTab(tabLayout.newTab().setText("View  Screen Scheduled Study"));
        tabLayout.addTab(tabLayout.newTab().setText("View Trial Scheduled Study"));
        tabLayout.setTabTextColors(    ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.white));
        //tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager =(ViewPager)findViewById(R.id.view_pager);
        SeniorStudySetupAdapter tabsAdapter = new SeniorStudySetupAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), "FromSMS");
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    }
}
