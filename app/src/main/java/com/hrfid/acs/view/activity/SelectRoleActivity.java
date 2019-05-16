package com.hrfid.acs.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.util.LogOutTimerUtil;
import com.hrfid.acs.util.Utils;

/**
 * Created by MS on 08/05/19.
 */
public class SelectRoleActivity extends Activity implements View.OnClickListener, LogOutTimerUtil.LogOutListener {

    private static final String TAG = "SelectRoleActivity";
    private String strUserType;
    private CardView cardViewTechnicalStaff;
    private CardView cardViewNurseStaff;
    private CardView cardViewLabStaff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role_type);


        initializeUI();

    }

    private void initializeUI() {

        cardViewTechnicalStaff = findViewById(R.id.card_view_technical_staff);
        cardViewLabStaff = findViewById(R.id.card_view_lab_staff);
        cardViewNurseStaff = findViewById(R.id.card_view_nurse_staff);

        cardViewNurseStaff.setOnClickListener(this);
        cardViewLabStaff.setOnClickListener(this);
        cardViewTechnicalStaff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.card_view_technical_staff:
                strUserType = Constants.USER_ROLE_TYPE_SENIOR_STAFF;
                gotoLoginPage();
                break;

            case R.id.card_view_nurse_staff:
                strUserType = Constants.USER_ROLE_TYPE_NURSE_STAFF;
                gotoLoginPage();
                break;

            case R.id.card_view_lab_staff:
                strUserType = Constants.USER_ROLE_TYPE_LAB_STAFF;
                gotoLoginPage();
                break;

            default:
                break;
        }

    }

    private void gotoLoginPage() {
        Intent mNextActivity = new Intent(SelectRoleActivity.this, BarcodeScanActivity.class);
        mNextActivity.putExtra(Constants.USER_ROLE_TYPE, strUserType);
        startActivity(mNextActivity);
        finish();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        Utils.createDialogTwoButtons(SelectRoleActivity.this, "Exit", true, "Are you sure you want to exit from app?", "OK", "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // Intent mNextActivity = new Intent(SelectRoleActivity.this, SelectRoleActivity.class);
                //startActivity(mNextActivity);
                finish();
            }
        }, null);
        //this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogOutTimerUtil.startLogoutTimer(SelectRoleActivity.this, this);
        Log.e(TAG, "OnStart () &&& Starting timer");
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogOutTimerUtil.startLogoutTimer(SelectRoleActivity.this, this);
        Log.e(TAG, "User interacting with screen");
    }

    /**
     * Performing idle time logout
     */
    @Override
    public void doLogout() {
        // write your stuff here
        //Toast.makeText(SelectRoleActivity.this, "u r logged Out....", Toast.LENGTH_LONG).show();
        Utils.showAlertDialog(SelectRoleActivity.this, "u r logged Out....");
    }
}
