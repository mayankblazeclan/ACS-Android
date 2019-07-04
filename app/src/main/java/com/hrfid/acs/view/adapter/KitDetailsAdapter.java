package com.hrfid.acs.view.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hrfid.acs.helpers.request.DismissKitDetailsRequest;
import com.hrfid.acs.helpers.request.DismissKitRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetKitDetailsRequest;
import com.hrfid.acs.helpers.request.MapKitDetailsRequest;
import com.hrfid.acs.helpers.request.MapKitRequestModel;
import com.hrfid.acs.helpers.request.MapSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectRequestModel;
import com.hrfid.acs.helpers.request.ModifyKitRequest;
import com.hrfid.acs.helpers.request.ModifyKitRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetKitDetails.GetKitDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetKitDetails.KitList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.barcode.ShowReplicateListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by MS on 2019-05-31.
 */
public class KitDetailsAdapter extends RecyclerView.Adapter<KitDetailsAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener, View.OnClickListener {


   // String[] sNumber = {"0", "1","2","3", "4", "5", "6", "7", "8", "9", "10"};

    //private Button btnGenerateBarcode;
    private Button btnSubmit;
    private String message = "";
    private ImageView imageView;
    // private TextView txt_start_date;
    private int mYear, mMonth, mDay;
    private String startDate ="";
    private String endDate = "";
    private  Spinner spnStudyIDs, spnLocal, spnCentral, spnAliquot;
    //private Button btnReplicate;
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
    private TextView editTextKIT_ID;
    private EditText editTextAccessionNumber;
    private EditText editTextVISIT;
    private String strStudyName;
    private String strStudyTitle;
    private RadioButton rbScreening, rbTrial;
    private RadioButton rbAdYes, rbAdNo;
    private RadioButton rbReqFormYES, rbReqFormNO;

    Context context;
    String[] spnReason = {"DAMAGE", "MISSING"};
    List<KitList> kitLists;
    private  List<StudyList> getListStudy;
    private  List<Integer> listSpinnerStudyID;
    private RecyclerView recyclerView;
    List<String> listStudyLabel = new ArrayList<>();

    public KitDetailsAdapter(Context context, List<KitList> kitLists, List<StudyList> lists, RecyclerView recyclerView) {
        this.context = context;
        this.kitLists = kitLists;
        this.getListStudy = lists;
        this.recyclerView = recyclerView;
        //getAllStudyID();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_kit_details_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

        holder.txtKitId.setText(kitLists.get(position).getKitId().trim());
        holder.txtStudyName.setText("" +kitLists.get(position).getStudyTitle()+ "("+ kitLists.get(position).getStudyName()+")");
        holder.txtVisit.setText(kitLists.get(position).getVisit().trim());
        holder.txtVisit.setSelected(true);

        if(kitLists.get(position).getIsTrial() == 1) {
            holder.txtKitType.setText("TRIAL");
        }else {
            holder.txtKitType.setText("SCREENING");
        }
        //holder.txt_status.setText(kitLists.get(position).getStatus().trim());

        if (kitLists.get(position).getStatus().equalsIgnoreCase("In_Screening")) {
            holder.txt_status.setText("In_Screening");
            holder.txt_status.setTextColor(Color.parseColor("#F9980B"));
        } else if (kitLists.get(position).getStatus().equalsIgnoreCase("In_Trial")) {
            holder.txt_status.setText("In_Trial");
            holder.txt_status.setTextColor(Color.parseColor("#5AA105"));
        } else if (kitLists.get(position).getStatus().equalsIgnoreCase("Dismissed")) {
            holder.txt_status.setText("Dismissed");
            holder.txt_status.setTextColor(Color.RED);
        }else {
            holder.txt_status.setText("In_Stock");
            holder.txt_status.setTextColor(Color.parseColor("#5dade2"));
        }

        if(kitLists.get(position).getAdditionalKit() ==0) {
            holder.txtAdditionalKit.setText("NO");
        }else {
            holder.txtAdditionalKit.setText("YES");
        }

        if(kitLists.get(position).getRequisitionForm() ==0) {
            holder.txt_requistion_form.setText("NO");
        }else {
            holder.txt_requistion_form.setText("YES");
        }

        if(kitLists.get(position).getExtNumber().equalsIgnoreCase(" ")) {
            holder.txtExtNumber.setText("--");
        }else {
            holder.txtExtNumber.setText(kitLists.get(position).getExtNumber().trim());
        }


        holder.txtCategory.setText(kitLists.get(position).getCategory().trim());
        holder.txtLocal.setText(""+kitLists.get(position).getLocal());
        holder.txtCentral.setText(""+kitLists.get(position).getCentral());
        holder.txt_aliquot.setText(""+kitLists.get(position).getAliquot());
        holder.txtScanDate.setText
                (Utilities.splitDateFromDesired(kitLists.get(position).getScanDate()));
        holder.txt_exp_date.setText
                (Utilities.splitDateFromDesired(kitLists.get(position).getExpiryDate()));

        if(kitLists.get(position).getIsMapped() == 1){

            holder.btnMap.setVisibility(View.GONE);
            holder.btnModify.setVisibility(View.GONE);

        }
        else {

            holder.btnModify.setVisibility(View.VISIBLE);
            holder.btnMap.setVisibility(View.VISIBLE);
            holder.btnDismiss.setVisibility(View.VISIBLE);
        }

        if(kitLists.get(position).getStatus().equalsIgnoreCase("Dismissed")){
            holder.btnModify.setVisibility(View.GONE);
            holder.btnMap.setVisibility(View.GONE);
            holder.btnDismiss.setVisibility(View.GONE);
        }else {

        }

        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showModifyDialog();

                showModifyDialog(kitLists.get(position), getListStudy);
            }
        });

        holder.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDeleteDialog(kitLists.get(position));
            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showDeleteDialog();

                Utils.createDialogTwoButtons(
                        context, context.getString(R.string.kit_mapping),
                        true, context.getString(R.string.kit_alert_mapping),
                        context.getString(R.string.dlg_yes_text),
                        context.getString(R.string.dlg_no_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                //CALL MAP API
                                callKitMapAPI(kitLists.get(position).getId(), kitLists.get(position).getIsTrial());

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




    @Override
    public int getItemCount() {
        return kitLists.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();

        // TODO Auto-generated method stub
        switch(parent.getId()){

            case R.id.spnStatusId :
                //Your Action Here.
               /* spnSelectedStudyID = String.valueOf(listStudy.get(position).getValue());
                strStudyName = String.valueOf(listStudy.get(position).getStudyId());
                strStudyTitle = listStudy.get(position).getLabel();*/
                //Toast.makeText(getContext(), parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();

                if(position !=0) {
                    spnSelectedStudyID = String.valueOf(getListStudy.get(position).getValue());
                    strStudyName = String.valueOf(getListStudy.get(position).getStudyId());
                    strStudyTitle = getListStudy.get(position).getLabel();
                    //Toast.makeText(context, spnSelectedStudyID, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(context, "strStudyTitle :"+strStudyTitle, Toast.LENGTH_SHORT).show();

                }else {
                    spnSelectedStudyID = String.valueOf(getListStudy.get(position).getValue());
                    strStudyName = String.valueOf(getListStudy.get(position).getStudyId());
                    strStudyTitle = getListStudy.get(position).getLabel();

                    //Toast.makeText(context, "strStudyTitle 0"+strStudyTitle, Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKitId;
        TextView txtStudyName;
        TextView txtVisit;
        TextView txtKitType;
        TextView txt_status;
        TextView txtExtNumber;
        TextView txtAdditionalKit;
        TextView txt_requistion_form;

        TextView txtCategory;
        TextView txtLocal;
        TextView txtCentral;
        TextView txt_aliquot;
        TextView txtScanDate;
        TextView txt_exp_date;

        Button btnModify, btnDismiss, btnMap;


        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            txtKitId = (TextView) itemView.findViewById(R.id.txtKitId);
            txtStudyName = (TextView) itemView.findViewById(R.id.txtStudyName);
            txtVisit = (TextView) itemView.findViewById(R.id.txtVisit);
            txtKitType = itemView.findViewById(R.id.txtKitType);
            txtAdditionalKit = (TextView) itemView.findViewById(R.id.txtAdditionalKit);
            txt_requistion_form = itemView.findViewById(R.id.txt_requistion_form);
            txt_status = itemView.findViewById(R.id.txt_status);
            txtExtNumber = itemView.findViewById(R.id.txtExtNumber);

            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtLocal = itemView.findViewById(R.id.txtLocal);
            txtCentral = itemView.findViewById(R.id.txtCentral);
            txt_aliquot = itemView.findViewById(R.id.txt_aliquot);
            txtScanDate = itemView.findViewById(R.id.txtScanDate);
            txt_exp_date = itemView.findViewById(R.id.txt_exp_date);

            btnModify = itemView.findViewById(R.id.btnModify);
            btnDismiss = itemView.findViewById(R.id.btnDismiss);
            btnMap = itemView.findViewById(R.id.btnMap);
        }
    }


    //MAP API CALL
    private void callKitMapAPI(int ID, int isTrial) {

        MapKitRequestModel mapKitRequestModel = new MapKitRequestModel();
        mapKitRequestModel.setAppName(AppConstants.APP_NAME);
        mapKitRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        mapKitRequestModel.setDeviceType(AppConstants.APP_OS);
        mapKitRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        mapKitRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        mapKitRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        mapKitRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        mapKitRequestModel.setEvent(AppConstants.MAP_KIT);
        mapKitRequestModel.setUserName(new PrefManager(context).getUserName());
        mapKitRequestModel.setIsTrial(isTrial);
        mapKitRequestModel.setId(Integer.valueOf(ID));

        new NetworkingHelper(new MapKitDetailsRequest(context, true,
                mapKitRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("mapKit API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("mapKit API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                                callGetKitDetailsAPI();


                            }else {

                                Logger.logError("mapKit API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("mapKit API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("mapKit API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("mapKit API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("mapKit Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("mapKit API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }




    private void showDeleteDialog(final KitList kitList) {

        final EditText edtReason;

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_resaon_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        edtReason = dialog.findViewById(R.id.edtReason);


        dialog.show();

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog


                if(edtReason.getText().length() >0) {

                    dialog.dismiss();
                    callKitDismissAPI(kitList.getId(), edtReason.getText().toString().trim());

                }else {
                    Toast.makeText(context,"Please enter REASON" , Toast.LENGTH_SHORT).show();
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


    //DISMISS API CALL
    private void callKitDismissAPI(int ID, String reason) {

        DismissKitRequestModel mapKitRequestModel = new DismissKitRequestModel();
        mapKitRequestModel.setAppName(AppConstants.APP_NAME);
        mapKitRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        mapKitRequestModel.setDeviceType(AppConstants.APP_OS);
        mapKitRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        mapKitRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        mapKitRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        mapKitRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        mapKitRequestModel.setEvent(AppConstants.KIT_REASON);
        mapKitRequestModel.setUserName(new PrefManager(context).getUserName());
        mapKitRequestModel.setReason(reason);
        mapKitRequestModel.setId(Integer.valueOf(ID));
        mapKitRequestModel.setStatus("Dismissed");

        new NetworkingHelper(new DismissKitDetailsRequest(context, true,
                mapKitRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("dismissKit API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("dismissKit API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                                callGetKitDetailsAPI();


                            }else {

                                Logger.logError("dismissKit API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("dismissKit API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("dismissKit API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("dismissKit API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("dismissKit Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("dismissKit API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

    private void showReturnDialog() {

        final ImageButton txt_dob;
        final TextView txtStartDate, txtDob;
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];
        final EditText edtScreenId;
        final Spinner spnResaon;

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_kit_return_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        spnResaon = (Spinner) dialog.findViewById(R.id.spnResaon);
        spnResaon.setOnItemSelectedListener(this);

        ArrayAdapter adpNumber = new ArrayAdapter(context,android.R.layout.simple_spinner_item, spnReason);
        adpNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnResaon.setAdapter(adpNumber);

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

    //Call callGetKitDetailsAPI API
    private void callGetKitDetailsAPI() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        commonRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_KIT_DETAILS);
        commonRequestModel.setUserName(new PrefManager(context).getUserName());

        new NetworkingHelper(new GetKitDetailsRequest((Activity) context, true,
                commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetKitDetailsResponse getKitDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetKitDetailsResponse.class);

                        if (getKitDetailsResponse.getStatus().getCODE() == 200) {

                            if(getKitDetailsResponse.getKitList().size() > 0){

                               // linearLayout.setVisibility(View.VISIBLE);
                                //textView.setVisibility(View.GONE);

                                Logger.logError("getKitList API success status " +
                                        getKitDetailsResponse.getStatus());
                                Logger.logError("getKitList API success getSubjectList" +
                                        getKitDetailsResponse.getKitList());

                               // getAllStudyID();

                                KitDetailsAdapter customAdapter = new KitDetailsAdapter(context, getKitDetailsResponse.getKitList(), getListStudy, recyclerView);
                                recyclerView.setAdapter(customAdapter);


                            }else {

                                Logger.logError("getKitList API Failure " +
                                        "getSubjectList" +
                                        getKitDetailsResponse.getKitList());

                                Utils.showAlertDialog((Activity) context,  "NO DATA IN STUDY");
                            }

                        }else {

                            Logger.logError("getKitList API Failure " +
                                    getKitDetailsResponse.getStatus().getCODE());
                            Logger.logError("getKitList API Failure " +
                                    getKitDetailsResponse.getStatus().getMSG());

                            Utils.showAlertDialog((Activity) context,  getKitDetailsResponse.getStatus()
                                    .getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("getKitList Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("getKitList API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

    @Override
    public void onClick(View v) {

        String str="";
        switch (v.getId()){
           /* case R.id.btnGenerateBarcode:
                //Toast.makeText(getContext(),"Button Generate Pressed" , Toast.LENGTH_SHORT).show();
                generateBarcode();
                break;*/

         /*   case R.id.btnSubmit:
                //submitDetails();
                break;*/

          /*  case R.id.btn_replicate :

                if(editTextKIT_ID.getText().toString().length()>0) {
                    //Your dialog
                    showReplicateDialog();
                }else {
                    Toast.makeText(context, "Enter KIT ID " , Toast.LENGTH_SHORT).show();
                }
                break;*/

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


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

    private void setStartDate() {

        final Calendar c = new GregorianCalendar();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

    private void generateBarcode() {

        message = editTextKIT_ID.getText().toString();

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
            Toast.makeText(context,"Please enter KIT ID" , Toast.LENGTH_SHORT).show();
        }
    }

    private void showModifyDialog(final KitList kitList, List<StudyList> lists) {

        String[] sNumber = {"0", "1","2","3", "4", "5", "6", "7", "8", "9", "10"};
        listSpinnerStudyID = new ArrayList<>();
        listSpinnerStudyID.add(kitList.getStudyId());

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_kit_modify);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        spnStudyIDs = (Spinner) dialog.findViewById(R.id.spnStatusId);
        spnStudyIDs.setOnItemSelectedListener(this);

        spnLocal = (Spinner) dialog.findViewById(R.id.spnLocal);
        spnLocal.setOnItemSelectedListener(this);

        spnCentral = (Spinner) dialog.findViewById(R.id.spnCentral);
        spnCentral.setOnItemSelectedListener(this);

        spnAliquot = (Spinner) dialog.findViewById(R.id.spnAliquot);
        spnAliquot.setOnItemSelectedListener(this);

        btnStartDatePicker=(ImageButton)dialog.findViewById(R.id.btn_start_date);
        txtStartDate=(TextView)dialog.findViewById(R.id.txt_start_date);
        txtStartDate.setText(Utilities.splitDateFromDesired(kitList.getScanDate()));

        btnEndDatePicker=(ImageButton)dialog.findViewById(R.id.btn_end_date);
        txtEndDate=(TextView)dialog.findViewById(R.id.txt_end_date);
        txtEndDate.setText(Utilities.splitDateFromDesired(kitList.getExpiryDate()));

        rbScreening=(RadioButton) dialog.findViewById(R.id.rScreening);
        rbTrial=(RadioButton) dialog.findViewById(R.id.rTrial);
        rbAdYes=(RadioButton) dialog.findViewById(R.id.rbAdYes);
        rbAdNo=(RadioButton) dialog.findViewById(R.id.rbAdNo);

        rbReqFormYES=(RadioButton) dialog.findViewById(R.id.rbReqFormYES);
        rbReqFormNO=(RadioButton) dialog.findViewById(R.id.rbReqFormNO);

        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);

        btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

  //      btnGenerateBarcode = dialog.findViewById(R.id.btnGenerateBarcode);
//        btnGenerateBarcode.setOnClickListener(this);

        editTextKIT_ID = dialog.findViewById(R.id.edtKitId);
        editTextKIT_ID.setText(kitList.getKitId());

        editTextAccessionNumber = dialog.findViewById(R.id.edtAccession);
        editTextAccessionNumber.setText(kitList.getExtNumber());

        editTextVISIT = dialog.findViewById(R.id.edtVisit);
        editTextVISIT.setText(kitList.getVisit());

        rbSample = (RadioButton)dialog.findViewById(R.id.radioSample);
        rbAliquot = (RadioButton)dialog.findViewById(R.id.radioAliquot);
        rbBoth = (RadioButton)dialog.findViewById(R.id.radioBoth);

        llLocal = (LinearLayout) dialog.findViewById(R.id.linearLayout_local);
        llCentral = (LinearLayout) dialog.findViewById(R.id.linearLayout_central);
        llAliquot = (LinearLayout) dialog.findViewById(R.id.linearLayout_alqt);

        imageView = (ImageView) dialog.findViewById(R.id.barcode_image);
        //txt_start_date = dialog.findViewById(R.id.txt_start_date);

        //btnReplicate = dialog.findViewById(R.id.btn_replicate);
        //btnReplicate.setOnClickListener(this);

        radioKITtype =(RadioGroup) dialog.findViewById(R.id.radioGroupKitType);
        radioAdditionalKITtype =(RadioGroup) dialog.findViewById(R.id.rg_additional_kit);
        radioGroupCategory =(RadioGroup) dialog.findViewById(R.id.rg_category);
        radioGroupReqForm =(RadioGroup) dialog.findViewById(R.id.radioGroup_req_form);

        int selectedId=radioKITtype.getCheckedRadioButtonId();
        radioButtonKitTYPE =(RadioButton)dialog.findViewById(selectedId);

        int selectedId1=radioAdditionalKITtype.getCheckedRadioButtonId();
        radioButtonAdditionalKitTYPE =(RadioButton)dialog.findViewById(selectedId1);

        int selectedId2=radioGroupCategory.getCheckedRadioButtonId();
        radioButtonCategory =(RadioButton)dialog.findViewById(selectedId2);

        int selectedId3=radioGroupReqForm.getCheckedRadioButtonId();
        radioButtonReqForm =(RadioButton)dialog.findViewById(selectedId3);

        if(kitList.getIsTrial() ==1){
            rbTrial.setChecked(true);
        }else {
            rbScreening.setChecked(true);
        }

        if(kitList.getAdditionalKit() ==1){
            rbAdYes.setChecked(true);
        }else {
            rbAdNo.setChecked(true);
        }

        if(kitList.getRequisitionForm() ==1){
            rbReqFormYES.setChecked(true);
        }else {
            rbReqFormNO.setChecked(true);
        }

        if(kitList.getCategory().equalsIgnoreCase("SAMPLE")){
            rbSample.setChecked(true);
            llLocal.setVisibility(View.VISIBLE);
            llCentral.setVisibility(View.VISIBLE);
            llAliquot.setVisibility(View.GONE);

        }else if(kitList.getCategory().equalsIgnoreCase("ALIQUOT")){
            rbAliquot.setChecked(true);
            llLocal.setVisibility(View.GONE);
            llCentral.setVisibility(View.GONE);
            llAliquot.setVisibility(View.VISIBLE);

        }else {
            rbBoth.setChecked(true);
            llLocal.setVisibility(View.VISIBLE);
            llCentral.setVisibility(View.VISIBLE);
            llAliquot.setVisibility(View.VISIBLE);
        }

        List<String> listsGroup = new ArrayList<>();
        listsGroup.add(""+kitList.getCentral());
        //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

        for (int i = 0; i < 11; i++) {
            if(String.valueOf(kitList.getCentral()) !=sNumber[i]) {
                listsGroup.add(sNumber[i]);
                // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
            }
        }

        ArrayAdapter adpNumber = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup);
        adpNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCentral.setAdapter(adpNumber);

        List<String> listsGroup2 = new ArrayList<>();
        listsGroup2.add(""+kitList.getLocal());
        //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

        for (int i = 0; i < 11; i++) {
            if(String.valueOf(kitList.getLocal()) !=sNumber[i]) {
                listsGroup2.add(sNumber[i]);
                // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
            }
        }

        ArrayAdapter adpNumberLocal = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup2);
        adpNumberLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLocal.setAdapter(adpNumberLocal);

        List<String> listsGroup3 = new ArrayList<>();
        listsGroup3.add(""+kitList.getAliquot());
        //System.out.println("listsGroup (1) :" + String.valueOf(subjectList.getStudyName()));

        for (int i = 0; i < 11; i++) {
            if(String.valueOf(kitList.getAliquot()) !=sNumber[i]) {
                listsGroup3.add(sNumber[i]);
                // System.out.println("spnGroupName[i] Group :" + spnGroupName[i]);
            }
        }


        ArrayAdapter adpNumberAliquot = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listsGroup3);
        adpNumberAliquot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAliquot.setAdapter(adpNumberAliquot);

        List<String> listSetLabel = new ArrayList<>();

        for (int i = 0; i < getListStudy.size(); i++) {

            listSetLabel.add(""+getListStudy.get(i).getLabel());
            Logger.log("List of Label :"+getListStudy.get(i).getLabel());

        }

        ArrayAdapter studyIdAdp = new ArrayAdapter(context,android.R.layout.simple_spinner_item, listSetLabel);
        studyIdAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStudyIDs.setAdapter(studyIdAdp);

        rbSample.setOnClickListener(this);
        rbAliquot.setOnClickListener(this);
        rbBoth.setOnClickListener(this);

        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        // if decline button is clicked, close the custom dialog
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
                String kitType = "";
                if(rbScreening.isChecked()){
                    kitType="SCREENING";
                }else if(rbTrial.isChecked()){
                    kitType= "TRIAL";
                }else {

                }

                String additionalKit = "";
                if(rbAdYes.isChecked()){
                    additionalKit="YES";
                }else if(rbAdNo.isChecked()){
                    additionalKit= "NO";
                }else {

                }

                String requistionForm = "";
                if(rbReqFormYES.isChecked()){
                    requistionForm="YES";
                }else if(rbReqFormNO.isChecked()){
                    requistionForm= "NO";
                }else {

                }

                String categoryStatus = "";
                if(rbSample.isChecked()){
                    categoryStatus="SAMPLE";
                }else if(rbAliquot.isChecked()){
                    categoryStatus= "ALIQUOT";
                }else if(rbBoth.isChecked()){
                    categoryStatus= "BOTH";
                }else {

                }

                String sLocal ="", sCentral ="", sAliquot ="";

                if(rbSample.isChecked()){
                    /*rbSample.setChecked(true);
                    llLocal.setVisibility(View.VISIBLE);
                    llCentral.setVisibility(View.VISIBLE);
                    llAliquot.setVisibility(View.GONE);*/

                    sLocal = spnLocal.getSelectedItem().toString();
                    sCentral =spnCentral.getSelectedItem().toString();
                    sAliquot = "0";

                }else if(rbAliquot.isChecked()){
                   /* rbAliquot.setChecked(true);
                    llLocal.setVisibility(View.GONE);
                    llCentral.setVisibility(View.GONE);
                    llAliquot.setVisibility(View.VISIBLE);*/

                    sLocal = "0";
                    sCentral = "0";
                    sAliquot = spnAliquot.getSelectedItem().toString();

                }else {
                    /*rbBoth.setChecked(true);
                    llLocal.setVisibility(View.VISIBLE);
                    llCentral.setVisibility(View.VISIBLE);
                    llAliquot.setVisibility(View.VISIBLE);*/

                    sLocal = spnLocal.getSelectedItem().toString();
                    sCentral =spnCentral.getSelectedItem().toString();
                    sAliquot = spnAliquot.getSelectedItem().toString();
                }

                submitDetails(v, kitList.getId(), kitType, additionalKit, requistionForm, categoryStatus, sLocal, sCentral, sAliquot);
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



        Button btnReturn = (Button) dialog.findViewById(R.id.btnReturn);
        // if decline button is clicked, close the custom dialog
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                showReturnDialog();
            }
        });

        dialog.show();
    }


    private void showReplicateDialog() {

        final TextView et_text;
        final Button btn_submit;
        final Button btnCancel;
        final Spinner sp_qtyc, sp_qtyl;


        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_qtyc.setAdapter(adapter);
        sp_qtyl.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_text.getText().toString().equals("")) {
                    Toast.makeText(context, "Please Enter Text", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(context, ShowReplicateListActivity.class);
                    i.putExtra("qtyc", sp_qtyc.getSelectedItem().toString());
                    i.putExtra("qtyl", sp_qtyl.getSelectedItem().toString());
                    i.putExtra("text", et_text.getText().toString());
                    context.startActivity(i);
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

    private void submitDetails(View v, int id, String kitType, String additionalKit, String requistionForm, String categorty, String sLocal, String sCentral, String sAliquot) {

        if(editTextKIT_ID.length() >0) {

            if(editTextVISIT.length() > 0) {

                if(!txtStartDate.getText().toString().equalsIgnoreCase("")){

                    if(!txtEndDate.getText().toString().equalsIgnoreCase("")){


                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        Date date1 = null;
                        try {
                            date1 = format.parse(txtStartDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        /*radioKITtype =(RadioGroup) v.findViewById(R.id.radioGroupKitType);
                        radioAdditionalKITtype =(RadioGroup) v.findViewById(R.id.rg_additional_kit);
                        radioGroupCategory =(RadioGroup) v.findViewById(R.id.rg_category);
                        radioGroupReqForm =(RadioGroup) v.findViewById(R.id.radioGroup_req_form);

                        int selectedId=radioKITtype.getCheckedRadioButtonId();
                        radioButtonKitTYPE =(RadioButton)v.findViewById(selectedId);

                        int selectedId1=radioAdditionalKITtype.getCheckedRadioButtonId();
                        radioButtonAdditionalKitTYPE =(RadioButton)v.findViewById(selectedId1);

                        int selectedId2=radioGroupCategory.getCheckedRadioButtonId();
                        radioButtonCategory =(RadioButton)v.findViewById(selectedId2);

                        int selectedId3=radioGroupReqForm.getCheckedRadioButtonId();
                        radioButtonReqForm =(RadioButton)v.findViewById(selectedId3);*/


                        CallModifyDetailsAPI(editTextKIT_ID.getText().toString(),
                                editTextAccessionNumber.getText().toString(),
                                editTextVISIT.getText().toString(),
                                kitType,
                                additionalKit,
                                categorty,
                                requistionForm,
                                txtStartDate.getText().toString(),
                                txtEndDate.getText().toString(),
                                sLocal,
                                sCentral,
                                sAliquot,
                                spnSelectedStudyID,
                                strStudyName,
                                strStudyTitle,
                                id);
                        //  }




               /* callAddSubjectOnBoardingAPI(editTextKIT_ID.getText().toString(),
                        txt_start_date.getText().toString(),
                        spnPersonGender.getSelectedItem().toString(),
                        spnGroups.getSelectedItem().toString(),
                        spnStudyIDs.getSelectedItem().toString());*/

                    }else {
                        Toast.makeText(context,"Please Select Expiry Date" , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context,"Please Select Scan Date" , Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(context,"Please enter VISIT" , Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"Please enter KIT ID" , Toast.LENGTH_SHORT).show();
        }

    }

    //Call CallModifyDetailsAPI API
    private void CallModifyDetailsAPI(String kitId, String accessionNumber, String visit,
                                      String kitType, String additionalKit, String category,
                                      String reqForm, String startDate, String endDate,
                                      String localQty, String centralQty, String aliquotQty,
                                      String studyID, String strStudyName, String strStudyTitle, int id) {

        ModifyKitRequestModel modifyKitRequestModel = new ModifyKitRequestModel();
        modifyKitRequestModel.setAppName(AppConstants.APP_NAME);
        modifyKitRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        modifyKitRequestModel.setDeviceType(AppConstants.APP_OS);
        modifyKitRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        modifyKitRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        modifyKitRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        modifyKitRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        modifyKitRequestModel.setEvent(AppConstants.MODIFY_KIT);
        if(kitType.equalsIgnoreCase("TRIAL")) {
            modifyKitRequestModel.setIsTrial(1);
        }else {
            modifyKitRequestModel.setIsTrial(0);
        }
        modifyKitRequestModel.setUserName(new PrefManager(context).getUserName());
        modifyKitRequestModel.setKitId(kitId);
        if(!accessionNumber.isEmpty()) {
            modifyKitRequestModel.setExtNum(accessionNumber);
        }else {
            modifyKitRequestModel.setExtNum(" ");
        }
        modifyKitRequestModel.setVisit(visit);
        if(additionalKit.equalsIgnoreCase("YES")) {
            modifyKitRequestModel.setAdditionalKit(1);
        }else {
            modifyKitRequestModel.setAdditionalKit(0);
        }
        modifyKitRequestModel.setCategory(category);
        modifyKitRequestModel.setStatus(AppConstants.IN_STOCK);
        modifyKitRequestModel.setLocal(Integer.valueOf(localQty));
        modifyKitRequestModel.setCentral(Integer.valueOf(centralQty));
        modifyKitRequestModel.setAliquot(Integer.valueOf(aliquotQty));
        if(reqForm.equalsIgnoreCase("YES")) {
            modifyKitRequestModel.setReqForm(1);
        }else {
            modifyKitRequestModel.setReqForm(0);
        }
        modifyKitRequestModel.setScanDate(startDate);
        modifyKitRequestModel.setExpDate(endDate);
        modifyKitRequestModel.setStudyId(Integer.valueOf(studyID));
        modifyKitRequestModel.setGenBarcode(kitId);
        modifyKitRequestModel.setStudyName(strStudyName);
        modifyKitRequestModel.setStudyTitle(strStudyTitle);
        modifyKitRequestModel.setId(id);

        Logger.log("Integer.valueOf(studyID) "+Integer.valueOf(studyID));
        Logger.log("strStudyName "+strStudyName);
        Logger.log("strStudyTitle "+strStudyTitle);

        new NetworkingHelper(new ModifyKitRequest((Activity) context, true, modifyKitRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        CommonResponse commonResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, CommonResponse.class);

                        if (commonResponse.getStatus().getCODE() == 200) {

                            if(commonResponse.getResponse().get(0).isStatus()){

                                Logger.logError("modifyKIT API success " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifyKIT API success " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                                callGetKitDetailsAPI();
                                /*editTextKIT_ID.setText("");
                                editTextAccessionNumber.setText("");
                                editTextVISIT.setText("");
                                txtStartDate.setText("");
                                txtEndDate.setText("");*/

                            }else {

                                Logger.logError("modifyKIT API Failure " +
                                        commonResponse.getResponse().get(0).isStatus());
                                Logger.logError("modifyKIT API Failure " +
                                        commonResponse.getResponse().get(0).getMessage());

                                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                            }

                        }else {

                            Logger.logError("modifyKIT API Failure " +
                                    commonResponse.getResponse().get(0).isStatus());
                            Logger.logError("modifyKIT API Failure " +
                                    commonResponse.getResponse().get(0).getMessage());

                            Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("modifyKIT Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("modifyKIT API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

    /*//getAllStudyID API
    private void getAllStudyID()
    {
        final CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
        commonRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
        //commonRequestModel.setEvent(AppConstants.GET_NOTIFICATION);
        commonRequestModel.setUserName(new PrefManager(context).getUserName());

        new NetworkingHelper(new GetAllStudyIdRequest((Activity) context, true, commonRequestModel)) {

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



                                    for (int i = 0; i < commonResponse.getStudyList().size(); i++) {

                                        listStudyLabel.add(commonResponse.getStudyList().get(i).getLabel());

                                    }


                                }else {
                                    Logger.logError("No STUDY_LIST FOUND :" + "No STUDY_LIST FOUND");
                                }


                            }else {

                                Utils.showAlertDialog((Activity) context,  commonResponse.getStatus().getMSG());
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

    }*/
}

