package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.view.adapter.StaffItemAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 08/05/19.
 */
public class SeniorStaffHomeActivity extends AppCompatActivity implements View.OnClickListener{

  /*  private Button btnCreateStudy;
    private Button btnCalendar;
    private Button btnBarcode;
    private Button btnCreateInventory;
    private Button btnCreateSubject;*/

    GridView gridView;
    ArrayList<StaffItem> staffItemList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_staff_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senior Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeUI();

    }


    private void initializeUI() {

       /* btnCreateStudy = findViewById(R.id.btnCreateStudy);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnBarcode = findViewById(R.id.btnBarcode);
        btnCreateInventory = findViewById(R.id.btnCreateInventory);
        btnCreateSubject = findViewById(R.id.btnCreateSubject);

        btnCreateStudy.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);
        btnCreateInventory.setOnClickListener(this);
        btnCreateSubject.setOnClickListener(this);*/


        gridView = (GridView) findViewById(R.id.simpleGridView);
        staffItemList.add(new StaffItem("Create Study",R.drawable.ic_error));
        staffItemList.add(new StaffItem("Create Inventory",R.drawable.ic_error));
        staffItemList.add(new StaffItem("Create Subject",R.drawable.ic_error));
        staffItemList.add(new StaffItem("CALENDAR",R.drawable.ic_error));
        staffItemList.add(new StaffItem("BARCODE",R.drawable.ic_error));

        StaffItemAdapter staffItemAdapter=new StaffItemAdapter(this,R.layout.activity_staff_grid_items, staffItemList);
        gridView.setAdapter(staffItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(SeniorStaffHomeActivity.this, "Tapped On " + staffItemList.get(i).getTagName(), Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public void onClick(View v) {
/*
        switch (v.getId()){
            case R.id.btnCreateStudy:
                Utilities.showToast(getApplicationContext(), "Tapped on Create Study");
                break;

            case R.id.btnCalendar:
                Utilities.showToast(getApplicationContext(), "Tapped on CALENDAR");
                break;

            case R.id.btnBarcode:
                Utilities.showToast(getApplicationContext(), "Tapped on BARCODE");
                break;

            case R.id.btnCreateInventory:
                Utilities.showToast(getApplicationContext(), "Tapped on Create Inventory");
                break;

            case R.id.btnCreateSubject:
                Utilities.showToast(getApplicationContext(), "Tapped on Create Subject");
                break;
            default:
                break;
        }*/

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
