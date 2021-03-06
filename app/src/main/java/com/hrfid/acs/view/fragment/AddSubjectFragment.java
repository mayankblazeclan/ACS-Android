package com.hrfid.acs.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.AddSubjectRequest;
import com.hrfid.acs.helpers.request.AddSubjectRequestModel;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.CreateScheduleModel;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.SeniorStudySetupActivity;
import com.hrfid.acs.view.activity.SeniorSubjectOnBoardingActivity;
import com.hrfid.acs.view.adapter.SubjectOnBoardingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by MS on 2019-06-04.
 */
public class AddSubjectFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "SeniorSubjectOnBoarding";

    String[] spnGender = {"Male","Female","Other"};

    String[] spnGroup = {"-", "G1","G2","G3", "G4", "G5"};

    private Button btnGenerateBarcode;
    private Button btnSubmit;
    //private Bitmap myBitmap;
    private EditText edtStudyName;
    private String message = "";
    private ImageView imageView;
    private TextView txt_date_of_birth;
    private ImageButton btnDateOfBirth;
    private int mYear, mMonth, mDay;
    private String spnSelectedStudyID ="";
    private  Spinner spnStudyIDs;
    private Spinner spnGroups;
    private  Spinner spnPersonGender;
    private  List<StudyList> listStudy = new ArrayList<>();
    private Switch aSwitchOptionalData;
    private LinearLayout ll_optional_data;
    private EditText editTextInitials;
    private EditText editTextRand;
    private int isOptional=0;
    private RadioGroup radioGroupEStatus;
    private RadioGroup radioGroupPKSubstudy;
    private RadioGroup radioGroupLeuka;
    private RadioGroup radioGroupGenomic;
    private RadioGroup radioGroupFuture;
    private RadioButton rbEStatus;
    private RadioButton rbPKStatus;
    private RadioButton rbLeukaStatus;
    private RadioButton rbGenomicStatus;
    private RadioButton rbFutureStatus;
    private String strStudyName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_subject, container, false);

        initViews(v);

        getAllStudyID();

        return v;
    }

    private void initViews(View v) {


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        /*Spinner spnBloodGroups = (Spinner) v.findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);*/

        spnStudyIDs = (Spinner) v.findViewById(R.id.spnStatusId);
        spnStudyIDs.setOnItemSelectedListener(this);

        spnGroups = (Spinner) v.findViewById(R.id.spnGroup);
        spnGroups.setOnItemSelectedListener(this);

        spnPersonGender = (Spinner) v.findViewById(R.id.spnPersonGender);
        spnPersonGender.setOnItemSelectedListener(this);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        btnGenerateBarcode = v.findViewById(R.id.btnGenerateBarcode);
        btnGenerateBarcode.setOnClickListener(this);

        edtStudyName = v.findViewById(R.id.edtStudyName);
        editTextInitials = v.findViewById(R.id.edtInitials);
        editTextRand = v.findViewById(R.id.edtRand);
        imageView = (ImageView) v.findViewById(R.id.barcode_image);
        txt_date_of_birth = v.findViewById(R.id.txt_start_date);

        btnDateOfBirth =(ImageButton)v.findViewById(R.id.btn_date_of_birth);
        btnDateOfBirth.setOnClickListener(this);

        radioGroupEStatus =(RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroupPKSubstudy =(RadioGroup) v.findViewById(R.id.radioGroup1);
        radioGroupLeuka =(RadioGroup) v.findViewById(R.id.radioGroup2);
        radioGroupGenomic =(RadioGroup) v.findViewById(R.id.radioGroup3);
        radioGroupFuture =(RadioGroup) v.findViewById(R.id.radioGroup4);


        //Creating the ArrayAdapter instance having the country list

        ll_optional_data =(LinearLayout) v.findViewById(R.id.ll_optional_data);
        ll_optional_data.setVisibility(View.GONE);


        ArrayAdapter genderAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, spnGender);
        genderAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonGender.setAdapter(genderAdp);

        ArrayAdapter groupAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, spnGroup);
        groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroups.setAdapter(groupAdp);

        aSwitchOptionalData = (Switch) v.findViewById(R.id.switch1);
        aSwitchOptionalData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    ll_optional_data.setVisibility(View.VISIBLE);
                    isOptional=1;
                } else {
                    // The toggle is disabled
                    ll_optional_data.setVisibility(View.GONE);
                    isOptional=0;
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        switch(parent.getId()){
            case R.id.spnGroup :
                //Your Action Here.
                break;

            case R.id.spnPersonGender :
                //Your Another Action Here.
                break;

            case R.id.spnStatusId :
                //Your Action Here.
                spnSelectedStudyID = String.valueOf(listStudy.get(position).getValue());
                strStudyName = String.valueOf(listStudy.get(position).getStudyId());
                // Toast.makeText(getContext(), spnSelectedStudyID , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnGenerateBarcode:
                //Toast.makeText(getContext(),"Button Generate Pressed" , Toast.LENGTH_SHORT).show();
                generateBarcode();
                break;

            case R.id.btnSubmit:
                submitDetails();
                break;

            case R.id.btn_date_of_birth:
                selectDOB();
                break;

        }

    }

    private void selectDOB() {

        final Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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


                            txt_date_of_birth.setText(year + "-" + paddedMonth + "-" + fDate);
                            //startDate = txt_date_of_birth.getText().toString();

                        } else if (monthOfYear < 13 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txt_date_of_birth.setText(year + "-" + paddedMonth + "-" + fDate);
                            //startDate = txt_date_of_birth.getText().toString();

                        } else {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                            txt_date_of_birth.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                            //startDate = txt_date_of_birth.getText().toString();
                        }

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));

        datePickerDialog.show();
    }

    private void submitDetails() {

        if(edtStudyName.getText().toString().trim().length() >0) {

            if(editTextInitials.getText().toString().trim().length() >0) {

                if(!txt_date_of_birth.getText().toString().equalsIgnoreCase("")){

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Date date1 = null;
                    try {
                        date1 = format.parse(txt_date_of_birth.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar cal = Calendar.getInstance();
                    Date sysDate = cal.getTime();

                    if(date1.compareTo(sysDate) >0) {


                        Toast.makeText(getActivity(),"Please select correct Date of Birth",Toast.LENGTH_SHORT).show();

                    }else {

                        int selectedId=radioGroupEStatus.getCheckedRadioButtonId();
                        rbEStatus=(RadioButton)getView().findViewById(selectedId);

                        int selectedId1=radioGroupPKSubstudy.getCheckedRadioButtonId();
                        rbPKStatus=(RadioButton)getView().findViewById(selectedId1);


                        int selectedId2=radioGroupLeuka.getCheckedRadioButtonId();
                        rbLeukaStatus=(RadioButton)getView().findViewById(selectedId2);


                        int selectedId3=radioGroupGenomic.getCheckedRadioButtonId();
                        rbGenomicStatus=(RadioButton)getView().findViewById(selectedId3);


                        int selectedId4=radioGroupFuture.getCheckedRadioButtonId();
                        rbFutureStatus=(RadioButton)getView().findViewById(selectedId4);

                        if(isOptional ==0)
                        {

                            callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                                    txt_date_of_birth.getText().toString(),
                                    spnPersonGender.getSelectedItem().toString(),
                                    spnGroups.getSelectedItem().toString(),
                                    spnSelectedStudyID,
                                    editTextInitials.getText().toString(),
                                    strStudyName,
                                    isOptional,
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " ");

                        }else {

                            callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                                    txt_date_of_birth.getText().toString(),
                                    spnPersonGender.getSelectedItem().toString(),
                                    spnGroups.getSelectedItem().toString(),
                                    spnSelectedStudyID,
                                    editTextInitials.getText().toString(),
                                    strStudyName,
                                    isOptional,
                                    editTextRand.getText().toString().trim(),
                                    rbEStatus.getText().toString().trim(),
                                    rbPKStatus.getText().toString().trim(),
                                    rbLeukaStatus.getText().toString().trim(),
                                    rbGenomicStatus.getText().toString().trim(),
                                    rbFutureStatus.getText().toString().trim());

                        }
                    /*callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                            txt_date_of_birth.getText().toString(),
                            spnPersonGender.getSelectedItem().toString(),
                            spnGroups.getSelectedItem().toString(),
                            spnSelectedStudyID,
                            editTextInitials.getText().toString(),
                            editTextRand.getText().toString(), isOptional);*/
                    }




               /* callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                        txt_date_of_birth.getText().toString(),
                        spnPersonGender.getSelectedItem().toString(),
                        spnGroups.getSelectedItem().toString(),
                        spnStudyIDs.getSelectedItem().toString());*/

                }else {
                    Toast.makeText(getContext(),"Please Select Date Of Birth" , Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getContext(),"Please enter Initials" , Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"Please enter Screen ID" , Toast.LENGTH_SHORT).show();
        }

    }

    private void generateBarcode() {

        message = edtStudyName.getText().toString();

        if(message.trim().length() >0) {

            Bitmap bitmap = null;
            try {
                bitmap = Utilities.CreateImage(message, "Barcode");
                //myBitmap = bitmap;
            } catch (WriterException we) {
                we.printStackTrace();
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }else {
            Toast.makeText(getContext(),"Please enter Screen ID" , Toast.LENGTH_SHORT).show();
        }
    }

    //Call callStudySetup API
    private void callAddSubjectOnBoardingAPI(String screenId, String strDob, String gender,
                                             String group, String studyID, String initials,
                                             String strStudyName,
                                             int isOptional, String strRand,  String eStatus,
                                             String strPkStudy, String strLeuka, String strGenomic,
                                             String strFuture) {

        AddSubjectRequestModel addSubjectRequestModel = new AddSubjectRequestModel();
        addSubjectRequestModel.setAppName(AppConstants.APP_NAME);
        addSubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        addSubjectRequestModel.setDeviceType(AppConstants.APP_OS);
        addSubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        addSubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        addSubjectRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        addSubjectRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        addSubjectRequestModel.setEvent(AppConstants.ADD_SUBJECT);
        addSubjectRequestModel.setUserName(new PrefManager(getActivity()).getUserName());
        addSubjectRequestModel.setScreenId(screenId);
        addSubjectRequestModel.setDob(strDob);
        addSubjectRequestModel.setGenBarcode(screenId);
        addSubjectRequestModel.setStatus(AppConstants.INQUEUE);
        addSubjectRequestModel.setGender(gender);
        addSubjectRequestModel.setGroup(group);
        addSubjectRequestModel.setStudyId(Integer.valueOf(studyID));
        addSubjectRequestModel.setInitials(initials);
        if(strRand.equalsIgnoreCase("")){
         addSubjectRequestModel.setRandNum(" ");
        }else {
            addSubjectRequestModel.setRandNum(strRand);
        }
        addSubjectRequestModel.setAntigenStatus(eStatus);
        addSubjectRequestModel.setPKSubStudy(strPkStudy);
        addSubjectRequestModel.setLeuka(strLeuka);
        addSubjectRequestModel.setGenomic(strGenomic);
        addSubjectRequestModel.setFResearch(strFuture);
        addSubjectRequestModel.setIsOptional(isOptional);
        addSubjectRequestModel.setStudyName(strStudyName);

        new NetworkingHelper(new AddSubjectRequest(getActivity(), true, addSubjectRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("subjectOnboard API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("subjectOnboard API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                //Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());

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
                                        Intent mNextActivity = new Intent(getActivity(), SeniorSubjectOnBoardingActivity.class);
                                        startActivity(mNextActivity);
                                        getActivity().finish();
                                    }
                                });

                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                edtStudyName.setText("");
                                txt_date_of_birth.setText("");

                            }else {

                                Logger.logError("subjectOnboard API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("subjectOnboard API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                          /*  Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());*/

                            Utils.showAlertDialog(getActivity(),  commonResponse.getStatus().geteRROR());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("subjectOnboard API Failure " +
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
                                    spnStudyIDs.setAdapter(studyIdAdp);

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
}
