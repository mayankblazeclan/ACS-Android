package com.hrfid.acs.view.registration;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.data.SharedPreferenceManager;
import com.hrfid.acs.util.FragmentUtils;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.registration.server.ServerFragment;
import com.hrfid.acs.view.registration.server.ServerPresenter;


public class RegistrationActivity extends BaseActivity implements
        ServerFragment.OnFragmentListener {

    private static final String TAG = "RegistrationActivity";
    private SharedPreferenceManager mPrefs;
    private int mRegFrag;

    //RK

    private Fragment mFragment;
    private ServerFragment serverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // SETUP TOOLBAR
        toolbarSetup(R.string.device_registration_title, false, false);

        mRegFrag = getIntent().getIntExtra(Constants.REG_ACTIVITY_FRAG, Constants.REG_SERVER_FRAG);
        mPrefs = new SharedPreferenceManager(this);
        mFragment = new ServerFragment();
        serverFragment = (ServerFragment)
                getSupportFragmentManager().findFragmentById(R.id.splashscreen_frame);

        serverFragment = ServerFragment.newInstance();

        setFragment();
//        setFragmentDisplay(mFragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }



    private void setFragment() {
        switch (mRegFrag) {
            case Constants.REG_SERVER_FRAG:
                setServerFragment();
                break;
        }
    }

    private void setServerFragment() {
        ServerFragment mFragment = (ServerFragment)
                getSupportFragmentManager().findFragmentById(R.id.splashscreen_frame);

        mFragment = ServerFragment.newInstance();
        FragmentUtils.addFragmentToActivity(
                getSupportFragmentManager(), serverFragment, R.id.registration_frame, true);

        new ServerPresenter(serverFragment, mPrefs);
    }

    /**
     * Insert/View Fragment
     *
     * @param mReFragment fragment to be displayed
     */
    private void setFragmentDisplay(Fragment mReFragment) {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.registration_frame, mReFragment)
                .commit();
    }

    @Override
    public void serverNext(int activity) {
        nextActivity(activity);
    }

    /**
     * Next activity
     * @param nextActivity
     */
    private void nextActivity(int nextActivity) {
        Intent mNextActivity = null;
        switch (nextActivity) {
            case Constants.MAIN_ACTIVITY:
                mNextActivity = new Intent(RegistrationActivity.this, SelectRoleActivity.class);
                mNextActivity.putExtra(Constants.MAIN_ACTIVITY_FRAG, Constants.MAIN_SERVER_FRAG);
                break;

            /*case Constants.USER_AUTH_ACTIVITY:
                mNextActivity = new Intent(RegistrationActivity.this, UserAuthActivity.class);
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
