package com.hrfid.acs.view.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.ModifyScheduleRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.StudyList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by MS on 2019-05-31.
 */
public class ViewTrialStudyFragmentAdapter extends RecyclerView.Adapter<ViewTrialStudyFragmentAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    List<StudyList> studyLists;
    String[] status = new String[0];
    Context context;
    private boolean isFromScreening;

    public ViewTrialStudyFragmentAdapter(Context context, List<StudyList> studyLists, boolean isFromScreening) {
        this.context = context;
        this.studyLists = studyLists;
        this.isFromScreening = isFromScreening;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_study_item_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        if(studyLists != null) {
            if(studyLists.get(position).getIsTrial().equalsIgnoreCase("1")) {
                holder.txtStudy.setText(studyLists.get(position).getName().trim()
                        + " " + "(" + studyLists.get(position).getId() + ")");

                holder.txt_start_end_date.setText
                        (Utilities.splitDateFromDesired(studyLists.get(position).getStartDate())
                                + " - " + Utilities.splitDateFromDesired(studyLists.get(position)
                                .getEndDate()));
                holder.txt_number_of_day.setText("" + studyLists.get(position).getTotalDays());

                //holder.txt_number_of_day.setText("" + studyLists.get(position).getIsTrial());
                if (studyLists.get(position).getStatus().equalsIgnoreCase("ACTIVE")) {
                    holder.txtStatus.setText("ACTIVE");
                    holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));
                } else if (studyLists.get(position).getStatus().equalsIgnoreCase("INACTIVE")) {
                    holder.txtStatus.setText("INACTIVE");
                    holder.txtStatus.setTextColor(Color.RED);
                } else {
                    holder.txtStatus.setText("INPROGRESS");
                    holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
                }
            }else {
                //holder.itemView.setVisibility(View.GONE);
            }
        }
        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModifyDialog(studyLists.get(position));
            }
        });
       /* holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });*/
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtStudy;
        TextView txt_start_end_date;
        TextView txt_number_of_day;
        TextView txtStatus;
        Button btnModify, btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtStudy = (TextView) itemView.findViewById(R.id.txtStudy);
            txt_start_end_date = itemView.findViewById(R.id.txt_start_end_date);
            txt_number_of_day = itemView.findViewById(R.id.txt_number_of_day);
            txtStatus = itemView.findViewById(R.id.txt_status);
            btnModify = (Button) itemView.findViewById(R.id.btnModify);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }

    private void showModifyDialog(final StudyList studyList) {

        ImageButton btnStartDatePicker, btnEndDatePicker;
        final TextView txtStartDate, txtEndDate;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_study_modify);
        dialog.setCancelable(false);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        final Spinner spin = (Spinner) dialog.findViewById(R.id.spnStatus);
        spin.setOnItemSelectedListener(this);


        if(studyList.getStatus().equalsIgnoreCase("ACTIVE")){
            status = new String[]{"ACTIVE", "INACTIVE", "INPROGRESS"};
        }else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "ACTIVE", "INPROGRESS"};
        }else if(studyList.getStatus().equalsIgnoreCase("INPROGRESS")){
            status = new String[]{"INPROGRESS", "INACTIVE", "ACTIVE"};
        }else {

        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,status);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        final EditText editText =  dialog.findViewById(R.id.edtStudyName);
        editText.setText(studyList.getName());

        btnStartDatePicker=(ImageButton)dialog.findViewById(R.id.btn_start_date);
        txtStartDate=(TextView)dialog.findViewById(R.id.txt_start_date);
        txtStartDate.setText(Utilities.splitDateFromDesired(studyList.getStartDate()));

        btnEndDatePicker=(ImageButton)dialog.findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)dialog.findViewById(R.id.txt_end_date);
        txtEndDate.setText(Utilities.splitDateFromDesired(studyList.getEndDate()));

        btnEndDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                // dialog.dismiss();

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear[0] = c.get(Calendar.YEAR);
                mMonth[0] = c.get(Calendar.MONTH);
                mDay[0] = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                                String fmonth;
                                int month;
                                if (monthOfYear < 10 && dayOfMonth < 10) {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String fDate = "0" + dayOfMonth;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                                    txtEndDate.setText(year + "-" + paddedMonth + "-" + fDate);
                                    //endDate =txtEndDate.getText().toString();

                                } else {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                                    txtEndDate.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                                    //endDate =txtEndDate.getText().toString();
                                }

                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.show();
            }
        });


        btnStartDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear[0] = c.get(Calendar.YEAR);
                mMonth[0] = c.get(Calendar.MONTH);
                mDay[0] = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // txtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                String fmonth;
                                int month;
                                if (monthOfYear < 10 && dayOfMonth < 10) {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String fDate = "0" + dayOfMonth;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(fDate + "/" + paddedMonth + "/" + year);


                                    txtStartDate.setText(year + "-" + paddedMonth + "-" + fDate);

                                } else {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                                    txtStartDate.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                                }

                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.show();
            }
        });

        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();

                if(editText.getText().toString().length() != 0){

                    if(!txtStartDate.getText().toString().equalsIgnoreCase("")
                            && !txtEndDate.getText().toString().equalsIgnoreCase("")){

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = format.parse(txtStartDate.getText().toString());
                            date2 = format.parse(txtEndDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (date1.compareTo(date2) <= 0) {

                            callModifySetupAPI(editText.getText().toString(), txtStartDate.getText().toString(), txtEndDate.getText().toString(), spin.getSelectedItem().toString(), studyList.getId());

                        }else {

                            Toast.makeText(context,"End Date Cannot Be Smaller Than Start Date" , Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        Toast.makeText(context,"Please Select Both the Dates.." , Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(context,"Please Enter STUDY NAME" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studyLists.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void callModifySetupAPI(String studyName, String startDate, String endDate, String status, int id) {

        ModifyScheduleRequestModel modifyScheduleRequestModel = new ModifyScheduleRequestModel();
        modifyScheduleRequestModel.setAppName(AppConstants.APP_NAME);
        modifyScheduleRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        modifyScheduleRequestModel.setDeviceType(AppConstants.APP_OS);
        modifyScheduleRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        modifyScheduleRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        modifyScheduleRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        modifyScheduleRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        modifyScheduleRequestModel.setEvent(AppConstants.MODIFY_ACTIVITY);
        modifyScheduleRequestModel.setUserName(new PrefManager(context).getUserName());
        modifyScheduleRequestModel.setName(studyName);
        modifyScheduleRequestModel.setStartDate(startDate);
        modifyScheduleRequestModel.setEndDate(endDate);
        modifyScheduleRequestModel.setStatus(status);
        modifyScheduleRequestModel.setActivity(AppConstants.MODIFY_ACTIVITY);
        modifyScheduleRequestModel.setIsTrial(1);
        modifyScheduleRequestModel.setId(id);

        new NetworkingHelper(new ModifyScheduleRequest((Activity) context, true, modifyScheduleRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("modifySchedule API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifySchedule API success " +
                                        commonResponse.getResponse().get(0).getMessage());


                                /*Intent mNextActivity = new Intent(getActivity(), SelectRoleActivity.class);
                                startActivity(mNextActivity);
                                getActivity().finish();*/

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());


                            }else {

                                Logger.logError("modifySchedule API Failure1 " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifySchedule API Failure2 " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("modifySchedule API Failure3 " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("modifySchedule API Failure4 " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("modifySchedule Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("modifySchedule API Failure4 " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }


    private void showDeleteDialog() {

        Utils.createDialogTwoButtons(
                context, context.getString(R.string.study_delete),
                true, context.getString(R.string.delete_study_message),
                context.getString(R.string.dlg_yes_text),
                context.getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //CALL DELETE API
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
    }


}

