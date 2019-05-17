package com.hrfid.acs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.hrfid.acs.view.activity.BarcodeScanActivity;

/**
 * Created by MS on 25/04/18.
 */

public class PrefManager {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String PREFERENCES_FILE = "ACS_App";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void saveLoginDetails(String userName, String userRoleType, boolean isLogin, String barCodeValue) {
        editor.putString("userName", userName);
        editor.putString("userRoleType", userRoleType);
        editor.putBoolean("isLogin", isLogin);
        editor.putString("barCodeValue", barCodeValue);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString("userName", "");
    }

    public String getUserRoleType() {
        return sharedPreferences.getString("userRoleType", "");
    }

    public String getBarCodeValue() {
        return sharedPreferences.getString("barCodeValue", "");
    }

    public boolean getIsLogin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLogin", false);
    }

 /*   public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }*/

}
