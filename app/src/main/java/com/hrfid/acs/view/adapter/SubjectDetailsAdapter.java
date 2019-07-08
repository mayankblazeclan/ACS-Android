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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.DeleteSubjectRequest;
import com.hrfid.acs.helpers.request.GetSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectRequestModel;
import com.hrfid.acs.helpers.request.ModifySubjectRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.DeleteScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.SubjectList;
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
  // String[] status = { "APPROVED", "REJECTED", "IN_QUEUE"};
  Context context;
  String[] spnGroupName = {"-", "G1","G2","G3", "G4", "G5"};
  private String spnSelectedStudyID ="";
  private String spnSelectedStudyValue ="";
  List<String> lists1 = new ArrayList<>();
  private Spinner spnStudyIDs;

  List<SubjectList> subjectLists;
  private  List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> listStudyID;
  //private  List<Integer> listSpinnerStudyID;
  private RecyclerView recyclerView;

  public SubjectDetailsAdapter(Context context, List<SubjectList> subjectLists, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists, RecyclerView recyclerView) {
    this.context = context;
    this.subjectLists = subjectLists;
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
    if(subjectLists != null) {

      holder.txtScreenId.setText(subjectLists.get(position).getScreenId().trim());
      holder.txtDob.setText
              (Utilities.splitDateFromDesired(subjectLists.get(position).getDOB()));
      holder.txtGroup.setText("" + subjectLists.get(position).getGroupId());
      holder.txtGender.setText("" + subjectLists.get(position).getGender());
      holder.txtStudyId.setText("" +subjectLists.get(position).getStudyTitle()+ "("+ subjectLists.get(position).getStudyName()+")");
      holder.txtIntials.setText("" + subjectLists.get(position).getInitials());

      if(subjectLists.get(position).getRandNumber()!=null && !subjectLists.get(position).getRandNumber().equalsIgnoreCase(" ")) {
        holder.txtRandNo.setText("" + subjectLists.get(position).getRandNumber());
      }else {
        holder.txtRandNo.setText("--");
      }

      if(subjectLists.get(position).getEAntigenStatus()!=null && !subjectLists.get(position).getEAntigenStatus().equalsIgnoreCase(" ")) {
        holder.txtEAntigenStatus.setText("" +subjectLists.get(position).getEAntigenStatus());
      }else {
        holder.txtEAntigenStatus.setText("--");
      }


      if(subjectLists.get(position).getPkSubStudy()!=null && !subjectLists.get(position).getPkSubStudy().equalsIgnoreCase(" ")) {
        holder.txtOptionalPKSubstatus.setText("" +subjectLists.get(position).getPkSubStudy());
      }else {
        holder.txtOptionalPKSubstatus.setText("--");
      }


      if(subjectLists.get(position).getLeukapheresis()!=null && !subjectLists.get(position).getLeukapheresis().equalsIgnoreCase(" ")) {
        holder.txt_optional_leukapherisi.setText("" +subjectLists.get(position).getLeukapheresis());
      }else {
        holder.txt_optional_leukapherisi.setText("--");
      }


      if(subjectLists.get(position).getGenomic()!=null && !subjectLists.get(position).getGenomic().equalsIgnoreCase(" ")) {
        holder.txt_optional_genomic.setText("" +subjectLists.get(position).getGenomic());
      }else {
        holder.txt_optional_genomic.setText("--");
      }

      if(subjectLists.get(position).getFutureReserch()!=null && !subjectLists.get(position).getFutureReserch().equalsIgnoreCase(" ")) {
        holder.txt_optional_future_research.setText("" +subjectLists.get(position).getFutureReserch());
      }else {
        holder.txt_optional_future_research.setText("--");
      }






      if (subjectLists.get(position).getStatus().equalsIgnoreCase("In_Screening")) {
        holder.txtStatus.setText("In_Screening");
        holder.txtStatus.setTextColor(Color.parseColor("#F9980B"));
      } else if (subjectLists.get(position).getStatus().equalsIgnoreCase("In_Trial")) {
        holder.txtStatus.setText("In_Trial");
        holder.txtStatus.setTextColor(Color.parseColor("#5AA105"));
      } else if (subjectLists.get(position).getStatus().equalsIgnoreCase("Rejected")) {
        if(subjectLists.get(position).getRejection_reason()!=null) {
          holder.txtStatus.setText("Rejected" + " (" + subjectLists.get(position).getRejection_reason() + ")");
          holder.txtStatus.setTextColor(Color.RED);
        }else {
          holder.txtStatus.setText("Rejected");
          holder.txtStatus.setTextColor(Color.RED);
        }
      } else if (subjectLists.get(position).getStatus().equalsIgnoreCase("Withdrawal")) {
        if(subjectLists.get(position).getRejection_reason()!=null) {
          holder.txtStatus.setText("Withdrawal" + " (" + subjectLists.get(position).getRejection_reason() + ")");
          holder.txtStatus.setTextColor(Color.RED);
        }else {
          holder.txtStatus.setText("Withdrawal");
          holder.txtStatus.setTextColor(Color.RED);
        }
      }else {
        holder.txtStatus.setText("IN QUEUE");
        holder.txtStatus.setTextColor(Color.parseColor("#5dade2"));
      }

      if(subjectLists.get(position).getIsMapped() == 1){

        holder.btnMap.setVisibility(View.GONE);
        holder.btnDelete.setVisibility(View.GONE);

      }
      else {

        holder.btnModify.setVisibility(View.VISIBLE);
        holder.btnMap.setVisibility(View.VISIBLE);
        holder.btnDelete.setVisibility(View.VISIBLE);
      }

      if(subjectLists.get(position).getStatus().equalsIgnoreCase("Rejected")){

        holder.btnMap.setVisibility(View.GONE);
        holder.btnDelete.setVisibility(View.GONE);
        holder.btnModify.setVisibility(View.GONE);

      }else if(subjectLists.get(position).getStatus().equalsIgnoreCase("Withdrawal")){

        holder.btnMap.setVisibility(View.GONE);
        holder.btnDelete.setVisibility(View.GONE);
        holder.btnModify.setVisibility(View.GONE);

      }else {

      }

    }

    holder.btnModify.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //showModifyDialog();

        // showModifyDialog(studyLists.get(position), lists);

        if(subjectLists.get(position).getIsMapped() == 1){

          if(subjectLists.get(position).getStatus().equalsIgnoreCase("In_Trial")){

            showModifyDialogWithMapped_InTrial(subjectLists.get(position), listStudyID);
          }else {
            showModifyDialogWithMapped(subjectLists.get(position), listStudyID);
          }

        }else {

          showModifyDialog(subjectLists.get(position), listStudyID);
        }
      }
    });

    holder.btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //showDeleteDialog();

        showDeleteDialog(subjectLists.get(position).getId());
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
                    callSubjectMapAPI(subjectLists.get(position).getId());

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
    return subjectLists.size();
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //  Toast.makeText(context,status[position] , Toast.LENGTH_SHORT).show();

    switch(parent.getId()){
      case R.id.spnGroup:
        //Your Action Here.spnGroups.getSelectedItem().toString() spnStatusId
        //Toast.makeText(context, parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
        break;

      case R.id.spnStatusId:
        //Your Action Here.spnGroups.getSelectedItem().toString() spnStatusId
        // Toast.makeText(context, parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
        break;

      case R.id.spnStudyId :
        //Your Action Here.
        if(position !=0) {
          for (int i = 0; i < listStudyID.size(); i++) {

            if(listStudyID.get(i).getLabel().equalsIgnoreCase(spnStudyIDs.getSelectedItem().toString()))
            {
              spnSelectedStudyID = String.valueOf(listStudyID.get(i).getStudyId());
              spnSelectedStudyValue = String.valueOf(listStudyID.get(i).getValue());
             // Toast.makeText(context, spnSelectedStudyID+ " value :" + spnSelectedStudyValue, Toast.LENGTH_SHORT).show();
            }

          }
          //Toast.makeText(context, spnSelectedStudyID, Toast.LENGTH_SHORT).show();
        }else {
          spnSelectedStudyID = String.valueOf(listStudyID.get(position).getStudyId());
          spnSelectedStudyValue = String.valueOf(listStudyID.get(position).getValue());
          //Toast.makeText(context, spnSelectedStudyID, Toast.LENGTH_SHORT).show();

          // Toast.makeText(context, spnSelectedStudyID+ " value :" + spnSelectedStudyValue, Toast.LENGTH_SHORT).show();

        }
        break;
    }
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
    TextView txtIntials;
    TextView txtRandNo;
    TextView txtEAntigenStatus;
    TextView txtOptionalPKSubstatus;
    TextView txt_optional_leukapherisi;
    TextView txt_optional_genomic;
    TextView txt_optional_future_research;


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
      txtIntials = itemView.findViewById(R.id.txtIntials);
      txtRandNo = itemView.findViewById(R.id.txtRandNo);
      txtEAntigenStatus= itemView.findViewById(R.id.txtEAntigenStatus);
      txtOptionalPKSubstatus= itemView.findViewById(R.id.txtOptionalPKSubstatus);
      txt_optional_leukapherisi= itemView.findViewById(R.id.txt_optional_leukapherisi);
      txt_optional_genomic= itemView.findViewById(R.id.txt_optional_genomic);
      txt_optional_future_research= itemView.findViewById(R.id.txt_optional_future_research);

      btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //viewScreenStudyFragmentAdapter.removeItem(getAdapterPosition());
          showDeleteDialog(subjectLists.get(getAdapterPosition()).getId());
        }
      });
    }
  }

  private void showModifyDialogWithMapped(final SubjectList subjectList, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists) {

    final EditText edtScreenId;

       /* listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(studyList.getStudyId());*/


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
    edtScreenId.setText(subjectList.getScreenId());
    edtScreenId.setEnabled(false);

    final Spinner spnStatus = (Spinner) dialog.findViewById(R.id.spnStatusId);
    spnStatus.setOnItemSelectedListener(this);

    String[] status;
    if(subjectList.getStatus().equalsIgnoreCase("In_Screening")){
      status = new String[]{"--", "APPROVE", "REJECT"};
    }else {
      status = new String[]{"--", "APPROVE", "REJECT"};
    }
            /*else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "In_Screening", "In_Queue"};
        }else if(studyList.getStatus().equalsIgnoreCase("INQUEUE")){
            status = new String[]{"In_Queue", "INACTIVE", "In_Screening"};
        }else {

        }*/


    /*    if(listSpinnerStudyID.get(0).equals(status)){

            Logger.log("Element found :"+status);
        }*/

    final Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
    spnGroups.setOnItemSelectedListener(this);

    List<String> listsGroup = new ArrayList<>();

    listsGroup.add(subjectList.getGroupId());
    //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

    for (int i = 0; i < 6; i++) {
      if(!subjectList.getGroupId().equalsIgnoreCase(spnGroupName[i])) {
        listsGroup.add(spnGroupName[i]);
        // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
      }
    }

    //Creating the ArrayAdapter instance having the country list
    ArrayAdapter adpStatus = new ArrayAdapter(context,android.R.layout.simple_spinner_item, status);
    adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnStatus.setAdapter(adpStatus);


    ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup);
    groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnGroups.setAdapter(groupAdp);

    dialog.show();

    Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
    // if decline button is clicked, close the custom dialog
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Close dialog
        dialog.dismiss();
        callModifySubjectWithMappedAPI(subjectList, spnStatus.getSelectedItem().toString(), spnGroups.getSelectedItem().toString(), "In_Screening");
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


  private void showModifyDialogWithMapped_InTrial(final SubjectList subjectList, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists) {

    final EditText edtScreenId;

       /* listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(studyList.getStudyId());*/


    // Create custom dialog object
    final Dialog dialog = new Dialog(context);
    // Include dialog.xml file
    dialog.setContentView(R.layout.dialog_subject_modify_with_mapped_in_trial);
    dialog.setCancelable(false);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    Window window = dialog.getWindow();
    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    //Adding value for ScreenID

    edtScreenId = dialog.findViewById(R.id.edtScreenId);
    edtScreenId.setText(subjectList.getScreenId());
    edtScreenId.setEnabled(false);


            /*else if(studyList.getStatus().equalsIgnoreCase("INACTIVE")){
            status = new String[]{"INACTIVE", "In_Screening", "In_Queue"};
        }else if(studyList.getStatus().equalsIgnoreCase("INQUEUE")){
            status = new String[]{"In_Queue", "INACTIVE", "In_Screening"};
        }else {

        }*/


    /*    if(listSpinnerStudyID.get(0).equals(status)){

            Logger.log("Element found :"+status);
        }*/

    final Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
    spnGroups.setOnItemSelectedListener(this);

    List<String> listsGroup = new ArrayList<>();

    listsGroup.add(subjectList.getGroupId());
    //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

    for (int i = 0; i < 6; i++) {
      if(!subjectList.getGroupId().equalsIgnoreCase(spnGroupName[i])) {
        listsGroup.add(spnGroupName[i]);
        // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
      }
    }

    ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup);
    groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnGroups.setAdapter(groupAdp);

    dialog.show();

    Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
    // if decline button is clicked, close the custom dialog
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Close dialog
        dialog.dismiss();
        callModifySubjectWithMappedAPI(subjectList, "--", spnGroups.getSelectedItem().toString(), "In_Trial");
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

  private void showModifyDialog(final SubjectList subjectList, List<com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList> lists) {

    final ImageButton txt_dob;
    final TextView txtDob;
    final int[] mYear = new int[1];
    final int[] mMonth = new int[1];
    final int[] mDay = new int[1];
    final EditText edtScreenId;
    final Switch aSwitchOptionalData;
    final LinearLayout ll_optional_data;
    final EditText edtInitials;
    final EditText edtRand;
    final RadioGroup radioGroupEStatus;
    final RadioGroup radioGroupPKSubstudy;
    final RadioGroup radioGroupLeuka;
    final RadioGroup radioGroupGenomic;
    final RadioGroup radioGroupFuture;

    final RadioButton radioButtonEStatusNA;
    final RadioButton radioButtonEStatusPOS;
    final RadioButton radioButtonEStatusNEG;
    final RadioButton radioButtonPKStatusNA;
    final RadioButton radioButtonPKStatusYES;
    final RadioButton radioButtonPKStatusNO;
    final RadioButton radioButtonLEUKAStatusNA;
    final RadioButton radioButtonLEUKAStatusYES;
    final RadioButton radioButtonLEUKAStatusNO;
    final RadioButton radioButtonGenomicStatusNA;
    final RadioButton radioButtonGenomicStatusYES;
    final RadioButton radioButtonGenomicStatusNO;
    final RadioButton radioButtonFutureStatusNA;
    final RadioButton radioButtonFutureStatusYES;
    final RadioButton radioButtonFutureStatusNO;
    final int[] isOptional = {0};

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
    edtScreenId.setText(subjectList.getScreenId());
    edtScreenId.setEnabled(false);

    //Adding Initials Values
    edtInitials = dialog.findViewById(R.id.edtInitials);
    edtInitials.setText(subjectList.getInitials());


    //Adding RAND Values
    edtRand = dialog.findViewById(R.id.edtRand);
    edtRand.setText(subjectList.getRandNumber());

    radioGroupEStatus =(RadioGroup) dialog.findViewById(R.id.radioGroup);
    radioGroupPKSubstudy =(RadioGroup) dialog.findViewById(R.id.radioGroup1);
    radioGroupLeuka =(RadioGroup) dialog.findViewById(R.id.radioGroup2);
    radioGroupGenomic =(RadioGroup) dialog.findViewById(R.id.radioGroup3);
    radioGroupFuture =(RadioGroup) dialog.findViewById(R.id.radioGroup4);

    //E-ANTIGEN STATUS
    radioButtonEStatusNA = (RadioButton) dialog.findViewById(R.id.radioNA);
    radioButtonEStatusPOS = (RadioButton) dialog.findViewById(R.id.radioYES);
    radioButtonEStatusNEG = (RadioButton) dialog.findViewById(R.id.radioNO);

    //PK Study
    radioButtonPKStatusNA = (RadioButton) dialog.findViewById(R.id.radioNA1);
    radioButtonPKStatusYES = (RadioButton) dialog.findViewById(R.id.radioYES1);
    radioButtonPKStatusNO = (RadioButton) dialog.findViewById(R.id.radioNO1);

    //LEUKA Study
    radioButtonLEUKAStatusNA = (RadioButton) dialog.findViewById(R.id.radioNA2);
    radioButtonLEUKAStatusYES = (RadioButton) dialog.findViewById(R.id.radioYES2);
    radioButtonLEUKAStatusNO = (RadioButton) dialog.findViewById(R.id.radioNO2);

    //GENOMIC Study
    radioButtonGenomicStatusNA = (RadioButton) dialog.findViewById(R.id.radioNA3);
    radioButtonGenomicStatusYES = (RadioButton) dialog.findViewById(R.id.radioYES3);
    radioButtonGenomicStatusNO = (RadioButton) dialog.findViewById(R.id.radioNO3);

    //FUTURE STATUS Study
    radioButtonFutureStatusNA = (RadioButton) dialog.findViewById(R.id.radioNA4);
    radioButtonFutureStatusYES = (RadioButton) dialog.findViewById(R.id.radioYES4);
    radioButtonFutureStatusNO = (RadioButton) dialog.findViewById(R.id.radioNO4);

    if(subjectList.getEAntigenStatus().equalsIgnoreCase("NEG")){

      radioButtonEStatusNEG.setChecked(true);

    }else if(subjectList.getEAntigenStatus().equalsIgnoreCase("POS")){

      radioButtonEStatusPOS.setChecked(true);
    }else {

      radioButtonEStatusNA.setChecked(true);
    }


    //Set Checked Radio Button For Study PK
    if(subjectList.getPkSubStudy().equalsIgnoreCase("NO")){

      radioButtonPKStatusNO.setChecked(true);

    }else if(subjectList.getPkSubStudy().equalsIgnoreCase("YES")){

      radioButtonPKStatusYES.setChecked(true);
    }else {

      radioButtonPKStatusNA.setChecked(true);
    }

    //Set Checked Radio Button For LEUKAPHERSIS
    if(subjectList.getLeukapheresis().equalsIgnoreCase("NO")){

      radioButtonLEUKAStatusNO.setChecked(true);

    }else if(subjectList.getLeukapheresis().equalsIgnoreCase("YES")){

      radioButtonLEUKAStatusYES.setChecked(true);
    }else {

      radioButtonLEUKAStatusNA.setChecked(true);
    }

    //Set Checked Radio Button For GENOMIC
    if(subjectList.getGenomic().equalsIgnoreCase("NO")){

      radioButtonGenomicStatusNO.setChecked(true);

    }else if(subjectList.getGenomic().equalsIgnoreCase("YES")){

      radioButtonGenomicStatusYES.setChecked(true);
    }else {

      radioButtonGenomicStatusNA.setChecked(true);
    }


    //Set Checked Radio Button For FUTURE RESEARCH
    if(subjectList.getFutureReserch().equalsIgnoreCase("NO")){

      radioButtonFutureStatusNO.setChecked(true);

    }else if(subjectList.getFutureReserch().equalsIgnoreCase("YES")){

      radioButtonFutureStatusYES.setChecked(true);
    }else {

      radioButtonFutureStatusNA.setChecked(true);
    }

    spnStudyIDs = (Spinner) dialog.findViewById(R.id.spnStudyId);
    spnStudyIDs.setOnItemSelectedListener(this);

    final Spinner spnGroups = (Spinner) dialog.findViewById(R.id.spnGroup);
    spnGroups.setOnItemSelectedListener(this);




    List<String> listsGroup = new ArrayList<>();

    listsGroup.add(subjectList.getGroupId());
    //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

    for (int i = 0; i < 6; i++) {
      if(!subjectList.getGroupId().equalsIgnoreCase(spnGroupName[i])) {
        listsGroup.add(spnGroupName[i]);
        // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
      }
    }

    lists1.clear();

    String s = subjectList.getStudyTitle()+ " ("+String.valueOf(subjectList.getStudyName())+")";

    //lists1.add(s);
    //System.out.println("listStudyID (1) :" + String.valueOf(subjectList.getStudyName()));

    for (int i = 0; i < listStudyID.size(); i++) {
      // if (!s.equalsIgnoreCase(listStudyID.get(i).getLabel())) {
      lists1.add(listStudyID.get(i).getLabel());
      //System.out.println("listStudyID name :" + listStudyID.get(i).getLabel());
      //}
    }



    //Creating the ArrayAdapter instance having the country list
    ArrayAdapter studyIdAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, lists1);
    studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnStudyIDs.setAdapter(studyIdAdp);
    spnStudyIDs.setSelection(subjectList.getStudyId()-1);


    ArrayAdapter groupAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup);
    groupAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnGroups.setAdapter(groupAdp);

    txt_dob=(ImageButton)dialog.findViewById(R.id.btn_dob);
    txtDob=(TextView)dialog.findViewById(R.id.txt_dob);
    txtDob.setText(Utilities.splitDateFromDesired(subjectList.getDOB()));


    ll_optional_data =(LinearLayout) dialog.findViewById(R.id.ll_optional_data);
    ll_optional_data.setVisibility(View.GONE);



    aSwitchOptionalData = (Switch) dialog.findViewById(R.id.switch1);
    aSwitchOptionalData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          // The toggle is enabled
          ll_optional_data.setVisibility(View.VISIBLE);
          isOptional[0] =1;
        } else {
          // The toggle is disabled
          ll_optional_data.setVisibility(View.GONE);
          isOptional[0] =0;
        }
      }
    });

    if(subjectList.getOptionalRequired() == 1){
      aSwitchOptionalData.setChecked(true);
    }else {
      aSwitchOptionalData.setChecked(false);
    }
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

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (1000 * 60 * 60));

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


        // callModifySubjectAPI(String studyName, String strStudyId, String strDoctorCode, String startDate, String endDate, String status, int id)

        if(edtInitials.length() >0) {

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



              if(isOptional[0] ==0)
              {

                            /*callAddSubjectOnBoardingAPI(edtStudyName.getText().toString(),
                                    txt_date_of_birth.getText().toString(),
                                    spnPersonGender.getSelectedItem().toString(),
                                    spnGroups.getSelectedItem().toString(),
                                    spnSelectedStudyID,
                                    editTextInitials.getText().toString(),
                                    isOptional[0],
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " ");*/

                dialog.dismiss();

                callModifySubjectAPI(subjectList, spnGroups.getSelectedItem().toString(),
                        spnStudyIDs.getSelectedItem().toString(), spnSelectedStudyID,
                        spnSelectedStudyValue, txtDob.getText().toString(),
                        edtInitials.getText().toString(), isOptional[0],
                        " ", " ", " ", " ",
                        " ", " ");


              }else {

                RadioButton rbEStatus;
                RadioButton rbPKStatus;
                RadioButton rbLeukaStatus;
                RadioButton rbGenomicStatus;
                RadioButton rbFutureStatus;

                int selectedId=radioGroupEStatus.getCheckedRadioButtonId();
                rbEStatus=(RadioButton)dialog.findViewById(selectedId);


                int selectedId1=radioGroupPKSubstudy.getCheckedRadioButtonId();
                rbPKStatus=(RadioButton)dialog.findViewById(selectedId1);


                int selectedId2=radioGroupLeuka.getCheckedRadioButtonId();
                rbLeukaStatus=(RadioButton)dialog.findViewById(selectedId2);


                int selectedId3=radioGroupGenomic.getCheckedRadioButtonId();
                rbGenomicStatus=(RadioButton)dialog.findViewById(selectedId3);


                int selectedId4=radioGroupFuture.getCheckedRadioButtonId();
                rbFutureStatus=(RadioButton)dialog.findViewById(selectedId4);

                //if(edtRand.getText().toString().trim().length() > 0){

                  dialog.dismiss();

                  callModifySubjectAPI(subjectList, spnGroups.getSelectedItem().toString(),
                          spnStudyIDs.getSelectedItem().toString(),
                          spnSelectedStudyID,
                          spnSelectedStudyValue,
                          txtDob.getText().toString(),
                          edtInitials.getText().toString(), isOptional[0],
                          edtRand.getText().toString(),
                          rbEStatus.getText().toString().trim(),
                          rbPKStatus.getText().toString().trim(),
                          rbLeukaStatus.getText().toString().trim(),
                          rbGenomicStatus.getText().toString().trim(),
                          rbFutureStatus.getText().toString().trim());

                /*}else {
                  Toast.makeText(context,"Please enter RAND" , Toast.LENGTH_SHORT).show();
                }*/

              }


            }

          }else {
            Toast.makeText(context,"Please Select Date Of Birth" , Toast.LENGTH_SHORT).show();
          }

        }else {
          Toast.makeText(context,"Please enter Initials" , Toast.LENGTH_SHORT).show();
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

              if(getSubjectDetailsResponse.getSubjectList().size() > 0){

                // linearLayout.setVisibility(View.VISIBLE);
                //textView.setVisibility(View.GONE);

                Logger.logError("getSubjectOnboardingDetails API success status " +
                        getSubjectDetailsResponse.getStatus());
                Logger.logError("getSubjectOnboardingDetails API success getStudyList" +
                        getSubjectDetailsResponse.getSubjectList());

                //getAllStudyID();

                SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(context, getSubjectDetailsResponse.getSubjectList(), listStudyID, recyclerView);
                recyclerView.setAdapter(customAdapter);


              }else {

                Logger.logError("getSubjectOnboardingDetails API Failure " +
                        "getStudyList" +
                        getSubjectDetailsResponse.getSubjectList());

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




  private void callModifySubjectAPI(SubjectList studyList, String spnGroups, String spnStudyIDLabel, String studyID, String spnSelectedStudyValue, String dob,
                                    String initials,
                                    int isOptional, String strRand,  String eStatus,
                                    String strPkStudy, String strLeuka, String strGenomic,
                                    String strFuture) {

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
    modifySubjectRequestModel.setStudyId(spnSelectedStudyValue);
    modifySubjectRequestModel.setGroup(spnGroups);
    modifySubjectRequestModel.setIsMapped(studyList.getIsMapped());
    modifySubjectRequestModel.setStudyName(studyID);
    modifySubjectRequestModel.setDob(dob);
    modifySubjectRequestModel.setInitials(initials);
    //modifySubjectRequestModel.setRandNum(strRand);
    if(strRand.equalsIgnoreCase("")){
      modifySubjectRequestModel.setRandNum(" ");
    }else {
      modifySubjectRequestModel.setRandNum(strRand);
    }
    modifySubjectRequestModel.setAntigenStatus(eStatus);
    modifySubjectRequestModel.setPKSubStudy(strPkStudy);
    modifySubjectRequestModel.setLeuka(strLeuka);
    modifySubjectRequestModel.setGenomic(strGenomic);
    modifySubjectRequestModel.setFResearch(strFuture);
    modifySubjectRequestModel.setIsOptional(isOptional);
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




  private void callModifySubjectWithMappedAPI(SubjectList studyList, String spnStatus, String groupValue, String statusValue) {

    ModifySubjectRequestModel modifySubjectRequestModel = new ModifySubjectRequestModel();
    modifySubjectRequestModel.setAppName(AppConstants.APP_NAME);
    modifySubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
    modifySubjectRequestModel.setDeviceType(AppConstants.APP_OS);
    modifySubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
    modifySubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
    modifySubjectRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
    modifySubjectRequestModel.setTagId(new PrefManager(context).getBarCodeValue());

    modifySubjectRequestModel.setUserName(new PrefManager(context).getUserName());

    // modifySubjectRequestModel.setStatus(spnStatus);
    modifySubjectRequestModel.setId(studyList.getId());
    modifySubjectRequestModel.setGroup(groupValue);
    modifySubjectRequestModel.setIsMapped(studyList.getIsMapped());
    modifySubjectRequestModel.setDob(studyList.getDOB());

    modifySubjectRequestModel.setStudyName(""+studyList.getStudyName());
    modifySubjectRequestModel.setStudyId(String.valueOf(studyList.getStudyId()));


    //Newly Added Parameter
    modifySubjectRequestModel.setInitials(studyList.getInitials());
    modifySubjectRequestModel.setRandNum(studyList.getRandNumber());
    modifySubjectRequestModel.setAntigenStatus(studyList.getEAntigenStatus());
    modifySubjectRequestModel.setPKSubStudy(studyList.getPkSubStudy());
    modifySubjectRequestModel.setLeuka(studyList.getLeukapheresis());
    modifySubjectRequestModel.setGenomic(studyList.getGenomic());
    modifySubjectRequestModel.setFResearch(studyList.getFutureReserch());
    modifySubjectRequestModel.setIsOptional(studyList.getOptionalRequired());

    if(spnStatus.equalsIgnoreCase("APPROVE")){
      modifySubjectRequestModel.setIsApproved("1");
      modifySubjectRequestModel.setStatus("In_Trial");
      modifySubjectRequestModel.setEvent(AppConstants.SUBJECT_APPROVE);
    }else if(spnStatus.equalsIgnoreCase("--")){
      //modifySubjectRequestModel.setIsApproved("1");
      modifySubjectRequestModel.setStatus(statusValue);
      modifySubjectRequestModel.setEvent(AppConstants.MODIFY_SUBJECT);
    }else  {
      modifySubjectRequestModel.setIsApproved("0");
      modifySubjectRequestModel.setStatus("Rejected");
      modifySubjectRequestModel.setEvent(AppConstants.SUBJECT_REJECT);
    }
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

              Utils.showAlertDialog((Activity) context,  commonResponse.getStatus().geteRROR());
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

