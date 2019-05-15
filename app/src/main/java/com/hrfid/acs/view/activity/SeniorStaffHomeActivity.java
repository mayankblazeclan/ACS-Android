package com.hrfid.acs.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.model.StaffItem;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.StaffItemAdapter;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

/**
 * Created by MS on 08/05/19.
 */
public class SeniorStaffHomeActivity extends AppCompatActivity {


    GridView gridView;
    ArrayList<StaffItem> staffItemList=new ArrayList<>();
    NotificationBadge mBadge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_staff_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Senior Staff");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        initializeUI();

    }


    private void initializeUI() {


        gridView = (GridView) findViewById(R.id.simpleGridView);
        staffItemList.add(new StaffItem("Study Setup",R.drawable.ic_study_setup));
        staffItemList.add(new StaffItem("Inventory Setup",R.drawable.ic_senior_inventory));
        staffItemList.add(new StaffItem("Subject Onboarding",R.drawable.ic_senior_onboarding));

       // mBadge = (NotificationBadge) findViewById(R.id.badge);
        //mBadge.setNumber(10);

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
    public void onBackPressed() {
       // super.onBackPressed();
        Utils.createDialogTwoButtons(SeniorStaffHomeActivity.this, "Logout", true, "Are you sure you want to logout?", "OK", "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SelectRoleActivity.class);
                startActivity(mNextActivity);
                finish();
            }
        }, null);
       // this.finish();
    }

   /* @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();

        TextView txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        txtViewCount.setText("10");
        //updateHotCount(count++);
        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // updateHotCount(count++);
                Toast.makeText(SeniorStaffHomeActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, NotificationActivity.class);
                startActivity(mNextActivity);
            }
        });
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(SeniorStaffHomeActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
                Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, NotificationActivity.class);
                startActivity(mNextActivity);
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Notification Functionality
        if (id == R.id.action_notification) {
            Toast.makeText(SeniorStaffHomeActivity.this, "Notification tapped", Toast.LENGTH_LONG).show();
            Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, NotificationActivity.class);
            startActivity(mNextActivity);
            //finish();
            return true;
        }

        //Logout Functionality
        if (id == R.id.action_logout) {
            //Toast.makeText(SeniorStaffHomeActivity.this, "Logout tapped", Toast.LENGTH_LONG).show();
            Utils.createDialogTwoButtons(SeniorStaffHomeActivity.this, "Logout", true, "Are you sure you want to logout?", "OK", "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent mNextActivity = new Intent(SeniorStaffHomeActivity.this, SelectRoleActivity.class);
                    startActivity(mNextActivity);
                    finish();
                }
            }, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
