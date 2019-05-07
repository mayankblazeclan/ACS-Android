package com.hrfid.acs.components;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hrfid.acs.R;


public class BaseActivity extends AppCompatActivity {

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

}
