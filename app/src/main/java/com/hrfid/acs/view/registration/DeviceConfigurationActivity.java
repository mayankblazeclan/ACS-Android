package com.hrfid.acs.view.registration;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.components.FontAwesomeIcons;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.interfaces.ServerDetails;
import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.server.ServerDetailsImpl;
import com.hrfid.acs.service.api.comfigration.ConfigurationApi;
import com.hrfid.acs.service.api.comfigration.ConfigurationDevice;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.view.activity.NotificationActivity;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.dialog.AlertDialogFragment;
import com.hrfid.acs.view.dialog.AlertDialogInterface;
import com.hrfid.acs.view.licenses.LicenseKeyActivity;

public class DeviceConfigurationActivity extends BaseActivity implements
        View.OnClickListener, AlertDialogInterface,
        ConfigurationApi.ConfigurationAPIInterface {

    private final String TAG = getClass().getSimpleName();

    private ConfigurationApi configurationApi;

    Toolbar toolbar;
    RelativeLayout mProgressBarLayout;
    EditText mEtApiUrlDeviceSettings;
    Button mImgBtnTestConnection;
    SharedPrefsManager mPref;

    String mApiUrl = "";
    String mCurrServerVersion = "";
    boolean isRegistered = false;
    String mLicenseKey = "";
    private TextView mTopbarTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_configration);


     /*   toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setLogo(R.drawable.toolbar_icon_settings);
        setSupportActionBar(toolbar);
        */
        toolbar = findViewById(R.id.top_bar);
        initToolbar();

        setTollBarTitle(getString(R.string.tb_configuration));


        String fromActivity = getIntent().getStringExtra("activityName");
        Log.v("", "fromActivity = " + fromActivity);
        if (fromActivity != null) {
            if (fromActivity.equals("main")) {
                toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
            } else if (fromActivity.equals("splash")) {
                //oolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            }
        } else {
            //do nothing
        }

//sharedpref
        mPref = new SharedPrefsManager();
        isRegistered = mPref.isRegistered(this);
        mLicenseKey = mPref.getLicenseKey(this);
        mCurrServerVersion = mPref.getServerVersion(this);
        mProgressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_relativelayout);
        mEtApiUrlDeviceSettings = (EditText) findViewById(R.id.editApiUrlDeviceSettings);

        if(!mPref.getApiUrl(this).equalsIgnoreCase("") || !mPref.getApiUrl(this).isEmpty()) {
            mEtApiUrlDeviceSettings.setText(mPref.getApiUrl(this));
        }else {
            mEtApiUrlDeviceSettings.setText("10.30.10.110:8080/");
        }

        //button
        mImgBtnTestConnection = (Button) findViewById(R.id.bt_connect);
        mImgBtnTestConnection.setOnClickListener(this);

        configurationApi = new ConfigurationApi(this);

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mTopbarTV = toolbar.findViewById(R.id.top_bar_title);
        FontAwesomeIcons mSettings = toolbar.findViewById(R.id.top_bar_settings);
        mSettings.setOnClickListener(this);
    }

    public void setTollBarTitle(String value) {
        mTopbarTV.setText(value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showDialog(R.string.alert_message_back, false);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(int alertMsg, boolean hideCancel) {
        DialogFragment dialog = AlertDialogFragment.newInstance(
                R.string.alert_title_info, alertMsg, hideCancel);
        dialog.show(getSupportFragmentManager(), "dialog");

//        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_connect:
                mApiUrl = mEtApiUrlDeviceSettings.getText().toString();
                if (mApiUrl != null && !mApiUrl.isEmpty()) {
                    mProgressBarLayout.setVisibility(View.VISIBLE);
                    hideKeyboard();

                    mImgBtnTestConnection.setEnabled(false);

//                    healthCheck.callAPI(mApiUrl);
                    fetchGetHealthCheck(mApiUrl);
                } else {
                    hideKeyboard();
                    showToastMessage("Api url is required!");
                }


                break;
        }
    }

    private void fetchGetHealthCheck(String controlPoint) {


        configurationApi.CallAPI(controlPoint);
    }

    //hide keyboard
    private void hideKeyboard() {
        if (mEtApiUrlDeviceSettings != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEtApiUrlDeviceSettings.getWindowToken(), 0);
        }
    }

    @Override
    public void doPositiveClick() {
        ServerDetails serverDetails = new ServerDetailsImpl();
        Intent intent;

        if (serverDetails.isLowerVersion(mCurrServerVersion)) {
            if (isRegistered) {
                intent = new Intent(DeviceConfigurationActivity.this, SelectRoleActivity.class);
            } else {
                intent = new Intent(DeviceConfigurationActivity.this, DeviceConfigurationActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            fetchGetLicenseKey();
        }
    }

    @Override
    public void doNegativeClick() {

    }

    private void fetchGetLicenseKey() {
        mProgressBarLayout.setVisibility(View.VISIBLE);
        mImgBtnTestConnection.setEnabled(false);
        //TODO API call for validateLicenseKey
        /*
        // temporary for client id
        int clientId = 1;
        App.getRestClient().getDeviceService().validateLicenseKey(clientId, mLicenseKey, new RestCallback<Status>() {
            @Override
            public void success(Status status, Response response) {
                Intent intent;
                if(isRegistered && status.getStatus().equals("success")) {
                    intent = new Intent(DeviceConfigurationActivity.this, MainActivity.class);
                }
                else{
                    intent = new Intent(DeviceConfigurationActivity.this, LicenseKeyActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(RestError restError) {
                showToastMessage(restError.getStrMessage());
                mProgressBarLayout.setVisibility(View.GONE);
                mImgBtnTestConnection.setEnabled(true);
            }
        });
    }
*/
    }

    @Override
    public void ConfigurationDeviceError(int errorCode) {
        LoggerLocal.error(TAG, "in ConfigurationDeviceError");
        mProgressBarLayout.setVisibility(View.GONE);
        mImgBtnTestConnection.setEnabled(true);
        //Utils.showAlertDialog(this, getString(R.string.something_went_wrong));
        Toast.makeText(this, R.string.msg_server_error_configration, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void ConfigurationDeviceResponse(ConfigurationDevice configurationDevice) {
        LoggerLocal.error(TAG, "in ConfigurationDeviceResponse");
        mProgressBarLayout.setVisibility(View.GONE);
        mImgBtnTestConnection.setEnabled(true);

        try {
            mPref.setServerVersion(getApplicationContext(), configurationDevice.getServer().get(0).getVersion());
            mPref.setApiUrl(getApplicationContext(), mApiUrl);
            //     Utils.showAlertDialog(this, getString(R.string.alert_message_configure_success));

            /*Intent intent = new Intent(DeviceConfigurationActivity.this, NotificationActivity.class);
            startActivity(intent);*/

            Intent intent = new Intent(DeviceConfigurationActivity.this, LicenseKeyActivity.class);
            intent.putExtra(Constants.KEY_ORG_ID,"1");
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
