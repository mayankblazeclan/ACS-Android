package com.hrfid.acs.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.data.Constants;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.CreateScheduleModel;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.SelectRoleActivity;
import com.hrfid.acs.view.activity.SeniorStaffHomeActivity;
import com.hrfid.acs.view.activity.SeniorStudySetupActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by MS on 2019-05-30.
 */
public class CreateStudyScheduleFrgement extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private ImageButton btnStartDatePicker, btnEndDatePicker;
    private TextView txtStartDate, txtEndDate;
    private int mYear, mMonth, mDay;
    private Button btnSubmit;
    private EditText edtStudyName;
    private String startDate ="";
    private String endDate = "";
    private Spinner spnStudyStatus;
    private String[] status = {"INPROGRESS", "ACTIVE","INACTIVE"};
    private RadioButton rbScreen, rbTrial;
    private int isTrail =0;
    private EditText edtDocCode;
    private EditText edtStudyId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_study, container, false);

        initViews(v);

        return v;
    }

    private void initViews(View v) {

        spnStudyStatus = (Spinner) v.findViewById(R.id.spnStatus);
        spnStudyStatus.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyStatus.setAdapter(aa);

        btnStartDatePicker=(ImageButton)v.findViewById(R.id.btn_start_date);
        txtStartDate=(TextView)v.findViewById(R.id.txt_start_date);

        btnEndDatePicker=(ImageButton)v.findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)v.findViewById(R.id.txt_end_date);

        btnSubmit=(Button) v.findViewById(R.id.btnSubmit);
        edtStudyName = v.findViewById(R.id.edtStudyName);

        edtDocCode = v.findViewById(R.id.edtDocCode);
        edtStudyId = v.findViewById(R.id.edtStudyID);

        rbScreen = (RadioButton) v.findViewById(R.id.radioScreen);
        rbTrial = (RadioButton) v.findViewById(R.id.radioTrail);

        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnStartDatePicker) {

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


        if (v == btnEndDatePicker) {

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
            datePickerDialog.show();
        }

        if(v==btnSubmit){

            if(edtStudyName.getText().toString().length() != 0){

                if(edtStudyId.getText().toString().length() !=0){

                    if(edtDocCode.getText().toString().length() !=0){

                if(!txtStartDate.getText().toString().equalsIgnoreCase("")
                        && !txtEndDate.getText().toString().equalsIgnoreCase("")){

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

                        if(date1.compareTo(sysDate) >0 && date2.compareTo(sysDate) >0) {

                            if (rbTrial.isChecked()) {
                                isTrail = 1;
                            } else {
                                isTrail = 0;
                            }

                            callStudySetup(edtStudyName.getText().toString(), edtStudyId.getText().toString(), edtDocCode.getText().toString(), startDate, endDate, spnStudyStatus.getSelectedItem().toString(), isTrail);

                        }else {

                            Toast.makeText(getActivity(),"Study with past date cannot be scheduled",Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(getActivity(),"Study end date cannot be earlier than the study start date" , Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Toast.makeText(getActivity(),"Please select Study Dates" , Toast.LENGTH_SHORT).show();
                }

                    }else {
                        Toast.makeText(getActivity(),"Please enter Doctor Code" , Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getActivity(),"Please enter Study ID" , Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getActivity(),"Please enter Study Name" , Toast.LENGTH_SHORT).show();
            }


        }

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // Toast.makeText(getActivity(),status[position] , Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onResume() {
        super.onResume();
        edtStudyName.setText("");
        txtStartDate.setText("");
    }

    //Call callStudySetup API
    private void callStudySetup(String studyName, String studyId, String strDocCode, String startDate, String endDate, String status, int isTrail) {
        CreateScheduleModel createScheduleModel = new CreateScheduleModel();
        createScheduleModel.setAppName(AppConstants.APP_NAME);
        createScheduleModel.setVersionNumber(AppConstants.APP_VERSION);
        createScheduleModel.setDeviceType(AppConstants.APP_OS);
        createScheduleModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        createScheduleModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        createScheduleModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        createScheduleModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        createScheduleModel.setEvent(AppConstants.CREATE_SCHEDULE);
        createScheduleModel.setUserName(new PrefManager(getActivity()).getUserName());
        createScheduleModel.setName(studyName);
        createScheduleModel.setStartDate(startDate);
        createScheduleModel.setEndDate(endDate);
        createScheduleModel.setStatus(status);
        createScheduleModel.setActivity(AppConstants.ACTIVITY);
        createScheduleModel.setIsTrial(isTrail);
        createScheduleModel.setStudyId(studyId);
        createScheduleModel.setDocCode(strDocCode);

        new NetworkingHelper(new CreateScheduleRequest(getActivity(), true, createScheduleModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("createSchedule API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("createSchedule API success " +
                                        commonResponse.getResponse().get(0).getMessage());


                                /*Intent mNextActivity = new Intent(getActivity(), SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                getActivity().finish();*/

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
                                        Intent mNextActivity = new Intent(getActivity(), SeniorStudySetupActivity.class);
                                        startActivity(mNextActivity);
                                        getActivity().finish();
                                    }
                                });

                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();

                                edtStudyName.setText("");
                                txtStartDate.setText("");
                                txtEndDate.setText("");

                            }else {

                                Logger.logError("createSchedule API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("createSchedule API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("createSchedule API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("createSchedule API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("createSchedule API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }
}
