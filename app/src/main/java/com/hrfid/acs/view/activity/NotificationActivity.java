package com.hrfid.acs.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.hrfid.acs.R;
import com.hrfid.acs.util.DataModel;
import com.hrfid.acs.view.adapter.NotificationItemAdapter;

import java.util.ArrayList;

/**
 * Created by MS on 08/05/19.
 */
public class NotificationActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;


    static String[] nameArray = {"Senior staff has changed study schedule of Study-no/name from ‘old date’ to 'new date",
            "Outside time window from Lab receipt to centrifuge",
            "The tube is missing/extra",
            "Out of range sample processing requirements (e.g. if tube needs to clot/spin immediately/spun within 30 mins/frozen immediately) -- an alert is needed if something is going out of time window or about to be spun too early)",
            "Outside time window from centrifuge to aliquot",
            "Draw tubes do not match the aliquot tubes - alert if miss-matched/extra/missing",
            "Outside time window from to aliquot to fridge or freezer",
            "Needing to be frozen/refrigerated (e.g. if something needs to be frozen within 30 minutes of centrifugation)",
            "Outside time window from Lab receipt to fridge or freezer",
            "Outside time window from Nursing to Lab"};

    static String[] versionArray = {"Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Lab Staff", "Nurse", "Senior Staff"};

    static Integer[] drawableArray = {R.drawable.ic_nurse_staff, R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff,
            R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff, R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff,
            R.drawable.ic_lab_staff, R.drawable.ic_nurse_staff, R.drawable.ic_senior_staff};

    static Integer[] id_ = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notification");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        initializeUI();

    }

    private void initializeUI() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < nameArray.length; i++) {
            data.add(new DataModel(
                   nameArray[i],
                    versionArray[i],
                    id_[i],
                    drawableArray[i]
            ));
        }
        adapter = new NotificationItemAdapter(data);
        recyclerView.setAdapter(adapter);

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
