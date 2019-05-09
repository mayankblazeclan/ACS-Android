package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.view.adapter.StaffItemAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 08/05/19.
 */
public class LabStaffHomeActivity extends AppCompatActivity implements View.OnClickListener {

  /*  private Button btnViewStudy;
    private Button btnCalendar;
    private Button btnBarcode;*/


    GridView gridView;
    ArrayList<StaffItem> staffItemList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_staff_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lab Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeUI();

    }

    private void initializeUI() {

        /*btnViewStudy = findViewById(R.id.btnViewStudy);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnBarcode = findViewById(R.id.btnBarcode);

        btnViewStudy.setOnClickListener(this);
        btnCalendar.setOnClickListener(this);
        btnBarcode.setOnClickListener(this);*/

        gridView = (GridView) findViewById(R.id.simpleGridView);
        staffItemList.add(new StaffItem("View Study",R.drawable.ic_error));
        staffItemList.add(new StaffItem("CALENDAR",R.drawable.ic_error));
        staffItemList.add(new StaffItem("BARCODE",R.drawable.ic_error));

        StaffItemAdapter staffItemAdapter=new StaffItemAdapter(this,R.layout.activity_staff_grid_items, staffItemList);
        gridView.setAdapter(staffItemAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(LabStaffHomeActivity.this, "Tapped On " + staffItemList.get(i).getTagName(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View v) {

        /*switch (v.getId()){
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
