package com.hrfid.acs.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.InventorySetupAdapter;
import com.hrfid.acs.view.adapter.SubjectOnBoardingAdapter;

/**
 * Created by MS on 2019-05-30.
 */
public class InventorySetupActivity extends BaseActivity {

    private static final String TAG = "InventorySetupActivity";
    ViewPager viewPager;
    //SearchView searchView;
    //MenuItem searchViewItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_study_setup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Inventory Setup");
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

        //Tab Layout for Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Add KIT"));
        tabLayout.addTab(tabLayout.newTab().setText("KIT Details"));
        tabLayout.setTabTextColors(    ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.white));
        //tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager =(ViewPager)findViewById(R.id.view_pager);
        InventorySetupAdapter tabsAdapter = new InventorySetupAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() ==1 ){
                    final InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);

                   // searchView.setVisibility(View.VISIBLE);
                  //  searchViewItem.setVisible(true);
                }else {
                  //  searchView.setVisibility(View.INVISIBLE);
                  //  searchViewItem.setVisible(false);
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() ==1 ){
                    final InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                  //  searchView.setVisibility(View.VISIBLE);
                   // searchViewItem.setVisible(true);
                }else {
                   // searchView.setVisibility(View.INVISIBLE);
                   // searchViewItem.setVisible(false);
                }

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() ==1 ){
                    final InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                   // searchView.setVisibility(View.VISIBLE);
                   // searchViewItem.setVisible(true);
                }else {
                  //  searchView.setVisibility(View.INVISIBLE);
                  //  searchViewItem.setVisible(false);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_with_search_logout, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_main, menu);
        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();
        notificaitons.setVisibility(View.GONE);
        menu.findItem(R.id.action_notification).setVisible(false);
        return true;*/

       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_with_search_logout, menu);
        searchViewItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        searchView.setVisibility(View.INVISIBLE);
        searchViewItem.setVisible(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             *//*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*//*
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });*/

        //return super.onCreateOptionsMenu(menu);
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
            Utils.createDialogTwoButtons(InventorySetupActivity.this, getString(R.string.settings_logout), true, getString(R.string.logout_message), getString(R.string.dlg_yes_text), getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (Utilities.isNetworkConnected(InventorySetupActivity.this)) {
                        callLogout();
                    } else {
                        Utils.showAlertDialog(InventorySetupActivity.this, getString(R.string.no_internet_connection));
                    }

                }
            }, null);
            return true;
        }

        if (id == R.id.app_bar_search) {
            //Toast.makeText(SeniorStaffHomeActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
            Intent mNextActivity = new Intent(InventorySetupActivity.this, SearchKitActivity.class);
            startActivity(mNextActivity);
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
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(InventorySetupActivity.this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.LOGOUT);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new LogoutRequest(InventorySetupActivity.this, true, commonRequestModel)) {

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


                                Intent mNextActivity = new Intent(InventorySetupActivity.this, SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                finish();

                            }else {

                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("Logout API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(InventorySetupActivity.this,  commonResponse.getResponse().get(0).getMessage());
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
}
