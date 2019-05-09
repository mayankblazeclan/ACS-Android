package com.hrfid.acs.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.service.api.userrole.UserRole;
import com.hrfid.acs.service.api.userrole.UserRoleService;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.LoggerLocal;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.lang.reflect.Method;
import java.util.List;

public class BarcodeScanActivity extends BaseActivity implements View.OnClickListener,
        UserRoleService.UserRoleInterface {
    private final String TAG = getClass().getSimpleName();

    private EditText etRFIDNumber;
    private Button btNext;

    private String tagId;
    private String mUserRole = "";
    private boolean isTagIdValid = false;
    private UserRoleService mService;
    private SharedPrefsManager spfManager;
    private RelativeLayout mProgressBarLayout;
    private String userRoleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bcuser_login);

        initUI();
        getIntentData();
        spfManager = new SharedPrefsManager();
        mService = new UserRoleService(this);

        etRFIDNumber.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager inputMethod = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethod!= null) {
                    inputMethod.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
       // etRFIDNumber.setText("");
    }

    private void initUI() {
        mProgressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_relativelayout);

        btNext = (Button) findViewById(R.id.bt_next);
        btNext.setOnClickListener(this);
        etRFIDNumber = (EditText) findViewById(R.id.et_rfid_number);
        etRFIDNumber.setEnabled(false);

      /*  etRFIDNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Utils.showToast(BarcodeScanActivity.this, "2"+s.toString());
                LoggerLocal.error(TAG, "TAG ID 2=" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoggerLocal.error(TAG, "TAG ID 3 =" + s.toString());

            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                tagId = etRFIDNumber.getText().toString();
//                String url = spfManager.getApiUrl(getApplicationContext());

                tagId = "E00401502B31ACBC";

                gotoNextActivity(userRoleType);


                //String url = Constants.SIT_STAGE_CONTROLPOINT + "." + Constants.CONTROLPOINT;
              /*  String url = "10.30.10.110:8080";
                if (!TextUtils.isEmpty(tagId) && Constants.USER_CARD_TAG_LENGHT != tagId.length() - 1) {
                    mProgressBarLayout.setVisibility(View.VISIBLE);
                    mService.ApiCallGetUserRole(url, tagId);
                } else {
                    Utils.showToast(this, "tag ID=" + tagId + "\nLength =" + tagId.length());
                    Utils.showAlertDialog(this, getString(R.string.please_scan_user_card_barcode));
                }*/

                break;
        }
    }

    private void gotoNextActivity(String userRoleType) {

        if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_SENIOR_STAFF)){

            //Go to next page of senior member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, SeniorStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);

        }else if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_NURSE_STAFF)){

            //Go to next page of nurse member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, NurseStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);

        }else {

            //Go to next page of lab member
            Intent mNextActivity = new Intent(BarcodeScanActivity.this, LabStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);

        }
    }

    @Override
    public void onUserRoleResponse(List<UserRole> userRole) {
        mProgressBarLayout.setVisibility(View.GONE);
        for (int i = 0; i < userRole.size(); i++) {
            if (null != userRole.get(i).getUserRole()) {
                mUserRole = "" + userRole.get(i).getUserRole();
                isTagIdValid = userRole.get(i).isTagIdValid();
            }
        }
        if(isTagIdValid) {
            if (mUserRole.equalsIgnoreCase(Constants.USER_ROLE_SYSTEM_ADMIN) || mUserRole.equalsIgnoreCase(Constants.USER_ROLE_ADMIN)) {
                if(spfManager.isRegistered(this)) {
                    goToNextScreen(Constants.MAIN_ACTIVITY);
                }else
                {
                    goToNextScreen(Constants.LOCATION_SELECT_ACTIVITY);
                }
            } else {
                etRFIDNumber.setText("");
                Utils.showAlertDialog(this, getString(R.string.user_role_not_define));
            }
        }else
        {
            etRFIDNumber.setText("");
            Utils.showAlertDialog(this, getString(R.string.user_role_not_define));
        }

    }

    @Override
    public void onError(int errorCode) {
        mProgressBarLayout.setVisibility(View.GONE);

        Utils.showAlertDialog(this, getString(R.string.something_went_wrong));
    }

    private void goToNextScreen(int mActivity) {

        Intent intent;

        switch (mActivity)
        {
            case Constants.MAIN_ACTIVITY:
               // intent = new Intent(BarcodeScanActivity.this, MainActivity.class);
                //startActivity(intent);
                Toast.makeText(BarcodeScanActivity.this, "Go to MAINACTIVITY", Toast.LENGTH_LONG).show();
                break;

            case Constants.LOCATION_SELECT_ACTIVITY:
                //intent = new Intent(BarcodeScanActivity.this, LocationChangeActivity.class);
                //startActivity(intent);
                Toast.makeText(BarcodeScanActivity.this, "Go to LOCATION_SELECT_ACTIVITY", Toast.LENGTH_LONG).show();

                break;
        }


    }

    @Override
    public void onBackPressed() {

        if(spfManager.isRegistered(this)) {
            LoggerLocal.error(TAG, "Cant go back frome here");
        }else
        {
            super.onBackPressed();
        }
    }
}
