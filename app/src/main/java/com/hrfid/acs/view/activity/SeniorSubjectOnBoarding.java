package com.hrfid.acs.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.SeniorStudySetupAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 2019-05-30.
 */
public class SeniorSubjectOnBoarding extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SeniorSubjectOnBoarding";
    String[] spnBloodGroup = {"O+","B-","B+", "A+", "A-"};

    String[] spnStudyID = {"10012","10011","10010", "10015", "10016"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_on_boarding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Subject Onboarding");
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
    }

    private void initializeUI() {

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spnBloodGroups = (Spinner) findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);

        Spinner spnStudyIDs = (Spinner) findViewById(R.id.spnStatusId);
        spnStudyIDs.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(SeniorSubjectOnBoarding.this,android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter studyIdAdp = new ArrayAdapter(SeniorSubjectOnBoarding.this,android.R.layout.simple_spinner_item, spnStudyID);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();
        notificaitons.setVisibility(View.GONE);

        menu.findItem(R.id.action_notification).setVisible(false);
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
            Utils.createDialogTwoButtons(SeniorSubjectOnBoarding.this, getString(R.string.settings_logout), true, getString(R.string.logout_message), getString(R.string.dlg_yes_text), getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Utilities.isNetworkConnected(SeniorSubjectOnBoarding.this)) {
                        callLogout();
                    } else {
                        Utils.showAlertDialog(SeniorSubjectOnBoarding.this, getString(R.string.no_internet_connection));
                    }


                    /*Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SelectRoleActivity.class);
                    startActivity(mNextActivity);
                    finish();*/
                }
            }, null);
            return true;
        }
        if (id == R.id.action_notification) {

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
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(SeniorSubjectOnBoarding.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.LOGOUT);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new LogoutRequest(SeniorSubjectOnBoarding.this, true, commonRequestModel)) {

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


                                Intent mNextActivity = new Intent(SeniorSubjectOnBoarding.this, SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                finish();

                            }else {

                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(SeniorSubjectOnBoarding.this,  commonResponse.getResponse().get(0).getMessage());
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SeniorSubjectOnBoarding.this,spnBloodGroup[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
