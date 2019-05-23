package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.ResetNotificationCountRequest;
import com.hrfid.acs.helpers.serverResponses.GetNofication.GetNotificationResponse;
import com.hrfid.acs.helpers.serverResponses.GetNofication.Notification;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.DataModel;
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
public class NotificationActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static List<Notification> data;
    private LinearLayout linearLayout;
    private TextView textView;


    static String[] nameArray = {"Senior staff has changed study schedule of Study-no/name from ‘old date’ to 'new date",
            "Outside time window from Lab receipt to centrifuge",
            "The tube is missing/extra",
            "Out of range sample processing requirements (e.g. if tube needs to clot/spin immediately/spun within 30 mins/frozen immediately) -- an alert is needed if something is going out of time window or about to be spun too early)",
            "Outside time window from centrifuge to aliquot",
            "Draw tubes do not match the aliquot tubes - alert if miss-matched/extra/missing",
            "Outside time window from to aliquot to fridge or freezer",
            "Needing to be frozen/refrigerated (e.g. if something needs to be frozen within 30 minutes of centrifugation)",
            "Outside time window from Lab receipt to fridge or freezer",
            "Outside time window from Nursing to Lab"};

    static String[] versionArray = {"Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Senior Staff"};

    static Integer[] drawableArray = {R.drawable.ic_nurse_staff, R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff,
            R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff, R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff,
            R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff, R.drawable.ic_senior_staff};

    static Integer[] id_ = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

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

        getNotification();

    }

    private void initializeUI() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        linearLayout = findViewById(R.id.llRec);
        textView = findViewById(R.id.txtNoNotification);

        /*data = new ArrayList<Notification>();
       *//* for (int i = 0; i < nameArray.length; i++) {
            data.add(new DataModel(
                   nameArray[i],
                    versionArray[i],
                    id_[i],
                    drawableArray[i]
            ));
        }*//*
        adapter = new NotificationItemAdapter(data);
        recyclerView.setAdapter(adapter);*/

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


    //GetNotification API
    private void getNotification()
    {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(NotificationActivity.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new GetNotificationRequest(NotificationActivity.this, true, commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetNotificationResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetNotificationResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getStatus().getMSG().equalsIgnoreCase("REQ_SUCCESS")){

                                Logger.logError("GetNofication API success " +
                                        commonResponse.getNotifications().get(0).getDescription());
                                Logger.logError("GetNofication API Total Unread " +
                                        commonResponse.getTotalUnread());


                                /*Intent mNextActivity = new Intent(NurseStaffHomeActivity.this, SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                finish();*/

                               // txtViewCount.setText(""+commonResponse.getTotalUnread());

                                if(commonResponse.getNotifications().size()>0) {

                                    linearLayout.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.GONE);
                                    data = new ArrayList<Notification>();
                                    data = commonResponse.getNotifications();
                                    adapter = new NotificationItemAdapter(data);
                                    recyclerView.setAdapter(adapter);
                                }else {
                                    linearLayout.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);

                                }

                                //resetNotificationCount();

                            }else {

                                //txtViewCount.setText("");
                                Logger.logError("GetNofication API Failure Statis" +
                                        commonResponse.getStatus());
                                /*Logger.logError("GetNofication API Failure " +
                                        commonResponse.getNotifications().get(0).getDescription());*/

                                Utils.showAlertDialog(NotificationActivity.this,  commonResponse.getStatus().getMSG());
                            }

                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("GetNofication API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }



    //resetNotificationCount API
    private void resetNotificationCount()
    {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(NotificationActivity.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new ResetNotificationCountRequest(NotificationActivity.this, true, commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetNotificationResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetNotificationResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getStatus().getMSG().equalsIgnoreCase("REQ_SUCCESS")){

                                Logger.logError("resetNotificationCount API success " +
                                        commonResponse.getNotifications().get(0).getDescription());
                                Logger.logError("resetNotificationCount API Total Unread " +
                                        commonResponse.getTotalUnread());


                                /*Intent mNextActivity = new Intent(NurseStaffHomeActivity.this, SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                finish();*/

                                // txtViewCount.setText(""+commonResponse.getTotalUnread());

                            }else {

                                //txtViewCount.setText("");
                                Logger.logError("resetNotificationCount API Failure Statis" +
                                        commonResponse.getStatus());
                                /*Logger.logError("GetNofication API Failure " +
                                        commonResponse.getNotifications().get(0).getDescription());*/

                                Utils.showAlertDialog(NotificationActivity.this,  commonResponse.getStatus().getMSG());
                            }

                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("resetNotificationCount API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }
}
