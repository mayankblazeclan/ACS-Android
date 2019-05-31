package com.hrfid.acs.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.view.adapter.ViewScreenStudyFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by MS on 2019-05-30.
 */
public class ViewScreenStudyFragment extends Fragment {

    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3",
            "Person 4", "Person 5", "Person 6", "Person 7"));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_screen_study, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        ViewScreenStudyFragmentAdapter customAdapter = new ViewScreenStudyFragmentAdapter(getContext(), personNames);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

}
