package com.hrfid.acs.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.activity.SeniorStaffHomeActivity;


public class BaseActivity extends AppCompatActivity {

    static Handler handler;
    static Runnable runnable;

    /*public Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Senior Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(BaseActivity.this, "Back Pressed", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            Toast.makeText(BaseActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
            return true;
        }
        else  if (id == R.id.action_logout) {
            Toast.makeText(BaseActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    /**
     * Toolbar Setup.
     * This will enable a toolbar on your application
     * Add the layout on your activity layout
     * if you wish to used this
     *     <include layout="@layout/toolbar_layout" android:id="@+id/header" />
     *
     * @param title {Integer} Title added on string.xml
     * @param isBack {Boolean} If back button is enable
     * @param isSettings {Boolean} If setting button is enable
     */
   /* public void toolbarSetup(int title, boolean isBack, boolean isSettings) {
        Toolbar mToolbar = findViewById(R.id.header);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView mTopbarTV = (TextView) mToolbar.findViewById(R.id.top_bar_title);
        mTopbarTV.setText(title);

        if (isBack) {
            FontAwesomeIcons mBack = (FontAwesomeIcons) mToolbar.findViewById(R.id.top_bar_back);
            mBack.setVisibility(View.VISIBLE);
            mBack.setOnClickListener(__ -> finish());
        }

        if (isSettings) {
            FontAwesomeIcons mSetting = (FontAwesomeIcons) mToolbar.findViewById(R.id.top_bar_settings);
            mSetting.setVisibility(View.VISIBLE);
            mSetting.setOnClickListener(__ -> showSettings());
        }
    }*/

    
    /**
     * Show Settings Activity
     */
  /*  private void showSettings() {
        Intent mSettingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(mSettingsActivity);
    }*/


   /* public static void startIdleTimeOut(final Activity activity) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // manageBackup(true,false);
                Utils.showAlertDialog(activity, "u r logged Out....");
            }
        };
    }

    public void stopHandler() {
        handler.removeCallbacks(runnable);
    }
    public void startHandler() {
        handler.postDelayed(runnable, 15*60*1000); //for 15 minutes
    }*/

}
