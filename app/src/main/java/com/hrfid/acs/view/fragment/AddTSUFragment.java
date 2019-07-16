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
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.InventorySetupActivity;
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

/**
 * Created by MS on 2019-06-04.
 */
public class AddTSUFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = "AddTSU_Details";

    String[] sNumber = {"0", "1","2","3", "4", "5", "6", "7", "8", "9", "10"};

    private Button btnGenerateBarcode;
    private Button btnSubmit;
    private String message = "";
    private ImageView imageView;
   // private TextView txt_start_date;
    private int mYear, mMonth, mDay;
    private String startDate ="";
    private String endDate = "";
    private  Spinner spnStudyIDs, spnLocal, spnCentral, spnAliquot;
    private Button btnReplicate;
    private RadioButton rbSample, rbAliquot, rbBoth;
    private LinearLayout llLocal, llCentral, llAliquot;
    private ImageButton btnStartDatePicker, btnEndDatePicker;
    private TextView txtStartDate, txtEndDate;
    private  List<StudyList> listStudy = new ArrayList<>();
    private String spnSelectedStudyID ="";
    private RadioGroup radioKITtype;
    private RadioButton radioButtonKitTYPE;
    private RadioGroup radioAdditionalKITtype;
    private RadioButton radioButtonAdditionalKitTYPE;
    private RadioGroup radioGroupCategory;
    private RadioButton radioButtonCategory;
    private RadioGroup radioGroupReqForm;
    private RadioButton radioButtonReqForm;
    private EditText editTextKIT_ID;
    private EditText editTextAccessionNumber;
    private EditText editTextVISIT;
    private String strStudyName;
    private String strStudyTitle;

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

/*
        spnStudyIDs = (Spinner) v.findViewById(R.id.spnStatusId);
        spnStudyIDs.setOnItemSelectedListener(this);

        spnLocal = (Spinner) v.findViewById(R.id.spnLocal);
        spnLocal.setOnItemSelectedListener(this);

        spnCentral = (Spinner) v.findViewById(R.id.spnCentral);
        spnCentral.setOnItemSelectedListener(this);

        spnAliquot = (Spinner) v.findViewById(R.id.spnAliquot);
        spnAliquot.setOnItemSelectedListener(this);

        btnStartDatePicker=(ImageButton)v.findViewById(R.id.btn_start_date);
        txtStartDate=(TextView)v.findViewById(R.id.txt_start_date);

        btnEndDatePicker=(ImageButton)v.findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)v.findViewById(R.id.txt_end_date);

        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);

        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        btnGenerateBarcode = v.findViewById(R.id.btnGenerateBarcode);
        btnGenerateBarcode.setOnClickListener(this);

        editTextKIT_ID = v.findViewById(R.id.edtKitId);
        editTextAccessionNumber = v.findViewById(R.id.edtAccession);
        editTextVISIT = v.findViewById(R.id.edtVisit);

        radioKITtype =(RadioGroup) v.findViewById(R.id.radioGroupKitType);
        radioAdditionalKITtype =(RadioGroup) v.findViewById(R.id.rg_additional_kit);
        radioGroupCategory =(RadioGroup) v.findViewById(R.id.rg_category);
        radioGroupReqForm =(RadioGroup) v.findViewById(R.id.radioGroup_req_form);

        imageView = (ImageView) v.findViewById(R.id.barcode_image);
        //txt_start_date = v.findViewById(R.id.txt_start_date);

        btnReplicate = v.findViewById(R.id.btn_replicate);
        btnReplicate.setOnClickListener(this);

        ArrayAdapter adpNumber = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, sNumber);
        adpNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAliquot.setAdapter(adpNumber);
        spnLocal.setAdapter(adpNumber);
        spnCentral.setAdapter(adpNumber);

        rbSample = (RadioButton)v.findViewById(R.id.radioSample);
        rbAliquot = (RadioButton)v.findViewById(R.id.radioAliquot);
        rbBoth = (RadioButton)v.findViewById(R.id.radioBoth);

        rbSample.setOnClickListener(this);
        rbAliquot.setOnClickListener(this);
        rbBoth.setOnClickListener(this);

        llLocal = (LinearLayout) v.findViewById(R.id.linearLayout_local);
        llCentral = (LinearLayout) v.findViewById(R.id.linearLayout_central);
        llAliquot = (LinearLayout) v.findViewById(R.id.linearLayout_alqt);*/

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        switch(parent.getId()){

            case R.id.spnStatusId :
                //Your Action Here.
                spnSelectedStudyID = String.valueOf(listStudy.get(position).getValue());
                strStudyName = String.valueOf(listStudy.get(position).getStudyId());
                strStudyTitle = listStudy.get(position).getLabel();
                //Toast.makeText(getContext(), parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        String str="";
        switch (v.getId()){
            case R.id.btnGenerateBarcode:
                //Toast.makeText(getContext(),"Button Generate Pressed" , Toast.LENGTH_SHORT).show();
                generateBarcode();
                break;

            case R.id.btnSubmit:
                submitDetails();
                break;

            case R.id.btn_replicate :

                if(editTextKIT_ID.getText().toString().length()>0) {
                    //Your dialog
                    showReplicateDialog();
                }else {
                    Toast.makeText(getContext(), "Please enter Kit ID" , Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.radioAliquot:
                boolean checked = ((RadioButton) v).isChecked();
                if(checked)
                    str = "Aliquot Selected";
                llLocal.setVisibility(View.GONE);
                llCentral.setVisibility(View.GONE);
                llAliquot.setVisibility(View.VISIBLE);
                break;

            case R.id.radioSample:
                boolean checked1 = ((RadioButton) v).isChecked();
                if(checked1)
                    str = "Sample Selected";
                llLocal.setVisibility(View.VISIBLE);
                llCentral.setVisibility(View.VISIBLE);
                llAliquot.setVisibility(View.GONE);
                break;

            case R.id.radioBoth:
                boolean checked2 = ((RadioButton) v).isChecked();
                if(checked2)
                    str = "Both Selected";
                llLocal.setVisibility(View.VISIBLE);
                llCentral.setVisibility(View.VISIBLE);
                llAliquot.setVisibility(View.VISIBLE);
                break;


            case R.id.btn_start_date:
                setStartDate();
                break;

            case R.id.btn_end_date:
                setExpDate();
                break;

        }
        //Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

    }

    private void setExpDate() {

        final Calendar c = Calendar.getInstance();
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


                            txtEndDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            endDate =txtEndDate.getText().toString();

                        }  else if (monthOfYear < 13 && dayOfMonth < 10) {

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

    private void setStartDate() {

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
                        if (monthOfYear < 10 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtStartDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            startDate =txtStartDate.getText().toString();

                        }  else if (monthOfYear < 13 && dayOfMonth < 10) {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String fDate = "0" + dayOfMonth;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                            txtStartDate.setText(year + "-" + paddedMonth + "-" + fDate);
                            startDate =txtStartDate.getText().toString();

                        } else {

                            fmonth = "0" + monthOfYear;
                            month = Integer.parseInt(fmonth) + 1;
                            String paddedMonth = String.format("%02d", month);
                            //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                            txtStartDate.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                            startDate =txtStartDate.getText().toString();
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void submitDetails() {

        if(editTextKIT_ID.length() >0) {

            if(editTextVISIT.length() > 0) {

            if(!txtStartDate.getText().toString().equalsIgnoreCase("")){

                if(!txtEndDate.getText().toString().equalsIgnoreCase("")){


                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
                        Date sysDate = cal.getTime();

                       // if(date1.compareTo(sysDate) >0 && date2.compareTo(sysDate) >0) {

                   /* Calendar cal = Calendar.getInstance();
                    Date sysDate = cal.getTime();

                    if(date1.compareTo(sysDate) >0) {


                        Toast.makeText(getActivity(),"Please select correct Date of Birth",Toast.LENGTH_SHORT).show();

                    }else {*/


                            int selectedId = radioKITtype.getCheckedRadioButtonId();
                            radioButtonKitTYPE = (RadioButton) getView().findViewById(selectedId);

                            int selectedId1 = radioAdditionalKITtype.getCheckedRadioButtonId();
                            radioButtonAdditionalKitTYPE = (RadioButton) getView().findViewById(selectedId1);

                            int selectedId2 = radioGroupCategory.getCheckedRadioButtonId();
                            radioButtonCategory = (RadioButton) getView().findViewById(selectedId2);

                            int selectedId3 = radioGroupReqForm.getCheckedRadioButtonId();
                            radioButtonReqForm = (RadioButton) getView().findViewById(selectedId3);


                            //For selected Kit type
                            radioButtonKitTYPE.getText().toString().trim();


                            callAddKITdetailsAPI(editTextKIT_ID.getText().toString(),
                                    editTextAccessionNumber.getText().toString(),
                                    editTextVISIT.getText().toString(),
                                    radioButtonKitTYPE.getText().toString().trim(),
                                    radioButtonAdditionalKitTYPE.getText().toString().trim(),
                                    radioButtonCategory.getText().toString().trim(),
                                    radioButtonReqForm.getText().toString().trim(),
                                    txtStartDate.getText().toString(),
                                    txtEndDate.getText().toString(),
                                    spnLocal.getSelectedItem().toString(),
                                    spnCentral.getSelectedItem().toString(),
                                    spnAliquot.getSelectedItem().toString(),
                                    spnSelectedStudyID,
                                    strStudyName,
                                    strStudyTitle);
                            //  }




               /* callAddSubjectOnBoardingAPI(editTextKIT_ID.getText().toString(),
                        txt_start_date.getText().toString(),
                        spnPersonGender.getSelectedItem().toString(),
                        spnGroups.getSelectedItem().toString(),
                        spnStudyIDs.getSelectedItem().toString());*/
                        /*}else {

                            Toast.makeText(getActivity(),"Scan date with past date cannot be scheduled",Toast.LENGTH_SHORT).show();
                        }*/
                    }else {

                        Toast.makeText(getActivity(),"Kit Expiry Date cannot be earlier than Kit Scan Date" , Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getContext(),"Please Select Expiry Date" , Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(),"Please Select Scan Date" , Toast.LENGTH_SHORT).show();
            }

            }else {
                Toast.makeText(getContext(),"Please enter VISIT" , Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"Please enter KIT ID" , Toast.LENGTH_SHORT).show();
        }

    }

    private void generateBarcode() {

        message = editTextKIT_ID.getText().toString();

        if(message.length() >0) {

           /* Bitmap bitmap = null;
            try {
                bitmap = Utilities.CreateImage(message, "Barcode");
                //myBitmap = bitmap;
            } catch (WriterException we) {
                we.printStackTrace();
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }*/

            showGenerateBarcodeDialog(message, imageView);
        }else {
            Toast.makeText(getContext(),"Please enter KIT ID" , Toast.LENGTH_SHORT).show();
        }
    }

    //Call callAddKITdetailsAPI API
    private void callAddKITdetailsAPI(String kitId, String accessionNumber, String visit,
                                      String kitType, String additionalKit, String category,
                                      String reqForm, String startDate, String endDate,
                                      String localQty, String centralQty, String aliquotQty,
                                      String studyID, String strStudyName, String strStudyTitle) {

        AddKitRequestModel addKitRequestModel = new AddKitRequestModel();
        addKitRequestModel.setAppName(AppConstants.APP_NAME);
        addKitRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        addKitRequestModel.setDeviceType(AppConstants.APP_OS);
        addKitRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        addKitRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        addKitRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        addKitRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        addKitRequestModel.setEvent(AppConstants.ADD_KIT);
        if(kitType.equalsIgnoreCase("TRIAL")) {
            addKitRequestModel.setIsTrial(1);
        }else {
            addKitRequestModel.setIsTrial(0);
        }
        addKitRequestModel.setUserName(new PrefManager(getActivity()).getUserName());
        addKitRequestModel.setKitId(kitId);
        if(!accessionNumber.isEmpty()) {
            addKitRequestModel.setExtNum(accessionNumber);
        }else {
            addKitRequestModel.setExtNum(" ");
        }
        addKitRequestModel.setVisit(visit);
        if(additionalKit.equalsIgnoreCase("YES")) {
            addKitRequestModel.setAdditionalKit(1);
        }else {
            addKitRequestModel.setAdditionalKit(0);
        }
        addKitRequestModel.setCategory(category);
        addKitRequestModel.setStatus(AppConstants.IN_STOCK);
        addKitRequestModel.setLocal(Integer.valueOf(localQty));
        addKitRequestModel.setCentral(Integer.valueOf(centralQty));
        addKitRequestModel.setAliquot(Integer.valueOf(aliquotQty));
        if(reqForm.equalsIgnoreCase("YES")) {
            addKitRequestModel.setReqForm(1);
        }else {
            addKitRequestModel.setReqForm(0);
        }
        addKitRequestModel.setScanDate(startDate);
        addKitRequestModel.setExpDate(endDate);
        addKitRequestModel.setStudyId(Integer.valueOf(studyID));
        addKitRequestModel.setGenBarcode(kitId);
        addKitRequestModel.setStudyName(strStudyName);
        addKitRequestModel.setStudyTitle(strStudyTitle);

        new NetworkingHelper(new AddKitRequest(getActivity(), true, addKitRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("addKIT API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("addKIT API success " +
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
                                        Intent mNextActivity = new Intent(getActivity(), InventorySetupActivity.class);
                                        startActivity(mNextActivity);
                                        getActivity().finish();
                                    }
                                });

                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                editTextKIT_ID.setText("");
                                editTextAccessionNumber.setText("");
                                editTextVISIT.setText("");
                                txtStartDate.setText("");
                                txtEndDate.setText("");

                            }else {

                                Logger.logError("addKIT API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("addKIT API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                          /*  Logger.logError("addKIT API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("addKIT API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());*/

                            Utils.showAlertDialog(getActivity(),  commonResponse.getStatus().geteRROR());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("addKIT API Failure " +
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
        et_text.setText(editTextKIT_ID.getText().toString());

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


    private void showGenerateBarcodeDialog(final String message, ImageView imageView) {

        final TextView et_text;
        final TextView txtBarcode;
        final Button btn_submit;
        final Button btnCancel;
        final RadioGroup radioGroupLabelSize;
        final RadioButton[] rbLabelSize = new RadioButton[1];
        final ImageView imageView1;


        // Create custom dialog object
        final Dialog dialog = new Dialog(getContext());
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_generate_barcode);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        et_text = dialog.findViewById(R.id.et_text);
        txtBarcode = dialog.findViewById(R.id.txtBarcode);
        btn_submit = dialog.findViewById(R.id.btn_submit);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        imageView1 = dialog.findViewById(R.id.barcode_image);
        //spnLabel = dialog.findViewById(R.id.sp_qtyc);
        //spnSize = dialog.findViewById(R.id.sp_qtyl);
        et_text.setText(editTextKIT_ID.getText().toString());
        txtBarcode.setText(editTextKIT_ID.getText().toString());

        Bitmap bitmap = null;
        try {
            bitmap = Utilities.CreateImage(message, "Barcode");
            //myBitmap = bitmap;
        } catch (WriterException we) {
            we.printStackTrace();
        }
        if (bitmap != null) {
            imageView1.setImageBitmap(bitmap);
        }

        //radioGroupKitType =(RadioGroup) dialog.findViewById(R.id.radioGroupKitType);
        radioGroupLabelSize =(RadioGroup) dialog.findViewById(R.id.radioGroupLabelSize);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedId1 = radioGroupLabelSize.getCheckedRadioButtonId();
                rbLabelSize[0] = (RadioButton) dialog.findViewById(selectedId1);

                Toast.makeText(getContext(),"Label size is - "+ rbLabelSize[0].getText(),Toast.LENGTH_SHORT).show();


                PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                List<ReplicateModel> replicateListQtyc = new ArrayList<>();

                ReplicateModel replicateModel = null;
                try {
                    replicateModel = new ReplicateModel(message, Utilities.CreateImage(message, "Barcode"));
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                replicateListQtyc.add(replicateModel);
                String jobName = getString(R.string.app_name) +
                        " Document";

                if(rbLabelSize[0].getText().toString().trim().equalsIgnoreCase("0.5 ml tube")){

                    printManager.print(jobName, new PrintAdapter(getActivity(), "4", replicateListQtyc), null);

                }else if(rbLabelSize[0].getText().toString().trim().equalsIgnoreCase("1.5 - 2.0 ml tube")){

                    printManager.print(jobName, new PrintAdapter(getActivity(), "5", replicateListQtyc), null);

                }else {

                    printManager.print(jobName, new PrintAdapter(getActivity(), "6", replicateListQtyc), null);

                }



                /*if (et_text.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Enter Text", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getActivity(), ShowReplicateListActivity.class);
                    i.putExtra("qtyc", spnLabel.getSelectedItem().toString());
                    i.putExtra("qtyl", spnSize.getSelectedItem().toString());
                    i.putExtra("text", et_text.getText().toString());
                    startActivity(i);
                }*/
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
