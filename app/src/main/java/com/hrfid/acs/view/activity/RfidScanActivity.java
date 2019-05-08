package com.hrfid.acs.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

import java.util.List;

public class RfidScanActivity extends BaseActivity implements View.OnClickListener,
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
        etRFIDNumber.setText("");
    }

    private void initUI() {
        mProgressBarLayout = (RelativeLayout) findViewById(R.id.progressbar_relativelayout);

        btNext = (Button) findViewById(R.id.bt_next);
        btNext.setOnClickListener(this);
        etRFIDNumber = (EditText) findViewById(R.id.et_rfid_number);

        etRFIDNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Utils.showToast(RfidScanActivity.this, "2"+s.toString());
                LoggerLocal.error(TAG, "TAG ID 2=" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                LoggerLocal.error(TAG, "TAG ID 3 =" + s.toString());

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                tagId = etRFIDNumber.getText().toString();
//                String url = spfManager.getApiUrl(getApplicationContext());

                gotoNextActivity(userRoleType);


                /*
                String url = Constants.SIT_STAGE_CONTROLPOINT + "." + Constants.CONTROLPOINT;
                if (!TextUtils.isEmpty(tagId) && Constants.USER_CARD_TAG_LENGHT != tagId.length() - 1) {
                    mProgressBarLayout.setVisibility(View.VISIBLE);
                    mService.ApiCallGetUserRole(url, tagId);
                } else {
                    Utils.showToast(this, "tag ID=" + tagId + "\nLength =" + tagId.length());
                    Utils.showAlertDialog(this, getString(R.string.please_scan_user_card_barcode));
                }
                */

                break;
        }
    }

    private void gotoNextActivity(String userRoleType) {

        if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_SENIOR_STAFF)){

            //Go to next page of senior member
            Intent mNextActivity = new Intent(RfidScanActivity.this, SeniorStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);

        }else if(userRoleType.equalsIgnoreCase(Constants.USER_ROLE_TYPE_NURSE_STAFF)){

            //Go to next page of nurse member
            Intent mNextActivity = new Intent(RfidScanActivity.this, NurseStaffHomeActivity.class);
            mNextActivity.putExtra(Constants.USER_ROLE_TYPE, userRoleType);
            startActivity(mNextActivity);

        }else {

            //Go to next page of lab member
            Intent mNextActivity = new Intent(RfidScanActivity.this, LabStaffHomeActivity.class);
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
               // intent = new Intent(RfidScanActivity.this, MainActivity.class);
                //startActivity(intent);
                break;

            case Constants.LOCATION_SELECT_ACTIVITY:
                //intent = new Intent(RfidScanActivity.this, LocationChangeActivity.class);
                //startActivity(intent);
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
