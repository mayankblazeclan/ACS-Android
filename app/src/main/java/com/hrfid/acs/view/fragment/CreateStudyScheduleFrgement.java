package com.hrfid.acs.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrfid.acs.R;

/**
 * Created by MS on 2019-05-30.
 */
public class CreateStudyScheduleFrgement extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_study, container, false);

       // initViews(v);

        // callPublishSMS(true);

        return v;
    }

}
