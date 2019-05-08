package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hrfid.acs.R;
import com.hrfid.acs.util.Utilities;

/**
 * Created by MS on 08/05/19.
 */
public class NurseStaffHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnViewStudy;
    private Button btnCalendar;
    private Button btnBarcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_staff_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nurse Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeUI();

    }

    private void initializeUI() {

        btnViewStudy = findViewById(R.id.btnViewStudy);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnBarcode = findViewById(R.id.btnBarcode);

        btnViewStudy.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnViewStudy:
                Utilities.showToast(getApplicationContext(), "Tapped on View Study");
                break;

            case R.id.btnCalendar:
                Utilities.showToast(getApplicationContext(), "Tapped on CALENDAR");
                break;

            case R.id.btnBarcode:
                Utilities.showToast(getApplicationContext(), "Tapped on BARCODE");
                break;

            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
