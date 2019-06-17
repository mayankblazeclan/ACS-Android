package com.hrfid.acs.view.licenses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.components.FontAwesomeIcons;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.data.SharedPreferenceManager;
import com.hrfid.acs.interfaces.ServerDetails;
import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.server.ServerDetailsImpl;
import com.hrfid.acs.service.api.comfigration.ConfigurationApi;
import com.hrfid.acs.service.api.comfigration.ConfigurationDevice;
import com.hrfid.acs.service.api.licensesapi.LicenseKeyApi;
import com.hrfid.acs.service.api.licensesapi.LicenseKeyData;
import com.hrfid.acs.util.ForceUpdateChecker;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.NotificationActivity;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.dialog.AlertDialogFragment;
import com.hrfid.acs.view.dialog.AlertDialogInterface;
import com.hrfid.acs.view.licenses.LicenseKeyActivity;
import static com.hrfid.acs.data.SharedPref.REGISTERED;

import java.util.Map;

public class LicenseKeyActivity extends BaseActivity implements
        View.OnClickListener,
        AlertDialogInterface,
        LicenseKeyApi.LicenseKeyAPIInterface,
        ForceUpdateChecker.OnUpdateNeededListener{

    private final String TAG = getClass().getSimpleName();

    private LicenseKeyApi licenseKeyApi;

    private SharedPrefsManager spfManager;

    private RelativeLayout mProgressBarLayout;
    private EditText etLicenseKey;
    private Button btNext;
    private Button btExit;

    private int orgId = 0;
    private boolean mIsDeviceRegistered = false;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_key);
        initUI();
        Bundle bundle = getIntent().getExtras();

        orgId = bundle.getInt(Constants.KEY_ORG_ID);
        spfManager = new SharedPrefsManager();
        context = LicenseKeyActivity.this;

        licenseKeyApi = new LicenseKeyApi(context, this, spfManager);
    }

    private void initUI() {
        mProgressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_relativelayout);
        etLicenseKey = (EditText) findViewById(R.id.etLicenseKey);
        btNext = (Button) findViewById(R.id.bt_next);
        btExit = (Button) findViewById(R.id.bt_exit);

        //set Listener
        btNext.setOnClickListener(this);
        btExit.setOnClickListener(this);

        btNext.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                String licenseKey = "" + etLicenseKey.getText().toString();
                if (Utils.isNotNullOrEmpty(licenseKey)) {
                    String url = spfManager.getApiUrl(getApplicationContext());
                    LoggerLocal.error(TAG, "Calling API =" + url);
                    mProgressBarLayout.setVisibility(View.VISIBLE);
                    btNext.setEnabled(false);
                    licenseKeyApi.callApi(url, orgId, licenseKey);
                } else {
                    Utils.showToast(this, "please enter license key.");
                }
                break;

            case R.id.bt_exit:

               // startActivity(new Intent(this, SplashScreenActivity.class));
                finish();
                System.exit(0);
                break;
        }
    }

    @Override
    public void onLicenseKeyError(int errorCode) {
        mProgressBarLayout.setVisibility(View.GONE);
        btNext.setEnabled(true);
        Utils.showAlertDialog(this, getString(R.string.something_went_wrong));
    }


    @Override
    public void onLicenseKeyResponse(LicenseKeyData licenseKeyData) {

        mProgressBarLayout.setVisibility(View.GONE);
        btNext.setEnabled(true);
        if (null != licenseKeyData) {
            if (licenseKeyData.getResponse().get(0).isLicenseKeyValid()) {
                SharedPreferenceManager setSharedValue = new SharedPreferenceManager(this);
              /*  String url = spfManager.getApiUrl(getApplicationContext());
                LoggerLocal.error(TAG, "Calling API =" + url);

                String deviceWifiMac = "" + Utils.getDeviceWifiMacAddress(this);
                Map<String, Object> fields = Utils.getDeviceManufactureData();
                String serialNumber = "" + fields.get(Constants.DEVICE_SERIAL).toString();
                String deviceId = "" + serialNumber + deviceWifiMac;
                LoggerLocal.error(TAG, "deviceId=" + deviceId);
                licenseKeyApi.apiIsRegisterDevice(url, orgId, deviceId);*/

                spfManager.setRegistered(LicenseKeyActivity.this, true);
                setSharedValue.setSharedValue(REGISTERED, true);
                goToNextScreen(Constants.MAIN_ACTIVITY);


            } else {
                Utils.showAlertDialog(this, getString(R.string.invalid_license_key));
            }
        } else {
            LoggerLocal.error(TAG, "LicenseKeyData is empty");
        }
    }

    @Override
    public void onIsDeviceRegisteredError(int errorCode) {
        mProgressBarLayout.setVisibility(View.GONE);
        btNext.setEnabled(true);
        Utils.showAlertDialog(this, getString(R.string.something_went_wrong));
    }

    @Override
    public void onIsDeviceRegisteredResponse(boolean isDeviceRegistered) {

        mIsDeviceRegistered = isDeviceRegistered;
        mProgressBarLayout.setVisibility(View.GONE);
        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        LoggerLocal.error(TAG, "Success Device is ");


    }

    @Override
    public void doPositiveClick() {
        Utils.showToast(this, "Clicked OK on Dialog");
    }

    @Override
    public void doNegativeClick() {
        Utils.showToast(this, "Clicked No on Dialog");

    }

    private void goToNextScreen(int activity) {
        switch (activity) {
            case Constants.DEVICE_RFID_SCAN_ACTIVITY:
                Intent intent = new Intent(LicenseKeyActivity.this, SelectRoleActivity.class);
                startActivity(intent);
                break;
            case Constants.MAIN_ACTIVITY:
                Intent intentDeviceName = new Intent(LicenseKeyActivity.this, SelectRoleActivity.class);
                startActivity(intentDeviceName);
                break;
        }

    }



    @Override
    public void OnNOUpdateNeeded() {
        if (mIsDeviceRegistered) {
            goToNextScreen(Constants.DEVICE_RFID_SCAN_ACTIVITY);
        } else {
            goToNextScreen(Constants.DEVICE_NAME_ACTIVITY);
        }
    }


    @Override
    public void OnUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage(R.string.update_require)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                if (mIsDeviceRegistered) {
                                    goToNextScreen(Constants.DEVICE_RFID_SCAN_ACTIVITY);
                                } else {
                                    goToNextScreen(Constants.DEVICE_NAME_ACTIVITY);
                                }
                            }
                        }).create();
        dialog.show();
    }


    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
