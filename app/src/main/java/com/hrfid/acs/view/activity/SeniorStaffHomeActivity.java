package com.hrfid.acs.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.serverResponses.GetNofication.GetNotificationResponse;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.StaffItemAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 08/05/19.
 */
public class SeniorStaffHomeActivity extends BaseActivity {


    private static final String TAG = "SeniorStaffHOME";
    GridView gridView;
    ArrayList<StaffItem> staffItemList=new ArrayList<>();
    private TextView txtViewCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_staff_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senior Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        initializeUI();
        //getNotification();

    }


    private void initializeUI() {


        gridView = (GridView) findViewById(R.id.simpleGridView);
        staffItemList.add(new StaffItem("Study Setup",R.drawable.ic_nurse_guidelines));
        staffItemList.add(new StaffItem("Subject Onboarding",R.drawable.ic_senior_onboarding));
        staffItemList.add(new StaffItem("TSU",R.drawable.ic_senior_tsu));
        staffItemList.add(new StaffItem("Inventory Setup",R.drawable.ic_senior_inventory_barcode));
        staffItemList.add(new StaffItem("Sample Intake",R.drawable.ic_nurse_sample_intake));

        StaffItemAdapter staffItemAdapter=new StaffItemAdapter(this,R.layout.activity_staff_grid_items, staffItemList);
        gridView.setAdapter(staffItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped On " + staffItemList.get(i).getTagName(), Toast.LENGTH_LONG).show();

                if(i==0){

                    //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped On Create Study", Toast.LENGTH_LONG).show();

                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SeniorStudySetupActivity.class);
                    startActivity(mNextActivity);


                } else if(i==1){

                    //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped OnBoarding", Toast.LENGTH_LONG).show();

                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SeniorSubjectOnBoardingActivity.class);
                    startActivity(mNextActivity);


                } else if(i==2){

                    //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped on TSU", Toast.LENGTH_SHORT).show();
                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, TSUSetupActivity.class);
                    startActivity(mNextActivity);


                } else if(i==3){

                    //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped on 3", Toast.LENGTH_SHORT).show();

                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, InventorySetupActivity.class);
                    startActivity(mNextActivity);


                } else if(i==4){

                    //Toast.makeText(SeniorStaffHomeActivity.this, "Tapped on 3", Toast.LENGTH_SHORT).show();

                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SampleIntakeSetupActivity.class);
                    startActivity(mNextActivity);


                }else {

                   // Toast.makeText(SeniorStaffHomeActivity.this, "Tapped On " + 2, Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Utils.createDialogTwoButtons(SeniorStaffHomeActivity.this, "Logout", true, getString(R.string.logout_message), "YES", "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (Utilities.isNetworkConnected(SeniorStaffHomeActivity.this)) {
                    callLogout();
                } else {
                    Utils.showAlertDialog(SeniorStaffHomeActivity.this, getString(R.string.no_internet_connection));
                }
            }
        }, null);
        // this.finish();
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();

        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        //txtViewCount.setText("10");
        getNotification();
        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtViewCount.setVisibility(View.GONE);
                //Toast.makeText(SeniorStaffHomeActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, NotificationActivity.class);
                startActivity(mNextActivity);
            }
        });
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtViewCount.setVisibility(View.GONE);
                //Toast.makeText(SeniorStaffHomeActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, NotificationActivity.class);
                startActivity(mNextActivity);
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Logout Functionality
        if (id == R.id.action_logout) {
            //Toast.makeText(SeniorStaffHomeActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
            Utils.createDialogTwoButtons(SeniorStaffHomeActivity.this, getString(R.string.settings_logout), true, getString(R.string.logout_message), getString(R.string.dlg_yes_text), getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Utilities.isNetworkConnected(SeniorStaffHomeActivity.this)) {
                        callLogout();
                    } else {
                        Utils.showAlertDialog(SeniorStaffHomeActivity.this, getString(R.string.no_internet_connection));
                    }


                    /*Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SelectRoleActivity.class);
                    startActivity(mNextActivity);
                    finish();*/
                }
            }, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Call Logout API
    private void callLogout() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(SeniorStaffHomeActivity.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.LOGOUT);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new LogoutRequest(SeniorStaffHomeActivity.this, true, commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("Logout API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("Logout API success " +
                                        commonResponse.getResponse().get(0).getMessage());


                                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                finish();

                            }else {

                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(SeniorStaffHomeActivity.this,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("Logout API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }



    //GetNotification API
    private void getNotification()
    {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(SeniorStaffHomeActivity.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        //commonRequestModel.setUserRole(Constants.SENIOR_STAFF);
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new GetNotificationRequest(SeniorStaffHomeActivity.this, true, commonRequestModel)) {

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


                                if(commonResponse.getTotalUnread()>0){
                                    txtViewCount.setVisibility(View.VISIBLE);
                                    txtViewCount.setText(""+commonResponse.getTotalUnread());
                                }else {
                                    txtViewCount.setVisibility(View.INVISIBLE);
                                }


                            }else {

                                txtViewCount.setText("");
                                txtViewCount.setVisibility(View.INVISIBLE);
                                Logger.logError("GetNofication API Failure Statis" +
                                        commonResponse.getStatus());
                                /*Logger.logError("GetNofication API Failure " +
                                        commonResponse.getNotifications().get(0).getDescription());*/

                                Utils.showAlertDialog(SeniorStaffHomeActivity.this,  commonResponse.getStatus().getMSG());
                            }

                        }else {
                            Logger.logError("GetNofication API Failure for not getting 200" +
                                    commonResponse.getStatus());
                            txtViewCount.setText("");
                            txtViewCount.setVisibility(View.INVISIBLE);
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                        txtViewCount.setVisibility(View.INVISIBLE);
                    }

                } else {
                    txtViewCount.setVisibility(View.INVISIBLE);
                    Logger.logError("GetNofication API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

}
