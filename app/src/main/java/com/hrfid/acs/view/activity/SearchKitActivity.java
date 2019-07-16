package com.hrfid.acs.view.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.SearchKitRequest;
import com.hrfid.acs.helpers.request.SearchKitRequestModel;
import com.hrfid.acs.helpers.request.SearchSubjectRequest;
import com.hrfid.acs.helpers.request.SearchSubjectRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetKitDetails.GetKitDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.KitDetailsAdapter;
import com.hrfid.acs.view.adapter.SubjectDetailsAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by MS on 08/05/19.
 */
public class SearchKitActivity extends BaseActivity  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private static RecyclerView recyclerView;

    private  Spinner spnStudyIDs;
    private  List<StudyList> listStudy = new ArrayList<>();
    private String spnSelectedStudyID ="";
    String[] spnGroupName = {"G1","G2","G3", "G4", "G5"};
    private Button btnSubmit;
    private LinearLayout linearLayout;
    private TextView textView;
    private ImageButton  btnEndDatePicker;
    private TextView txtEndDate;
    private int mYear, mMonth, mDay;
    private String endDate = "";
    private EditText edtVisit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_kit_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Search KIT");
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

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        linearLayout = findViewById(R.id.llRec);
        textView = findViewById(R.id.txtNoData);

        btnEndDatePicker=(ImageButton)findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)findViewById(R.id.txt_end_date);

        edtVisit = findViewById(R.id.edtVisit);

        btnEndDatePicker.setOnClickListener(this);

       /* ArrayAdapter studyIdAdp = new ArrayAdapter(SearchKitActivity.this,android.R.layout.simple_spinner_item, spnGroupName);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroup.setAdapter(studyIdAdp);*/

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

            case R.id.btn_end_date:
                setExpDate();
                break;


        }

    }

    private void setExpDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(SearchKitActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String fmonth;
                        int month;
                        if (monthOfYear < 10 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtEndDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            endDate =txtEndDate.getText().toString();

                        } else if (monthOfYear < 13 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtEndDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            endDate =txtEndDate.getText().toString();

                        } else {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                            txtEndDate.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                            endDate =txtEndDate.getText().toString();
                        }



                        // txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        //txtEndDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //endDate =txtEndDate.getText().toString();

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.setTitle(null);
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (1000 * 60 * 60 *24));

        datePickerDialog.show();
    }


    private void submitDetails() {

        if(edtVisit.getText().toString().trim().length()>0){

            if(txtEndDate.getText().toString().trim().length()>0){


                if(!spnStudyIDs.getSelectedItem().toString().isEmpty()) {

                    callSearchAPI(txtEndDate.getText().toString().trim(), spnSelectedStudyID, edtVisit.getText().toString().trim());

                }else {
                    showToastMessage("Study ID is empty..");
                }

            }else {
                showToastMessage("Please Select Expiry Date");
            }

        }else {
            showToastMessage("Please enter VISIT");
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

                                    ArrayAdapter studyIdAdp = new ArrayAdapter(SearchKitActivity.this,android.R.layout.simple_spinner_item, lists);
                                    studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnStudyIDs.setAdapter(studyIdAdp);

                                }else {
                                    Logger.logError("No STUDY_LIST FOUND :" + "No STUDY_LIST FOUND");
                                }


                            }else {

                                Utils.showAlertDialog(SearchKitActivity.this,  commonResponse.getStatus().getMSG());
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
    private void callSearchAPI(String endDate, String studyID, String visit) {

        SearchKitRequestModel searchKitRequestModel = new SearchKitRequestModel();
        searchKitRequestModel.setAppName(AppConstants.APP_NAME);
        searchKitRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        searchKitRequestModel.setDeviceType(AppConstants.APP_OS);
        searchKitRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        searchKitRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(this));
        searchKitRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        searchKitRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        searchKitRequestModel.setEvent(AppConstants.SEARCH_KIT);
        searchKitRequestModel.setUserName(new PrefManager(this).getUserName());
        searchKitRequestModel.setStudyId(Integer.valueOf(studyID));
        searchKitRequestModel.setVisit(visit);
        searchKitRequestModel.setExpDate(endDate);

        new NetworkingHelper(new SearchKitRequest(this, true, searchKitRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetKitDetailsResponse getSubjectDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetKitDetailsResponse.class);

                        if (getSubjectDetailsResponse.getStatus().getCODE() == 200) {

                            if(getSubjectDetailsResponse.getKitList().size() > 0){

                                Logger.logError("SEARCH_KIT API success " +
                                        getSubjectDetailsResponse.getKitList().get(0).getStudyTitle());
                                Logger.logError("SEARCH_KIT API success " +
                                        getSubjectDetailsResponse.getKitList().get(0).getStudyName());

                                //Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());

                                linearLayout.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);

                                KitDetailsAdapter kitDetailsAdapter = new KitDetailsAdapter(SearchKitActivity.this, getSubjectDetailsResponse.getKitList(), listStudy, recyclerView);
                                recyclerView.setAdapter(kitDetailsAdapter);

                            }else {

                                linearLayout.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);

                                Logger.logError("SEARCH_KIT API Failure " +
                                        getSubjectDetailsResponse.getKitList());
                                Logger.logError("SEARCH_KIT API Failure " +
                                        getSubjectDetailsResponse.getStatus());

                                //Utils.showAlertDialog(SearchSubjectActivity.this,  getSubjectDetailsResponse.getStatus().getMSG());
                            }

                        }else {

                            Logger.logError("SEARCH_KIT API Failure " +
                                    getSubjectDetailsResponse.getStatus().getMSG());
                            Logger.logError("SEARCH_KIT API Failure " +
                                    getSubjectDetailsResponse.getStatus().getCODE());

                            linearLayout.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);

                            //Utils.showAlertDialog(SearchSubjectActivity.this,  getSubjectDetailsResponse.getStatus().getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("SEARCH_KIT Exception " + e.getMessage());
                    }

                } else {

                    linearLayout.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    Logger.logError("SEARCH_KIT API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

}
