package com.hrfid.acs.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.AddSubjectRequest;
import com.hrfid.acs.helpers.request.AddSubjectRequestModel;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.ResetNotificationCountRequest;
import com.hrfid.acs.helpers.request.SearchSubjectRequest;
import com.hrfid.acs.helpers.request.SearchSubjectRequestModel;
import com.hrfid.acs.helpers.serverResponses.GetNofication.GetNotificationResponse;
import com.hrfid.acs.helpers.serverResponses.GetNofication.Notification;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.NotificationItemAdapter;
import com.hrfid.acs.view.adapter.SubjectDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MS on 08/05/19.
 */
public class SearchSubjectActivity extends BaseActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private static RecyclerView recyclerView;

    private  Spinner spnStudyIDs, spnGroup;
    private  List<StudyList> listStudy = new ArrayList<>();
    private String spnSelectedStudyID ="";
    String[] spnGroupName = {"G1","G2","G3", "G4", "G5"};
    private Button btnSubmit;
    private LinearLayout linearLayout;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_subject_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search Subject");
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

        getAllStudyID();

    }

    private void initializeUI() {

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_list);
        // set a LinearLayoutManager with default vertical orientation
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        spnStudyIDs = (Spinner) findViewById(R.id.spnStudyId);
        spnStudyIDs.setOnItemSelectedListener(this);

        spnGroup = (Spinner) findViewById(R.id.spnGroup);
        spnGroup.setOnItemSelectedListener(this);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        linearLayout = findViewById(R.id.llRec);
        textView = findViewById(R.id.txtNoData);

        ArrayAdapter studyIdAdp = new ArrayAdapter(SearchSubjectActivity.this,android.R.layout.simple_spinner_item, spnGroupName);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroup.setAdapter(studyIdAdp);

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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        switch(parent.getId()){
            case R.id.spnGroup :
                //Your Action Here.
                break;

            case R.id.spnStudyId :
                //Your Action Here.
                spnSelectedStudyID = String.valueOf(listStudy.get(position).getValue());
               // Toast.makeText(SearchSubjectActivity.this, spnSelectedStudyID , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSubmit:
                submitDetails();
                break;


        }

    }

    private void submitDetails() {

        if(!spnGroup.getSelectedItem().toString().isEmpty()){

            if(!spnStudyIDs.getSelectedItem().toString().isEmpty()) {

                callSearchAPI(spnGroup.getSelectedItem().toString(), spnSelectedStudyID);

            }else {
                showToastMessage("Study ID is empty..");
            }

        }else {
            showToastMessage("GROUP is empty..");
        }

    }


    //getAllStudyID API
    private void getAllStudyID()
    {
        final CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(this));
        commonRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        //commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(this).getUserName());

        new NetworkingHelper(new GetAllStudyIdRequest(this, true, commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetAllStudyIdResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetAllStudyIdResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getStatus().getMSG().equalsIgnoreCase("REQ_SUCCESS")){

                                Logger.logError("getStudyIds API success " +
                                        commonResponse.getStudyList());

                                listStudy = commonResponse.getStudyList();

                                if(commonResponse.getStudyList().size()>0) {

                                    List<String> lists = new ArrayList<>();

                                    for (int i = 0; i < commonResponse.getStudyList().size(); i++) {

                                        lists.add(commonResponse.getStudyList().get(i).getLabel());

                                    }

                                    ArrayAdapter studyIdAdp = new ArrayAdapter(SearchSubjectActivity.this,android.R.layout.simple_spinner_item, lists);
                                    studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnStudyIDs.setAdapter(studyIdAdp);

                                }else {
                                    Logger.logError("No STUDY_LIST FOUND :" + "No STUDY_LIST FOUND");
                                }


                            }else {

                                Utils.showAlertDialog(SearchSubjectActivity.this,  commonResponse.getStatus().getMSG());
                            }

                        }

                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("getStudyIds API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }


    //Call callSearchAPI API
    private void callSearchAPI(String group, String studyID) {

        SearchSubjectRequestModel searchSubjectRequestModel = new SearchSubjectRequestModel();
        searchSubjectRequestModel.setAppName(AppConstants.APP_NAME);
        searchSubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        searchSubjectRequestModel.setDeviceType(AppConstants.APP_OS);
        searchSubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        searchSubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(this));
        searchSubjectRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        searchSubjectRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        searchSubjectRequestModel.setEvent(AppConstants.SEARCH_SUBJECT);
        searchSubjectRequestModel.setUserName(new PrefManager(this).getUserName());
        searchSubjectRequestModel.setStudyId(Integer.valueOf(studyID));
        searchSubjectRequestModel.setGroupId(group);

        new NetworkingHelper(new SearchSubjectRequest(this, true, searchSubjectRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetSubjectDetailsResponse getSubjectDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetSubjectDetailsResponse.class);

                        if (getSubjectDetailsResponse.getStatus().getCODE() == 200) {

                            if(getSubjectDetailsResponse.getSubjectList().size() > 0){

                                Logger.logError("searchSubject API success " +
                                        getSubjectDetailsResponse.getSubjectList().get(0).getStudyTitle());
                                Logger.logError("searchSubject API success " +
                                        getSubjectDetailsResponse.getSubjectList().get(0).getStudyName());

                                //Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());

                                linearLayout.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);

                                SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(SearchSubjectActivity.this, getSubjectDetailsResponse.getSubjectList(), listStudy, recyclerView);
                                recyclerView.setAdapter(customAdapter);

                            }else {

                                linearLayout.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);

                                Logger.logError("searchSubject API Failure " +
                                        getSubjectDetailsResponse.getSubjectList());
                                Logger.logError("searchSubject API Failure " +
                                        getSubjectDetailsResponse.getStatus());

                                //Utils.showAlertDialog(SearchSubjectActivity.this,  getSubjectDetailsResponse.getStatus().getMSG());
                            }

                        }else {

                            Logger.logError("searchSubject API Failure " +
                                    getSubjectDetailsResponse.getStatus().getMSG());
                            Logger.logError("searchSubject API Failure " +
                                    getSubjectDetailsResponse.getStatus().getCODE());

                            linearLayout.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);

                            //Utils.showAlertDialog(SearchSubjectActivity.this,  getSubjectDetailsResponse.getStatus().getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("searchSubject Exception " + e.getMessage());
                    }

                } else {

                    linearLayout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    Logger.logError("searchSubject API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

}
