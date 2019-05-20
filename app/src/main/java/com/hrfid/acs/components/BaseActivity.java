package com.hrfid.acs.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.activity.SeniorStaffHomeActivity;

import java.util.concurrent.TimeUnit;


public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private static android.os.Handler handler = new android.os.Handler();
    private static Runnable runnable = null;
    private static Runnable runnable2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (handler == null) {
            handler = new Handler();
        } else {
            handler.removeCallbacks(runnable);
            handler.removeCallbacks(runnable2);
        }
        if (runnable == null)
            runnable = new Runnable() {
                @Override
                public void run() {
                    //do your task here

                    Log.e(TAG, "User will be LOGOUT ....");
                    final AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(BaseActivity.this);

                    // Setting Dialog Title
                    alertDialog1.setTitle("Inactive Session");

                    // Setting Dialog Message
                    alertDialog1.setMessage("You will be automatically logged out after 5 min");

                    // Setting Icon to Dialog
                    alertDialog1.setIcon(R.drawable.ic_error);
                    // Setting Positive "Yes" Button
                    alertDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           //dialog.dismiss();
                           /* Toast.makeText(BaseActivity.this, "OK tapped", Toast.LENGTH_LONG).show();
                            Intent mNextActivity = new Intent(BaseActivity.this, SelectRoleActivity.class);
                            startActivity(mNextActivity);
                            finish();*/

                        }
                    });


                    // Showing Alert Message
                    //alertDialog1.show();

                        alertDialog1.show();
                    //}

                }
            };


        if (runnable2 == null)
            runnable2 = new Runnable() {
                @Override
                public void run() {
                    //do your task here

                    Log.e(TAG, "You are LOGOUT ....");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaseActivity.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Logout");

                    // Setting Dialog Message
                    alertDialog.setMessage("You are logout, Please re-login to continue");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.ic_error);
                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });


                    // Showing Alert Message
                    //alertDialog.show();

                    try {
                        alertDialog.show();
                    }
                    catch (WindowManager.BadTokenException e) {
                        //use a log message
                        e.printStackTrace();
                    }
                }
            };
        start();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stop();
        start();

    }

    void start() {
        Log.e(TAG, "Timer Started");
        handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(1));
        handler.postDelayed(runnable2, TimeUnit.MINUTES.toMillis(2));
    }

    void stop() {
        Log.e(TAG, "Timer Stopped");
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable2);
    }

}
