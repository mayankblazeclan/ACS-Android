package com.hrfid.acs.view.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.data.SharedPref;
import com.hrfid.acs.data.SharedPreferenceManager;
import com.hrfid.acs.service.br.ConnectivityReceiver;
import com.hrfid.acs.util.LoggerLocal;

public class SplashScreenPresenter implements SplashScreenTasks.Presenter {

    @NonNull
    private final SplashScreenTasks.View mView;
    private SharedPreferenceManager mPrefs;
    private Context mContext;
    private Activity mActivity;
    public SplashScreenPresenter(Context context, Activity activity,
                                 @NonNull SplashScreenTasks.View view,
                                 SharedPreferenceManager sharedPreferenceManager) {
        mView = view;
        mView.setPresenter(this);
        mPrefs = sharedPreferenceManager;
    }

    @Override
    public void subscribe() {
        // When start the presenter start/resume
    }

    @Override
    public void unsubscribe() {
        // When start the presenter pause/close
    }

    @Override
    public void loadProcess(int task) {
        switch (task) {
            case Constants.CHECK_CONN_TASK:
                checkNetworkConnection();
                break;

            case Constants.CHECK_REGISTRATION_TASK:
                // Check Registration
                checkDeviceRegistered();
                break;

            case Constants.CHECK_APP_PERMISSIONS:
                break;
        }
    }

    private void checkNetworkConnection() {
        if (ConnectivityReceiver.isConnected()) {
            // Next Step Is Device Registered
        //    checkDeviceRegistered();
        } else {
            mView.showDialog(Constants.DLG_INTERNET_CONNECTION, Constants.CHECK_CONN_TASK);
        }
    }

    private void checkDeviceRegistered() {
//        if (mPrefs.getValue(SharedPref.IS_DEV_REGISTERED, false)) {


        if (mPrefs.getValue(SharedPref.REGISTERED, false)) {
            // GO TO Main Activity
            mView.nextActivity(Constants.DEVICE_RFID_SCAN_ACTIVITY);
            LoggerLocal.error("SplashScreenPresenter","is register in if=="+mPrefs.getValue(SharedPref.REGISTERED, false));
        } else {
            mView.showDialog(Constants.DLG_DEV_NOT_REGISTERED, Constants.CHECK_REGISTRATION_TASK);
            LoggerLocal.error("SplashScreenPresenter","is register  in else=="+mPrefs.getValue(SharedPref.REGISTERED, false));
        }
    }

 /*   //app has all permission proceed ahead
    private boolean checkForPermissionAndRequest() {

        List<String> listPermissionNeeded = new ArrayList<>();

        for (String perm : appPermission) {
            if (ContextCompat.checkSelfPermission(mContext, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(perm);
            }
        }

        //Ask For Non Granted Permission

        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }*/
}