package com.hrfid.acs.view.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.DeleteScheduleRequest;
import com.hrfid.acs.helpers.request.DeleteSubjectRequest;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectRequestModel;
import com.hrfid.acs.helpers.request.ModifyScheduleRequest;
import com.hrfid.acs.helpers.request.ModifySubjectRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.DeleteScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.ModifySubjectRequestModel;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by MS on 2019-05-31.
 */
public class SubjectDetailsAdapter extends RecyclerView.Adapter<SubjectDetailsAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener {

    //ArrayList personNames;
    String[] status = { "APPROVED", "REJECTED", "IN_QUEUE"};
    Context context;
    String[] spnGender = {"Male","Female","Other"};
    String[] spnGroup = {"G1","G2","G3", "G4", "G5"};

    List<StudyList> studyLists;
    private  List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> listStudyID;
    private  List<Integer> listSpinnerStudyID;
    private RecyclerView recyclerView;

    public SubjectDetailsAdapter(Context context, List<StudyList> studyLists, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists, RecyclerView recyclerView) {
        this.context = context;
        this.studyLists = studyLists;
        this.listStudyID = lists;
        this.recyclerView = recyclerView;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subject_details_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        // holder.name.setText(" "+ personNames.get(position));
        // implement setOnClickListener event on item view.
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, ""+ personNames.get(position), Toast.LENGTH_SHORT).show();

                showModifyDialog();
            }
        });*/

     /* if( position ==1 || position ==4 || position ==7) {

          holder.txtStatus.setText("APPROVED");
          holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));

      }else if( position ==2 || position ==5 || position ==8) {

          holder.txtStatus.setText("REJECTED");
          holder.txtStatus.setTextColor(Color.RED);
      }else {

          holder.txtStatus.setText("IN_QUEUE");
          holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
      }*/


        if(studyLists != null) {

            holder.txtScreenId.setText(studyLists.get(position).getScreenId().trim());
            holder.txtDob.setText
                    (Utilities.splitDateFromDesired(studyLists.get(position).getDob()));
            holder.txtGroup.setText("" + studyLists.get(position).getGroupId());
            holder.txtGender.setText("" + studyLists.get(position).getGender());
            holder.txtStudyId.setText("" + studyLists.get(position).getStudyId());
            if (studyLists.get(position).getStatus().equalsIgnoreCase("In_Screening")) {
                holder.txtStatus.setText("In_Screening");
                holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));
            } else if (studyLists.get(position).getStatus().equalsIgnoreCase("INACTIVE")) {
                holder.txtStatus.setText("INACTIVE");
                holder.txtStatus.setTextColor(Color.RED);
            } else {
                holder.txtStatus.setText("In_Queue");
                holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
            }

            if(studyLists.get(position).getIsMapped() == 1){

                holder.btnMap.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);

            }else {

                holder.btnMap.setVisibility(View.VISIBLE);
                holder.btnDelete.setVisibility(View.VISIBLE);
            }

        }

        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showModifyDialog();

               // showModifyDialog(studyLists.get(position), lists);

                if(studyLists.get(position).getIsMapped() == 1){

                    showModifyDialogWithMapped(studyLists.get(position), listStudyID);

                }else {

                    showModifyDialog(studyLists.get(position), listStudyID);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showDeleteDialog();

                showDeleteDialog(studyLists.get(position).getId());
            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.createDialogTwoButtons(
                        context, context.getString(R.string.subject_mapping),
                        true, context.getString(R.string.alert_mapping),
                        context.getString(R.string.dlg_yes_text),
                        context.getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                //CALL MAP API
                                callSubjectMapAPI(studyLists.get(position).getId());

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });

            }
        });
    }

    private void callSubjectMapAPI(int ID) {

        MapSubjectRequestModel mapSubjectRequestModel = new MapSubjectRequestModel();
        mapSubjectRequestModel.setAppName(AppConstants.APP_NAME);
        mapSubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        mapSubjectRequestModel.setDeviceType(AppConstants.APP_OS);
        mapSubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        mapSubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        mapSubjectRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        mapSubjectRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        mapSubjectRequestModel.setEvent(AppConstants.MAP_SUBJECT);
        mapSubjectRequestModel.setUserName(new PrefManager(context).getUserName());
        mapSubjectRequestModel.setIsMapped(1);
        mapSubjectRequestModel.setStatus("In_Screening");
        mapSubjectRequestModel.setId(Integer.valueOf(ID));

        new NetworkingHelper(new MapSubjectDetailsRequest(context, true,
                mapSubjectRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("mapSubject API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("mapSubject API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                                getSubjectOnboardingDetails();


                            }else {

                                Logger.logError("subjectOnboard API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("subjectOnboard API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("subjectOnboard API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("mapSubject Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("mapSubject API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }


    @Override
    public int getItemCount() {
        return studyLists.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtScreenId;
        TextView txtGroup;
        TextView txtDob;
        TextView txtStatus;
        Button btnModify, btnDelete;
        TextView txtGender;
        TextView txtStudyId;
        Button btnMap;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtScreenId = (TextView) itemView.findViewById(R.id.txtScreenId);
            btnModify = (Button) itemView.findViewById(R.id.btnModify);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtGroup = (TextView) itemView.findViewById(R.id.txtGroup);
            txtDob = itemView.findViewById(R.id.txtDob);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtStudyId = itemView.findViewById(R.id.txtStudyId);
            btnMap = itemView.findViewById(R.id.btnMap);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //viewScreenStudyFragmentAdapter.removeItem(getAdapterPosition());

                    showDeleteDialog(studyLists.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    private void showModifyDialogWithMapped(final StudyList studyList, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists) {

        final EditText edtScreenId;

        listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(studyList.getStudyId());


        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_subject_modify_with_mapped);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //Adding value for ScreenID

        edtScreenId = dialog.findViewById(R.id.edtScreenId);
        edtScreenId.setText(studyList.getScreenId());
        edtScreenId.setEnabled(false);

        Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatusId);
        spnStatus.setOnItemSelectedListener(this);


        if(studyList.getStatus().equalsIgnoreCase("In_Screening")){
            status = new String[]{"APPROVE", "REJECT"};
        }/*else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "In_Screening", "In_Queue"};
        }else if(studyList.getStatus().equalsIgnoreCase("INQUEUE")){
            status = new String[]{"In_Queue", "INACTIVE", "In_Screening"};
        }else {

        }*/

        if(listSpinnerStudyID.get(0).equals(status)){

            Logger.log("Element found :"+status);
        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter adpStatus = new ArrayAdapter(context,android.R.layout.simple_spinner_item, status);
        adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adpStatus);

        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        // if decline button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
    }

    private void showModifyDialog(final StudyList studyList, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists) {

        final ImageButton txt_dob;
        final TextView txtDob;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];
        final EditText edtScreenId;

        listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(studyList.getStudyId());


        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_subject_modify);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //Adding value for ScreenID

        edtScreenId = dialog.findViewById(R.id.edtScreenId);
        edtScreenId.setText(studyList.getScreenId());
        edtScreenId.setEnabled(false);

  /*      //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spnBloodGroups = (Spinner) dialog.findViewById(R.id.spnBloodGroup);
        spnBloodGroups.setOnItemSelectedListener(this);*/

        final Spinner spnStudyIDs = (Spinner) dialog.findViewById(R.id.spnStudyId);
        spnStudyIDs.setOnItemSelectedListener(this);

        final Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
        spnGroups.setOnItemSelectedListener(this);

       /* Spinner spnPersonGender = (Spinner) dialog.findViewById(R.id.spnPersonGender);
        spnPersonGender.setOnItemSelectedListener(this);
*/
        //Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatusId);
        //spnStatus.setOnItemSelectedListener(this);


        if(studyList.getStatus().equalsIgnoreCase("ACTIVE")){
            status = new String[]{"ACTIVE", "INACTIVE", "IN_QUEUE"};
        }else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "ACTIVE", "IN_QUEUE"};
        }else if(studyList.getStatus().equalsIgnoreCase("INQUEUE")){
            status = new String[]{"IN_QUEUE", "INACTIVE", "ACTIVE"};
        }else {

        }

        if(listSpinnerStudyID.get(0).equals(status)){

            Logger.log("Element found :"+status);
        }

        //Creating the ArrayAdapter instance having the country list
        /*ArrayAdapter adpStatus = new ArrayAdapter(context,android.R.layout.simple_spinner_item, status);
        adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(adpStatus);*/


       /* //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bloodGroupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item,spnBloodGroup);
        bloodGroupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spnBloodGroups.setAdapter(bloodGroupAdp);*/

        if(studyList.getGender().equalsIgnoreCase("MALE")){
            spnGender = new String[]{"MALE", "FEMALE", "OTHER"};
        }else if(studyList.getGender().equalsIgnoreCase("FEMALE")){
            spnGender = new String[]{"FEMALE", "MALE", "OTHER"};
        }else if(studyList.getGender().equalsIgnoreCase("OTHER")){
            spnGender = new String[]{"OTHER", "MALE", "FEMALE"};
        }else {

        }

     /*   ArrayAdapter genderAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGender);
        genderAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonGender.setAdapter(genderAdp);*/

        List<String> lists1 = new ArrayList<>();

        for (int i = 0; i < listStudyID.size(); i++) {

            lists1.add(listStudyID.get(i).getLabel());

        }


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter studyIdAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, lists1);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);


        ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnGroup);
        groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGroups.setAdapter(groupAdp);

        txt_dob=(ImageButton)dialog.findViewById(R.id.btn_dob);
        txtDob=(TextView)dialog.findViewById(R.id.txt_dob);
        txtDob.setText(Utilities.splitDateFromDesired(studyList.getDob()));

        txt_dob.setOnClickListener(new View.OnClickListener() {
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


                                    txtDob.setText(year + "-" + paddedMonth + "-" + fDate);
                                    //endDate =txtEndDate.getText().toString();

                                } else {

                                    fmonth = "0" + monthOfYear;
                                    month = Integer.parseInt(fmonth) + 1;
                                    String paddedMonth = String.format("%02d", month);
                                    //editText.setText(dayOfMonth + "/" + paddedMonth + "/" + year);

                                    txtDob.setText(year + "-" + paddedMonth + "-" + dayOfMonth);
                                    //endDate =txtEndDate.getText().toString();
                                }

                            }
                        }, mYear[0], mMonth[0], mDay[0]);
                datePickerDialog.show();
            }
        });

        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();

               // callModifySubjectAPI(String studyName, String strStudyId, String strDoctorCode, String startDate, String endDate, String status, int id)

                if(!txtDob.getText().toString().equalsIgnoreCase("")){


                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Date date1 = null;
                    try {
                        date1 = format.parse(txtDob.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar cal = Calendar.getInstance();
                    Date sysDate = cal.getTime();

                    if(date1.compareTo(sysDate) >0) {


                        Toast.makeText(context,"Please select correct Date of Birth",Toast.LENGTH_SHORT).show();

                    }else {


                        callModifySubjectAPI(studyList, spnGroups.getSelectedItem().toString(), spnStudyIDs.getSelectedItem().toString());
                    }

                }else {
                    Toast.makeText(context,"Please Select Date Of Birth" , Toast.LENGTH_SHORT).show();
                }


            }
        });


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        // if decline button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });
    }


    /*private void showDeleteDialog() {

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
*/

    //Call getSubjectOnboardingDetails API
    private void getSubjectOnboardingDetails() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        commonRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_SUBJECT);
        commonRequestModel.setUserName(new PrefManager(context).getUserName());

        new NetworkingHelper(new GetSubjectDetailsRequest((Activity) context, true,
                commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetSubjectDetailsResponse getSubjectDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetSubjectDetailsResponse.class);

                        if (getSubjectDetailsResponse.getStatus().getCODE() == 200) {

                            if(getSubjectDetailsResponse.getStudyList().size() > 0){

                               // linearLayout.setVisibility(View.VISIBLE);
                                //textView.setVisibility(View.GONE);

                                Logger.logError("getSubjectOnboardingDetails API success status " +
                                        getSubjectDetailsResponse.getStatus());
                                Logger.logError("getSubjectOnboardingDetails API success getStudyList" +
                                        getSubjectDetailsResponse.getStudyList());

                                //getAllStudyID();

                                SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(context, getSubjectDetailsResponse.getStudyList(), listStudyID, recyclerView);
                                recyclerView.setAdapter(customAdapter);


                            }else {

                                Logger.logError("getSubjectOnboardingDetails API Failure " +
                                        "getStudyList" +
                                        getSubjectDetailsResponse.getStudyList());

                               // linearLayout.setVisibility(View.GONE);
                                //textView.setVisibility(View.VISIBLE);

                                Utils.showAlertDialog((Activity) context,  "NO DATA IN STUDY");
                            }

                        }else {

                            Logger.logError("getSubjectOnboardingDetails API Failure " +
                                    getSubjectDetailsResponse.getStatus().getCODE());
                            Logger.logError("getSubjectOnboardingDetails API Failure " +
                                    getSubjectDetailsResponse.getStatus().getMSG());

                            Utils.showAlertDialog((Activity) context,  getSubjectDetailsResponse.getStatus()
                                    .getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("getSubjectOnboardingDetails Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("getSubjectOnboardingDetails API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }


    private void showDeleteDialog(final int id) {

        Utils.createDialogTwoButtons(
                context, context.getString(R.string.subject_delete),
                true, context.getString(R.string.delete_subject_message),
                context.getString(R.string.dlg_yes_text),
                context.getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //CALL DELETE API
                        callDeleteSubjectAPI(id);
                        // dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
    }



    private void callDeleteSubjectAPI(int id) {

        DeleteScheduleRequestModel deleteScheduleRequestModel = new DeleteScheduleRequestModel();
        deleteScheduleRequestModel.setAppName(AppConstants.APP_NAME);
        deleteScheduleRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        deleteScheduleRequestModel.setDeviceType(AppConstants.APP_OS);
        deleteScheduleRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        deleteScheduleRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        deleteScheduleRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        deleteScheduleRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        deleteScheduleRequestModel.setEvent(AppConstants.DELETE_SUBJECT);
        deleteScheduleRequestModel.setUserName(new PrefManager(context).getUserName());
        deleteScheduleRequestModel.setId(String.valueOf(id));

        new NetworkingHelper(new DeleteSubjectRequest((Activity) context, true, deleteScheduleRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("deleteStudySchedule API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("deleteStudySchedule API success " +
                                        commonResponse.getResponse().get(0).getMessage());


                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                                /*viewScreenStudyFragmentAdapter.removeItem(adapterPosition);
                                viewScreenStudyFragmentAdapter.notifyDataSetChanged();*/
                                getSubjectOnboardingDetails();


                            }else {

                                Logger.logError("deleteStudySchedule API Failure1 " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("deleteStudySchedule API Failure2 " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("deleteStudySchedule API Failure3 " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("deleteStudySchedule API Failure4 " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("deleteStudySchedule Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("deleteStudySchedule API Failure4 " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }




    private void callModifySubjectAPI(StudyList studyList, String groupItem, String studyIdSelected) {

        ModifySubjectRequestModel modifySubjectRequestModel = new ModifySubjectRequestModel();
        modifySubjectRequestModel.setAppName(AppConstants.APP_NAME);
        modifySubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        modifySubjectRequestModel.setDeviceType(AppConstants.APP_OS);
        modifySubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        modifySubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        modifySubjectRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        modifySubjectRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        modifySubjectRequestModel.setEvent(AppConstants.MODIFY_SUBJECT);
        modifySubjectRequestModel.setUserName(new PrefManager(context).getUserName());

        modifySubjectRequestModel.setStatus(studyList.getStatus());
        modifySubjectRequestModel.setId(studyList.getId());
        modifySubjectRequestModel.setStudyId(studyIdSelected);
        modifySubjectRequestModel.setGroup(groupItem);
        modifySubjectRequestModel.setIsMapped(studyList.getIsMapped());
        modifySubjectRequestModel.setStudyName(studyList.getScreenId());
        modifySubjectRequestModel.setDob(studyList.getDob());
        //modifySubjectRequestModel.setIsApproved("");

        new NetworkingHelper(new ModifySubjectRequest((Activity) context, true, modifySubjectRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("modifySubject API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifySubject API success " +
                                        commonResponse.getResponse().get(0).getMessage());


                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                                getSubjectOnboardingDetails();
                                //viewScreenStudyFragmentAdapter.notifyDataSetChanged();


                            }else {

                                Logger.logError("modifySubject API Failure1 " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifySubject API Failure2 " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("modifySubject API Failure3 " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("modifySubject API Failure4 " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("modifySubject Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("modifySubject API Failure4 " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

}

