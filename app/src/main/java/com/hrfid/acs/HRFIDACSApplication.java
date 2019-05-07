package com.hrfid.acs;

import android.app.Application;
import android.support.annotation.NonNull;

import com.hrfid.acs.service.br.ConnectivityReceiverInterface;
import com.hrfid.acs.util.LoggerLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MS on 07/05/19.
 */
public class HRFIDACSApplication extends Application {

    private static final String TAG = HRFIDACSApplication.class.getSimpleName();

    private static HRFIDACSApplication mInstance;

    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;
        /**
         *
         *   set in-app defaults
         */


       /* FirebaseApp.initializeApp(mInstance);
        Fabric.with(this, new Crashlytics());
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String, Object> remoteConfigDefaults = new HashMap<>();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, true);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.1");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL, "https://play.google.com/store/apps/details?id=com.healthrfid.blood.control");

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(1)     // fetch every 60  min
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            LoggerLocal.error(TAG, "in successfully fetched firebase config details");
                            firebaseRemoteConfig.activateFetched();
                        }
                    }
                });*/





    }

    public static synchronized HRFIDACSApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiverInterface listener) {
        ConnectivityReceiverInterface mCRListener = listener;
    }
}
