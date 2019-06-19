package com.hrfid.acs.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    public static final long DISCONNECT_TIMEOUT = 1000000; // 5 min = 5 * 60 * 1000 ms =300000  //60000
    private static final String TAG = "BaseActivity";
    protected static int GRAVITY = 0;

    private Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // todo
            return true;
        }
    });

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
            //Toast.makeText(BaseActivity.this, "Logged out after 1 minutes on inactivity.", Toast.LENGTH_SHORT).show();

            Log.e(TAG, "User will be LOGOUT ....");
            new AlertDialog.Builder(BaseActivity.this)
                    .setTitle("Inactive Session")
                    .setMessage("You will be automatically logged out after 5 minutes")
                    .setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            stopDisconnectTimer();
                            resetDisconnectTimer();
                            dialog.dismiss();

                            /*Toast.makeText(BaseActivity.this, "OK tapped", Toast.LENGTH_LONG).show();
                            Intent mNextActivity = new Intent(BaseActivity.this, SelectRoleActivity.class);
                            startActivity(mNextActivity);
                            finish();*/
                        }
                    })
                    .setIcon(R.drawable.ic_error)
                    .show();
        }
    };

    private Runnable disconnectCallback2 = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
            //Toast.makeText(BaseActivity.this, "Logged out after 1 minutes on inactivity.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "You are LOGOUT ....");
            new AlertDialog.Builder(BaseActivity.this)
                    .setTitle("Logout")
                    .setMessage("You were logged out. Please re-login to continue.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            dialog.dismiss();
                            Toast.makeText(BaseActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
                            Intent mNextActivity = new Intent(BaseActivity.this, SelectRoleActivity.class);
                            startActivity(mNextActivity);
                            finish();
                        }
                    })
                    .setIcon(R.drawable.ic_error)
                    .show();
        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
        disconnectHandler.postDelayed(disconnectCallback2, 1600000);  //120000   //600000

    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.removeCallbacks(disconnectCallback2);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }


    public void toolbarSetup(int title, boolean isBack, boolean isSettings) {
        Toolbar mToolbar = findViewById(R.id.header);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView mTopbarTV = (TextView) mToolbar.findViewById(R.id.top_bar_title);
        mTopbarTV.setText(title);

        if (isBack) {
            FontAwesomeIcons mBack = (FontAwesomeIcons) mToolbar.findViewById(R.id.top_bar_back);
            mBack.setVisibility(View.VISIBLE);
           // mBack.setOnClickListener(__ -> );
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                }
            });
        }

        if (isSettings) {
            FontAwesomeIcons mSetting = (FontAwesomeIcons) mToolbar.findViewById(R.id.top_bar_settings);
            mSetting.setVisibility(View.VISIBLE);
           // mSetting.setOnClickListener(__ -> showSettings());
            mSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showSettings();
                }
            });
        }
    }


    /**
     * Show Settings Activity
     */
    private void showSettings() {
        Intent mSettingsActivity = new Intent(getApplicationContext(), SelectRoleActivity.class);
        startActivity(mSettingsActivity);
    }

    /**
     * This method will set the toast message
     * @param msg
     */
    public void showToastMessage(String msg){
        //set the toast message to the center of the screen
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(GRAVITY, 0, 0);
        toast.show();
    }

   /* private static final String TAG = "BaseActivity";
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


                           dialog.dismiss();
                            Toast.makeText(BaseActivity.this, "OK tapped", Toast.LENGTH_LONG).show();
                            Intent mNextActivity = new Intent(BaseActivity.this, SelectRoleActivity.class);
                            startActivity(mNextActivity);
                            finish();

                            stop();
                            start();

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
                            Toast.makeText(BaseActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
                            Intent mNextActivity = new Intent(BaseActivity.this, SelectRoleActivity.class);
                            startActivity(mNextActivity);
                            finish();

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
    }*/

}
