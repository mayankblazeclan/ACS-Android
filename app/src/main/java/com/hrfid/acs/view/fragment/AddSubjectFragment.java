package com.hrfid.acs.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.view.activity.SeniorSubjectOnBoarding;

/**
 * Created by MS on 2019-06-04.
 */
public class AddSubjectFragment extends Fragment  implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "SeniorSubjectOnBoarding";

    String[] spnBloodGroup = {"O+","B-","B+", "A+", "A-"};

    String[] spnStudyID = {"10012","10011","10010", "10015", "10016"};

    String[] spnGender = {"Male","Female","Other"};

    String[] spnGroup = {"G1","G2","G3", "G4", "G5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_subject, container, false);

        initViews(v);

        // callPublishSMS(true);

        return v;
    }

    private void initViews(View v) {


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spnBloodGroups = (Spinner) v.findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);

        Spinner spnStudyIDs = (Spinner) v.findViewById(R.id.spnStatusId);
        spnStudyIDs.setOnItemSelectedListener(this);

        Spinner spnGroups = (Spinner) v.findViewById(R.id.spnGroup);
        spnGroups.setOnItemSelectedListener(this);

        Spinner spnPersonGender = (Spinner) v.findViewById(R.id.spnPersonGender);
        spnPersonGender.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter studyIdAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, spnStudyID);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);

        ArrayAdapter genderAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, spnGender);
        genderAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonGender.setAdapter(genderAdp);

        ArrayAdapter groupAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, spnGroup);
        groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroups.setAdapter(groupAdp);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
