package com.hrfid.acs.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.AddKitRequest;
import com.hrfid.acs.helpers.request.AddKitRequestModel;
import com.hrfid.acs.helpers.request.AddTSURequest;
import com.hrfid.acs.helpers.request.AddTSURequestModel;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetTSUParamRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetKitDetails.KitList;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.GetTSUParamsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.Kit;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.InventorySetupActivity;
import com.hrfid.acs.view.activity.TSUSetupActivity;
import com.hrfid.acs.view.barcode.PrintAdapter;
import com.hrfid.acs.view.barcode.ReplicateModel;
import com.hrfid.acs.view.barcode.ShowReplicateListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by MS on 2019-06-04.
 */
public class AddTSUFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "AddTSU_Details";
    private  List<StudyList> listStudy = new ArrayList<>();
    String[] sNumberValue = {"ABC","DBABC","ADDABC","TAABC","OPABC","0", "1","2","3", "4", "5", "6", "7", "8", "9", "10"};
    private String strKitName;
    private String strKitRecId;
    private String spnSelectedKitID ="";

    private String strStudyName;
    private String strStudyTitle;
    private String spnSelectedStudyID ="";
    private int mYear, mMonth, mDay;


    private Spinner spnStudyLabel;
    private Spinner spnKitLabel;
    private Spinner spnPrimaryInvestigator;
    private Spinner spnTubeColor;
    private Spinner spnAliquotTubeColor;
    private Spinner spnDiscardTubeColor;
    private Spinner spnTestName;
    private Spinner spnCollectionLabel;
    private Spinner spnTransportLabel;
    private Spinner spnLabUse;
    private TextView edtVisit;
    private EditText edtSiteNo;
    private EditText edtCohortNo;
    private EditText edtTimepoint;
    private EditText edtTubeVolume;

    private EditText edtAliquotTubeVolume;
    private EditText edtAliquotExtNo;
    private EditText edtDiscardTubeVolume;
    private EditText edtCentrifugeProg;

    private RadioGroup radioTubeType;
    private RadioButton radioButtonTubeType;
    private ImageButton btnEntryDate;
    private TextView txtEntryDate;
    private Button btnSubmit;
    private List kitListFetchedParam = null;
    private List kitVisitListFetchedParam = null;
    private List listPrimaryInvestigator = null;
    private List listTubeColor = null;
    private List listAliquotTubeColor = null;
    private List listDiscardTubeColor = null;

    private List listTestName = null;
    private List listCollectionTube = null;
    private List listTransportTube = null;
    private List listLabUse = null;
    private List <Kit>listKitList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_tsu, container, false);

        initViews(v);

        getAllStudyID();

        return v;
    }

    private void initViews(View v) {

        spnStudyLabel = (Spinner) v.findViewById(R.id.spnStatusId);
        spnStudyLabel.setOnItemSelectedListener(this);

        spnKitLabel = (Spinner) v.findViewById(R.id.spnKitLabel);
        spnKitLabel.setOnItemSelectedListener(this);

        edtVisit = v.findViewById(R.id.edtVisit);
        edtSiteNo = v.findViewById(R.id.edtSiteNo);
        edtCohortNo = v.findViewById(R.id.edtCohortNo);
        edtTimepoint = v.findViewById(R.id.edtTimepoint);

        edtTubeVolume = v.findViewById(R.id.edtTubeVolume);
        edtAliquotTubeVolume = v.findViewById(R.id.edtAliquotTubeVolume);
        edtAliquotExtNo = v.findViewById(R.id.edtAliquotTubeExt);

        edtDiscardTubeVolume = v.findViewById(R.id.edtDiscardTubeVol);
        edtCentrifugeProg = v.findViewById(R.id.edtCentrifugeProgramme);

        spnPrimaryInvestigator = (Spinner) v.findViewById(R.id.spnPrimaryInvestigator);
        spnPrimaryInvestigator.setOnItemSelectedListener(this);

        spnTubeColor = (Spinner) v.findViewById(R.id.spnTubeColor);
        spnTubeColor.setOnItemSelectedListener(this);

        spnAliquotTubeColor = (Spinner) v.findViewById(R.id.spnAliquotTubeColor);
        spnAliquotTubeColor.setOnItemSelectedListener(this);

        spnDiscardTubeColor = (Spinner) v.findViewById(R.id.spnDiscardTubeColor);
        spnDiscardTubeColor.setOnItemSelectedListener(this);

        spnTestName = (Spinner) v.findViewById(R.id.spnTestName);
        spnTestName.setOnItemSelectedListener(this);

        spnCollectionLabel = (Spinner) v.findViewById(R.id.spnCollectionLabel);
        spnCollectionLabel.setOnItemSelectedListener(this);

        spnTransportLabel = (Spinner) v.findViewById(R.id.spnTransportLabel);
        spnTransportLabel.setOnItemSelectedListener(this);

        spnLabUse = (Spinner) v.findViewById(R.id.spnLabUse);
        spnLabUse.setOnItemSelectedListener(this);

        radioTubeType = v.findViewById(R.id.radioGroup_tube_type);
        txtEntryDate = v.findViewById(R.id.txt_entry_date);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        btnEntryDate=  v.findViewById(R.id.btnEntryDate);
        btnEntryDate.setOnClickListener(this);

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
       //         android.R.layout.simple_spinner_item, sNumberValue);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //spnKitLabel.setAdapter(adapter);
        //spnPrimaryInvestigator.setAdapter(adapter);

        //spnTubeColor.setAdapter(adapter);
        ///spnAliquotTubeColor.setAdapter(adapter);

        //spnDiscardTubeColor.setAdapter(adapter);
        //spnCollectionLabel.setAdapter(adapter);
        //spnTransportLabel.setAdapter(adapter);
        //spnTestName.setAdapter(adapter);
        //spnLabUse.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        switch(parent.getId()){

            case R.id.spnStatusId :
                //Your Action Here.
                spnSelectedStudyID = String.valueOf(listStudy.get(position).getValue());
                strStudyName = String.valueOf(listStudy.get(position).getStudyId());
                strStudyTitle = listStudy.get(position).getLabel();
                //Toast.makeText(getContext(), parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
                getTSUParams(spnSelectedStudyID);
                break;

            case R.id.spnPrimaryInvestigator :

                break;

            case R.id.spnKitLabel :
                if(kitVisitListFetchedParam!=null) {
                    edtVisit.setText(kitVisitListFetchedParam.get(position).toString());
                    strKitName = String.valueOf(listKitList.get(position).getKitId());
                    strKitRecId = String.valueOf(listKitList.get(position).getId());
                }
                break;

            //TubeColor
            case R.id.spnTubeColor :

                break;

            //AliquotTubeColor
            case R.id.spnAliquotTubeColor :

                break;

            //DiscardTubeColor
            case R.id.spnDiscardTubeColor :

                break;

            //TestName
            case R.id.spnTestName :

                break;

            //CollectionLabel
            case R.id.spnCollectionLabel :

                break;

            //TransportLabel
            case R.id.spnTransportLabel :

                break;

            //Lab Use
            case R.id.spnLabUse :

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

            case R.id.btnEntryDate:
                setEntryDate();
                break;

        }
    }

    private void setEntryDate() {

        final Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        //txtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        // txtStartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        //startDate =txtStartDate.getText().toString();

                        String fmonth;
                        int month;
                        String startDate;
                        if (monthOfYear < 10 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtEntryDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            startDate =txtEntryDate.getText().toString();

                        }  else if (monthOfYear < 13 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtEntryDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            startDate =txtEntryDate.getText().toString();

                        } else {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                            txtEntryDate.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                            startDate =txtEntryDate.getText().toString();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void submitDetails() {

        if(edtSiteNo.getText().toString().trim().length() >0) {

            if(edtCohortNo.getText().toString().trim().length() > 0) {

                if(edtTimepoint.getText().toString().trim().length() > 0) {

                    if(edtTubeVolume.getText().toString().trim().length() > 0) {

                        if(!txtEntryDate.getText().toString().equalsIgnoreCase("")){

                            //if(!txtEndDate.getText().toString().equalsIgnoreCase("")){


                       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = format.parse(startDate);
                            date2 = format.parse(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date1.compareTo(date2) <= 0) {
                            //Toast.makeText(getActivity(),"All Date OK.. RUN API.." , Toast.LENGTH_SHORT).show();
                            Calendar cal = Calendar.getInstance();
                            Date sysDate = cal.getTime();*/

                            // if(date1.compareTo(sysDate) >0 && date2.compareTo(sysDate) >0) {

                   /* Calendar cal = Calendar.getInstance();
                    Date sysDate = cal.getTime();

                    if(date1.compareTo(sysDate) >0) {


                        Toast.makeText(getActivity(),"Please select correct Date of Birth",Toast.LENGTH_SHORT).show();

                    }else {*/


                            int selectedId = radioTubeType.getCheckedRadioButtonId();
                            radioButtonTubeType = (RadioButton) getView().findViewById(selectedId);


                            callAddTSUapi(spnSelectedStudyID,
                                    strStudyName,
                                    strStudyTitle,
                                    spnSelectedKitID,
                                    strKitName,
                                    strKitRecId,
                                    edtVisit.getText().toString().trim(),
                                    edtSiteNo.getText().toString().trim(),
                                    edtCohortNo.getText().toString().trim(),
                                    spnPrimaryInvestigator.getSelectedItem().toString(),
                                    edtTimepoint.getText().toString().trim(),
                                    radioButtonTubeType.getText().toString().trim(),
                                    spnTubeColor.getSelectedItem().toString(),
                                    edtTubeVolume.getText().toString().trim(),
                                    spnAliquotTubeColor.getSelectedItem().toString(),
                                    edtAliquotTubeVolume.getText().toString().trim(),
                                    edtAliquotExtNo.getText().toString().trim(),
                                    spnDiscardTubeColor.getSelectedItem().toString(),
                                    edtDiscardTubeVolume.getText().toString().trim(),
                                    spnTestName.getSelectedItem().toString().trim(),
                                    spnCollectionLabel.getSelectedItem().toString().trim(),
                                    spnTransportLabel.getSelectedItem().toString().trim(),
                                    edtCentrifugeProg.getText().toString().trim(),
                                    spnLabUse.getSelectedItem().toString().trim(),
                                    txtEntryDate.getText().toString().trim()
                            );

                        }else {
                            Toast.makeText(getContext(),"Please select Entry Date" , Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getContext(),"Please enter Tube Volume" , Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(),"Please enter Timpepoint" , Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getContext(),"Please enter Cohort Number" , Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"Please enter Site Number" , Toast.LENGTH_SHORT).show();
        }

    }

    //Call callAddTSUapi API
    private void callAddTSUapi(String spnSelectedStudyID, String studyName, String studyTitle,
                               String spnSelectedKitID, String strKitName, String strKitRecId,
                               String visit, String siteNo, String cohortNo,
                               String prim_investegator, String strTimePoint, String rbTypeValue,
                               String tubeColor, String tubeVolume, String aliquotColor, String aliquotVol,
                               String aliquotExtNo, String spnDiscardTubeColor, String discardTubeVol,
                               String spnTestName, String spnCollectionLabel, String spnTransportLabel,
                               String centriProg, String strLabUse, String txtEntryDate) {

        AddTSURequestModel tsuRequestModel = new AddTSURequestModel();
        tsuRequestModel.setAppName(AppConstants.APP_NAME);
        tsuRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        tsuRequestModel.setDeviceType(AppConstants.APP_OS);
        tsuRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        tsuRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        tsuRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        tsuRequestModel.setUserName(new PrefManager(getActivity()).getUserName());
        tsuRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        tsuRequestModel.setEvent(AppConstants.ADD_TSU);

        tsuRequestModel.setVisit(visit);
         tsuRequestModel.setStudyName(strStudyName);
        //tsuRequestModel.setStudyName("SST");
        tsuRequestModel.setKitId(strKitName);
        tsuRequestModel.setStudyId(Integer.valueOf(spnSelectedStudyID));
        //tsuRequestModel.setStudyId(4);
        tsuRequestModel.setKitRecId(Integer.valueOf(strKitRecId));
        if(rbTypeValue.equalsIgnoreCase("Blood")) {
            tsuRequestModel.setTubeType(1);
        }else {
            tsuRequestModel.setTubeType(0);
        }
        tsuRequestModel.setIsDuplicate(0);
        tsuRequestModel.setEntryDate(txtEntryDate);
        tsuRequestModel.setSiteNo(siteNo);
        tsuRequestModel.setCohortNo(cohortNo);
        tsuRequestModel.setPi(prim_investegator);
        tsuRequestModel.setTimepoint(strTimePoint);
        tsuRequestModel.setTubeColor(tubeColor);
        tsuRequestModel.setTubeVol(tubeVolume);
        tsuRequestModel.setAliquotColor(aliquotColor);
        tsuRequestModel.setAliquotVol(aliquotVol);
        tsuRequestModel.setAliquotExt(aliquotExtNo);
        tsuRequestModel.setDiscardTubeColor(spnDiscardTubeColor);
        tsuRequestModel.setDiscardTubeVolume(discardTubeVol);
        tsuRequestModel.setTestName(spnTestName);
        tsuRequestModel.setCollectionLable(spnCollectionLabel);
        tsuRequestModel.setTransportLable(spnTransportLabel);
        tsuRequestModel.setCentrifugeProg(centriProg);
        tsuRequestModel.setLabUse(strLabUse);

        new NetworkingHelper(new AddTSURequest(getActivity(), true, tsuRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("addTSU API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("addTSU API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                // Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                                // ...Irrelevant code for customizing the buttons and title
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.alert_dialog_with_one_button, null);
                                dialogBuilder.setView(dialogView);
                                final AlertDialog alertDialog = dialogBuilder.create();

                                TextView tvDesc = (TextView) dialogView.findViewById(R.id.tv_dialog_desc);
                                tvDesc.setText(commonResponse.getResponse().get(0).getMessage());
                                Button btDialogOk = (Button) dialogView.findViewById(R.id.bt_dialog_ok);
                                btDialogOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        alertDialog.dismiss();
                                        Intent mNextActivity = new Intent(getActivity(), TSUSetupActivity.class);
                                        startActivity(mNextActivity);
                                        getActivity().finish();
                                    }
                                });

                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                edtSiteNo.setText("");
                                edtCohortNo.setText("");
                                edtTimepoint.setText("");
                                edtTubeVolume.setText("");
                                //txtEntryDate.setText("");

                            }else {

                                Logger.logError("addTSU API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("addTSU API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Utils.showAlertDialog(getActivity(),  commonResponse.getStatus().geteRROR());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("addTSU API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }





    //getAllStudyID API
    private void getAllStudyID()
    {
        final CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getContext()));
        commonRequestModel.setUserRole(new PrefManager(getContext()).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(getContext()).getBarCodeValue());
        //commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(getContext()).getUserName());

        new NetworkingHelper(new GetAllStudyIdRequest(getActivity(), true, commonRequestModel)) {

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

                                    ArrayAdapter studyIdAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, lists);
                                    studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnStudyLabel.setAdapter(studyIdAdp);

                                }else {
                                    Logger.logError("No STUDY_LIST FOUND :" + "No STUDY_LIST FOUND");
                                }


                            }else {

                                Utils.showAlertDialog(getActivity(),  commonResponse.getStatus().getMSG());
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


    private void getTSUParams(String studyId){

        new NetworkingHelper(new GetTSUParamRequest(getActivity(), true, studyId)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetTSUParamsResponse getTSUParamsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetTSUParamsResponse.class);

                        if (getTSUParamsResponse.getStatus().getCODE() == 200) {

                            if(getTSUParamsResponse.getResponse().getKits().size() > 0){

                                Logger.logError("GetTSU PARAM API success " +
                                        getTSUParamsResponse.getResponse().getKits().size());
                                Logger.logError("GetTSU PARAM API success " +
                                        getTSUParamsResponse.getResponse().getKits());


                                kitListFetchedParam = new ArrayList();
                                kitVisitListFetchedParam = new ArrayList();
                                listPrimaryInvestigator = new ArrayList();
                                listTubeColor = new ArrayList();
                                listDiscardTubeColor = new ArrayList();
                                listAliquotTubeColor = new ArrayList();

                                listTestName = new ArrayList();
                                listCollectionTube = new ArrayList();
                                listTransportTube = new ArrayList();
                                listLabUse = new ArrayList();
                                listKitList = new ArrayList();


                                listKitList = getTSUParamsResponse.getResponse().getKits();

                                for (int i = 0; i < getTSUParamsResponse.getResponse().getKits().size(); i++) {

                                    kitListFetchedParam.add(getTSUParamsResponse.getResponse().getKits().get(i).getKitId());
                                    kitVisitListFetchedParam.add(getTSUParamsResponse.getResponse().getKits().get(i).getVisit());

                                }

                                if(kitVisitListFetchedParam !=null) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, kitListFetchedParam);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnKitLabel.setAdapter(adapter);
                                }


                                //For Primary Investigator
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getPI().size(); i++) {

                                    listPrimaryInvestigator.add(getTSUParamsResponse.getResponse().getPI().get(i).getValue());
                                }

                                if(listPrimaryInvestigator!=null) {
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listPrimaryInvestigator);
                                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnPrimaryInvestigator.setAdapter(adapter1);
                                }

                                //For TubeColor
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getTubeColor().size(); i++) {

                                    listTubeColor.add(getTSUParamsResponse.getResponse().getTubeColor().get(i).getValue());
                                }

                                if(listTubeColor!=null) {
                                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listTubeColor);
                                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnTubeColor.setAdapter(adapter2);
                                }


                                //For DiscardTubeColor
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getDiscardTubeColor().size(); i++) {
                                    listDiscardTubeColor.add(getTSUParamsResponse.getResponse().getDiscardTubeColor().get(i).getValue());
                                }

                                if(listDiscardTubeColor!=null) {
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listDiscardTubeColor);
                                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnDiscardTubeColor.setAdapter(adapter1);
                                }

                                //For AliquotTubeColor
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getAliquotTubeColor().size(); i++) {
                                    listAliquotTubeColor.add(getTSUParamsResponse.getResponse().getAliquotTubeColor().get(i).getValue());
                                }

                                if(listAliquotTubeColor!=null) {
                                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listAliquotTubeColor);
                                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnAliquotTubeColor.setAdapter(adapter3);
                                }


                                //For TestName
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getTestName().size(); i++) {

                                    listTestName.add(getTSUParamsResponse.getResponse().getTestName().get(i).getValue());
                                }

                                if(listTestName!=null) {
                                    ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listTestName);
                                    adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnTestName.setAdapter(adapter4);
                                }

                                //For CollectionLable
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getCollectionLable().size(); i++) {

                                    listCollectionTube.add(getTSUParamsResponse.getResponse().getCollectionLable().get(i).getValue());
                                }

                                if(listCollectionTube!=null) {
                                    ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listCollectionTube);
                                    adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnCollectionLabel.setAdapter(adapter5);
                                }


                                //For TransportLable
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getTransportLable().size(); i++) {

                                    listTransportTube.add(getTSUParamsResponse.getResponse().getTransportLable().get(i).getValue());
                                }

                                if(listTransportTube!=null) {
                                    ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listTransportTube);
                                    adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnTransportLabel.setAdapter(adapter6);
                                }


                                //For LAB USE
                                //========================
                                for (int i = 0; i < getTSUParamsResponse.getResponse().getLabUse().size(); i++) {

                                    listLabUse.add(getTSUParamsResponse.getResponse().getLabUse().get(i).getValue());
                                }

                                if(listLabUse!=null) {
                                    ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item, listLabUse);
                                    adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spnLabUse.setAdapter(adapter7);
                                }



                            }else {

                              /*  Logger.logError("GetTSU PARAM API Failure " +
                                        getTSUParamsResponse.getResponse().getKits());
                                Logger.logError("GetTSU PARAM API Failure " +
                                        getTSUParamsResponse.getResponse().getKits());*/

                                Utils.showAlertDialog(getActivity(),  getTSUParamsResponse.getStatus().getMSG());
                            }

                        }else {

                            Utils.showAlertDialog(getActivity(),  getTSUParamsResponse.getStatus().getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("GetTSU PARAM API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };
    }

}
