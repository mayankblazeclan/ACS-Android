package com.hrfid.acs.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;

/**
 * Created by MS on 08/05/19.
 */
public class SelectRoleActivity extends Activity implements View.OnClickListener {

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
    }
}
