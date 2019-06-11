package com.hrfid.acs.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.service.api.userrole.LoginRequestModel;
import com.hrfid.acs.service.api.userrole.UserRole;
import com.hrfid.acs.service.api.userrole.UserRoleService;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity implements
        UserRoleService.UserRoleInterface {
    private final String TAG = getClass().getSimpleName();

    private EditText txtBarcodeNumber;
    private Button btNext;

    private String tagId;
    private String mUserRole = "";
    private String mUserName = "";
    private boolean isTagIdValid = false;
    private UserRoleService mService;
    private RelativeLayout mProgressBarLayout;
    private String userRoleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bcuser_login);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initUI();
        getIntentData();
        mService = new UserRoleService(this);

        txtBarcodeNumber.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager inputMethod = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethod!= null) {
                    //txtBarcodeNumber.requestFocus();
                   /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/

                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    //txtBarcodeNumber.setFocusable(true);
                    //inputMethod.showSoftInput(txtBarcodeNumber, InputMethodManager.SHOW_IMPLICIT);
                }
                return true;
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                userRoleType = extras.getString(Constants.USER_ROLE_TYPE);
                Logger.log("userRoleType :"+userRoleType);
                Utilities.showToast(getApplicationContext(),userRoleType);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtBarcodeNumber.setText("");
        txtBarcodeNumber.setText("E00401502B32123E"); //E00401502B32123E   //E00401502B31ACBC
    }

    private void initUI() {
        mProgressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_relativelayout);

        btNext = (Button) findViewById(R.id.bt_next);
        txtBarcodeNumber = (EditText) findViewById(R.id.et_rfid_number);
        //txtBarcodeNumber.setText("E00401502B31ACBC");

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtBarcodeNumber.getText().length() >0 && txtBarcodeNumber.getText().length()<17){

                    tagId = txtBarcodeNumber.getText().toString();

                   // gotoNextActivity(userRoleType);

                    LoginRequestModel loginRequestModel = new LoginRequestModel();
                    loginRequestModel.setAppName(AppConstants.APP_NAME);
                    loginRequestModel.setVersionNumber(AppConstants.APP_VERSION);
                    loginRequestModel.setDeviceType(AppConstants.APP_OS);
                    loginRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
                    loginRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(BarcodeScanActivity.this));
                    //loginRequestModel.setDeviceNumber("D123456");
                    loginRequestModel.setUserRole(userRoleType);
                    loginRequestModel.setTagId(tagId);
                    loginRequestModel.setEvent(AppConstants.LOGIN_EVENT);

                if (Utilities.isNetworkConnected(BarcodeScanActivity.this)) {

                    //Calling Login API ....
                    //String url = "10.30.10.110:8080/";
                    String url = AppConstants.LOGIN_URL;
                    if (!TextUtils.isEmpty(tagId) && Constants.USER_CARD_TAG_LENGHT != tagId.length() - 1) {
                        mProgressBarLayout.setVisibility(View.VISIBLE);
                        mService.ApiCallGetUserRole(url, loginRequestModel);
                    } else {
                        // Utils.showToast(this, "tag ID=" + tagId + "\nLength =" + tagId.length());
                        Utils.showAlertDialog(BarcodeScanActivity.this, getString(R.string.please_scan_user_card_barcode));
                    }


                } else {

                    Utilities.showSnackBar(BarcodeScanActivity.this.findViewById(android.R.id.content),
                            getResources().getString(R.string.ic_not_connection_message));
                }

                }else {

                    //gotoNextActivity(userRoleType);
                    Utilities.showToast(getApplicationContext(),"Please enter valid input");

                }


                //gotoNextActivity(userRoleType);
            }
        });

       // btNext.setOnClickListener(this);

       // txtBarcodeNumber.setText("E00401502B32123E");
       // txtBarcodeNumber.setEnabled(false);

        //txtBarcodeNumber.setText("E00401502B32123E");
        txtBarcodeNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Utils.showToast(BarcodeScanActivity.this, "2"+s.toString());
                //LoggerLocal.error(TAG, "TAG ID 2=" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
               // LoggerLocal.error(TAG, "TAG ID 3 =" + s.toString());

            }
        });
    }

    private void gotoNextActivity(String userRoleType) {

        if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_SENIOR_STAFF)){

            //new PrefManager(this).setUserRoleType(Constants.USER_ROLE_TYPE_SENIOR_STAFF);
            //new PrefManager(this).setUserRoleType(Constants.USER_ROLE_TYPE_SENIOR_STAFF);
            //Go to next page of senior member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, SeniorStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);
            finish();

        }else if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_NURSE_STAFF)){

           // new PrefManager(this).setUserRoleType(Constants.USER_ROLE_TYPE_NURSE_STAFF);
            //Go to next page of nurse member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, NurseStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);
            finish();

        }else {

            //new PrefManager(this).setUserRoleType(Constants.LAB_STAFF);
            //Go to next page of lab member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, LabStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);
            finish();

        }
    }

    @Override
    public void onUserRoleResponse(List<UserRole> userRole) {
        mProgressBarLayout.setVisibility(View.GONE);
        for (int i = 0; i < userRole.size(); i++) {
            if (null != userRole.get(i).getUserRole()) {
                mUserRole = "" + userRole.get(i).getUserRole();
                isTagIdValid = userRole.get(i).isTagIdValid();
                mUserName = "" + userRole.get(i).getUserName();
            }
        }
        if(isTagIdValid) {
            if (mUserRole.equalsIgnoreCase(userRoleType)) {

                saveLoginDetails(mUserName, mUserRole, true, tagId);;
                gotoNextActivity(userRoleType);

            }else {
                txtBarcodeNumber.setText("");
                Utils.showAlertDialog(this, getString(R.string.user_role_not_define));
            }
        }else
        {
            txtBarcodeNumber.setText("");
            Utils.showAlertDialog(this, getString(R.string.user_role_not_define));
        }

    }

    @Override
    public void onError(int errorCode) {
        mProgressBarLayout.setVisibility(View.GONE);

        Utils.showAlertDialog(this, getString(R.string.something_went_wrong));
    }

    @Override
    public void onBackPressed() {
        Intent mNextActivity = new Intent(BarcodeScanActivity.this, SelectRoleActivity.class);
        startActivity(mNextActivity);
        finish();
    }

    private void saveLoginDetails(String userName, String userRoleType, boolean isLogin, String barCodeValue) {
        new PrefManager(BarcodeScanActivity.this).saveLoginDetails(userName, userRoleType, isLogin, barCodeValue);
    }
}
