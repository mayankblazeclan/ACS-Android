package com.hrfid.acs.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Spinner;
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
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.StudyList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.barcode.ReplicateActivity;
import com.hrfid.acs.view.barcode.ShowReplicateListActivity;

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
public class AddKitFragment extends Fragment  implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "SeniorSubjectOnBoarding";

    String[] spnStudyID = {"10012","10011","10010", "10015", "10016"};

    String[] spnGender = {"Samples"};

    String[] spnGroup = {"Kits"};

    private Button btnGenerateBarcode;
    private Button btnSubmit;
    //private Bitmap myBitmap;
    private EditText edtStudyName;
    private String message = "";
    private ImageView imageView;
    private TextView txt_date_of_birth;
   // private ImageButton btnDateOfBirth;
    private int mYear, mMonth, mDay;
    private String startDate ="";
    private  Spinner spnStudyIDs;
    private Spinner spnGroups;
    private  Spinner spnPersonGender;
    private Button btnReplicate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_kit, container, false);

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
        imageView = (ImageView) v.findViewById(R.id.barcode_image);
        txt_date_of_birth = v.findViewById(R.id.txt_start_date);

        btnReplicate = v.findViewById(R.id.btn_replicate);
        btnReplicate.setOnClickListener(this);

/*        btnDateOfBirth =(ImageButton)v.findViewById(R.id.btn_date_of_birth);
        btnDateOfBirth.setOnClickListener(this);*/

       /* //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);*/


        //Creating the ArrayAdapter instance having the country list


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
                //Toast.makeText(getContext(), parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
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
                //submitDetails();
                break;

            case R.id.btn_date_of_birth:
                selectDOB();
                break;

            case R.id.btn_replicate :

                if(edtStudyName.getText().toString().length()>0) {
                    //Your dialog
                    showReplicateDialog();
                }else {
                    Toast.makeText(getContext(), "Enter Study ID " , Toast.LENGTH_SHORT).show();
                }
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
                            startDate = txt_date_of_birth.getText().toString();

                        } else {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                            txt_date_of_birth.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                            startDate = txt_date_of_birth.getText().toString();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void submitDetails() {

        if(edtStudyName.length() >0) {

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


                        callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                                txt_date_of_birth.getText().toString(),
                                spnPersonGender.getSelectedItem().toString(),
                                spnGroups.getSelectedItem().toString(),
                                spnStudyIDs.getSelectedItem().toString());
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
            Toast.makeText(getContext(),"Please enter Screen ID" , Toast.LENGTH_SHORT).show();
        }

    }

    private void generateBarcode() {

        message = edtStudyName.getText().toString();

        if(message.length() >0) {

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
    private void callAddSubjectOnBoardingAPI(String screenId, String strDob, String gender, String group, String studyID) {

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

                                Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
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

                            Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
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

                                if(commonResponse.getStudyList().size()>0) {

                                    List<Integer> lists = new ArrayList<>();

                                    for (int i = 0; i < commonResponse.getStudyList().size(); i++) {

                                        lists.add(commonResponse.getStudyList().get(i).getValue());

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





    private void showReplicateDialog() {

        final TextView et_text;
        final Button btn_submit;
        final Button btnCancel;
        final Spinner sp_qtyc, sp_qtyl;


        // Create custom dialog object
        final Dialog dialog = new Dialog(getContext());
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_replicate_barcode);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        et_text = dialog.findViewById(R.id.et_text);
        btn_submit = dialog.findViewById(R.id.btn_submit);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        sp_qtyc = dialog.findViewById(R.id.sp_qtyc);
        sp_qtyl = dialog.findViewById(R.id.sp_qtyl);
        et_text.setText(edtStudyName.getText().toString());

        String[] items = new String[]{"1 ", "2", "3 ", "4", "5 ", "6",
                "7 ", "8", "9 ", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_qtyc.setAdapter(adapter);
        sp_qtyl.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_text.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Enter Text", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getActivity(), ShowReplicateListActivity.class);
                    i.putExtra("qtyc", sp_qtyc.getSelectedItem().toString());
                    i.putExtra("qtyl", sp_qtyl.getSelectedItem().toString());
                    i.putExtra("text", et_text.getText().toString());
                    startActivity(i);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
