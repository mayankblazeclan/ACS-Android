package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.ResetNotificationCountRequest;
import com.hrfid.acs.helpers.serverResponses.GetNofication.GetNotificationResponse;
import com.hrfid.acs.helpers.serverResponses.GetNofication.Notification;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.NotificationItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MS on 08/05/19.
 */
public class SearchSubjectActivity extends BaseActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static List<Notification> data;
    private LinearLayout linearLayout;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notification");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        initializeUI();

        //getNotification();

        //resetNotificationCount();

    }

    private void initializeUI() {


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
