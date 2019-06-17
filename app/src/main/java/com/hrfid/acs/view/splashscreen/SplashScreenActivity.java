package com.hrfid.acs.view.splashscreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.data.SharedPreferenceManager;
import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.util.FragmentUtils;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.view.activity.BarcodeScanActivity;
import com.hrfid.acs.view.activity.NotificationActivity;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.registration.DeviceConfigurationActivity;


public class SplashScreenActivity extends AppCompatActivity implements SplashScreenFragment.SplashScreenInterface {

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        SplashScreenFragment splashScreenFragment =
                (SplashScreenFragment) getSupportFragmentManager().findFragmentById(R.id.splashscreen_frame);

        if (splashScreenFragment == null) {
            splashScreenFragment = SplashScreenFragment.newInstance();
            FragmentUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashScreenFragment, R.id.splashscreen_frame, true);
        }

        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this);

        new SplashScreenPresenter(this, SplashScreenActivity.this, splashScreenFragment, sharedPreferenceManager);

        SharedPrefsManager spfManager = new SharedPrefsManager();
        LoggerLocal.error(TAG, "in OnCreate spm register ="+spfManager.isRegistered(this));

    }

    @Override
    public void nextActivity(int nextActivity) {
        Intent mNextActivity = null;
        switch (nextActivity) {
           case Constants.REGISTRATION_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, BarcodeScanActivity.class);
                mNextActivity.putExtra(Constants.REG_ACTIVITY_FRAG, Constants.REG_SERVER_FRAG);
                break;

            case Constants.MAIN_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, SelectRoleActivity.class);
                mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
                break;

            case Constants.DEVICE_REGISTRATION_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, DeviceConfigurationActivity.class);
                mNextActivity.putExtra(Constants.REG_ACTIVITY_FRAG, Constants.REG_SERVER_FRAG);
                break;

             /*case Constants.USER_AUTH_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, UserAuthActivity.class);
                break;

            case Constants.DEVICE_REGISTRATION_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, DeviceConfigurationActivity.class);
                mNextActivity.putExtra(Constants.REG_ACTIVITY_FRAG, Constants.REG_SERVER_FRAG);
                break;
            case Constants.DEVICE_RFID_SCAN_ACTIVITY:
                mNextActivity = new Intent(SplashScreenActivity.this, BarcodeScanActivity.class);
                mNextActivity.putExtra(Constants.REG_ACTIVITY_FRAG, Constants.REG_SERVER_FRAG);

                break;*/


            default:
                finish();
                break;
        }

        if (mNextActivity != null) {
            startActivity(mNextActivity);
            finish();
        }
    }


}
