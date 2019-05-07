package com.hrfid.acs.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class ForceUpdateChecker {


    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "force_update_required";
    public static final String KEY_CURRENT_VERSION = "force_update_current_version";
    public static final String KEY_UPDATE_URL = "force_update_store_url";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;


    public interface OnUpdateNeededListener {
        void OnUpdateNeeded(String updateUrl);

        void OnNOUpdateNeeded();
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForceUpdateChecker(Context context, OnUpdateNeededListener onUpdateNeededListener) {
        this.onUpdateNeededListener = onUpdateNeededListener;
        this.context = context;
    }

    /**
     * for check app require update or not
     */
    public void check() {
        /*final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
            String appVersion = Utils.getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);


            if (!TextUtils.equals(currentVersion, appVersion) && null != onUpdateNeededListener) {
                LoggerLocal.error(TAG, "check() in if server="+appVersion);
                onUpdateNeededListener.OnUpdateNeeded(updateUrl);
            }else
            {
                onUpdateNeededListener.OnNOUpdateNeeded();
                LoggerLocal.error(TAG, "NO UPDATE NEEDED CURRENT VERSION IS ="+currentVersion);
                LoggerLocal.error(TAG, "check() in else ="+currentVersion);
            }

            LoggerLocal.error(TAG, "check() currentVersion="+currentVersion);
            LoggerLocal.error(TAG, "check() appVersion="+appVersion);
            LoggerLocal.error(TAG, "check() updateUrl="+updateUrl);

        }else
        {
            onUpdateNeededListener.OnNOUpdateNeeded();
            LoggerLocal.error(TAG, "No Update required");
        }*/
    }




    public  static class Builder {
        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener)
        {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }


        public ForceUpdateChecker build(){
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check()
        {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();

            return forceUpdateChecker;

        }
    }

}


