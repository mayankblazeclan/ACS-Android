package com.hrfid.acs.view.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.AddTSURequest;
import com.hrfid.acs.helpers.request.AddTSURequestModel;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetKitListForTSURequest;
import com.hrfid.acs.helpers.request.GetTSUDetailsRequest;
import com.hrfid.acs.helpers.request.GetTSUParamRequest;
import com.hrfid.acs.helpers.request.ModifyTSUDetailsRequestModel;
import com.hrfid.acs.helpers.request.ModifyTSURequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetKitListForTSU.GetKitListForTSUResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetKitListForTSU.Kit;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails.GetTSUDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails.TSUList;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.GetTSUParamsResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by MS on 2019-05-31.
 */
public class TSUDetailsAdapter extends RecyclerView.Adapter<TSUDetailsAdapter.MyViewHolder> implements AdapterView.OnItemSelectedListener, View.OnClickListener {

  private int mYear, mMonth, mDay;
  private List<String> listStudy = new ArrayList<String>();
  private String spnSelectedStudyID ="";
  private String strStudyName;
  private String strStudyTitle;
  private String strKitName;
  private String strKitRecId;
  Context context;
  List<TSUList> tsuLists;
  private  List<StudyList> getListStudy;
  private RecyclerView recyclerView;
  private static int currentPosition = 0;
  private String spnSelectedKitID ="";
  private TextView spnStudyLabel;
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
  private RadioButton radioButtonTubeTypeBlood;
  private RadioButton radioButtonTubeTypeUrine;
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
  private RadioButton rBlood, rUrine, rAliquot;

  private LinearLayout linearLayoutTube;
  private LinearLayout linearLayoutAliquot;

  public TSUDetailsAdapter(Context context, List<TSUList> tsuLists, List<StudyList> lists, RecyclerView recyclerView) {
    this.context = context;
    this.tsuLists = tsuLists;
    this.getListStudy = lists;
    this.recyclerView = recyclerView;
    //getAllStudyID();
  }
  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // infalte the item Layout
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tsu_details_row, parent, false);
    // set the view's size, margins, paddings and layout parameters
    MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
    return vh;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    //TextView txtKitId;
    //TextView txtStudyName;
    TextView txtVisit;
    TextView txtSiteNo;
    TextView txtCohortNo;
    TextView txtPrimartInvestigator;
    TextView txtTransport;
    TextView txtDiscardTubeColor;

    TextView txtDiscardTubeVolume;
    TextView txtCollectionLabel;
    TextView txtTestName;
    TextView txtEntryDate;
    TextView txtTubetype;
    TextView txtTubeColor;

    TextView txtTubeVol;
    TextView txtAliquotTubeColor;
    TextView txtAliquotTubeVol;
    TextView txtAliquotTubeExt;
    TextView txtCentrifugeProg;
    TextView txtLabUse;
    RelativeLayout linearLayout;
    TextView textViewHeading;

    Button btnModify, btnDuplicate;


    public MyViewHolder(View itemView) {
      super(itemView);
      // get the reference of item view's
      //txtKitId = (TextView) itemView.findViewById(R.id.txtKitId);
      // txtStudyName = (TextView) itemView.findViewById(R.id.txtStudyName);
      txtVisit = (TextView) itemView.findViewById(R.id.txtVisit);
      txtSiteNo = itemView.findViewById(R.id.txtSiteNo);
      txtTransport = (TextView) itemView.findViewById(R.id.txtTimepoint);
      txtDiscardTubeColor = itemView.findViewById(R.id.txtDiscardTubeColor);
      txtCohortNo = itemView.findViewById(R.id.txtCohortNo);
      txtPrimartInvestigator = itemView.findViewById(R.id.txtPrimartInvestigator);

      txtDiscardTubeVolume = itemView.findViewById(R.id.txtDiscardTubeVolume);
      txtCollectionLabel = itemView.findViewById(R.id.txtCollectionLabel);
      txtTestName = itemView.findViewById(R.id.txtTestName);
      txtEntryDate = itemView.findViewById(R.id.txtEntryDate);
      txtTubetype = itemView.findViewById(R.id.txtTubetype);
      txtTubeColor = itemView.findViewById(R.id.txtTubeColor);

      txtTubeVol = itemView.findViewById(R.id.txtTubeVol);
      txtAliquotTubeColor = itemView.findViewById(R.id.txtAliquotTubeColor);
      txtAliquotTubeVol = itemView.findViewById(R.id.txtAliquotTubeVol);
      txtAliquotTubeExt = itemView.findViewById(R.id.txtAliquotTubeExt);
      txtCentrifugeProg = itemView.findViewById(R.id.txtCentrifugeProg);
      txtLabUse = itemView.findViewById(R.id.txtLabUse);


      btnModify = itemView.findViewById(R.id.btnModify);
      btnDuplicate = itemView.findViewById(R.id.btnDuplicate);
      linearLayout = itemView.findViewById(R.id.relativeLayout);
      textViewHeading = itemView.findViewById(R.id.textViewHeading);
    }
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, final int position) {
    // set the data in items
    holder.linearLayout.setVisibility(View.GONE);
    //holder.txtKitId.setText(tsuLists.get(position).getKitId().trim());
    holder.textViewHeading.setText("LAB KIT : " + tsuLists.get(position).getKitId() + "  TIMEPOINT : " + tsuLists.get(position).getTimepoint()+ "  TEST NAME : " + tsuLists.get(position).getTestName());
    holder.textViewHeading.setSelected(true);
//        holder.txtStudyName.setText("" + tsuLists.get(position).getStudyTitle()+ "("+ tsuLists.get(position).getStudyName()+")");
    holder.txtVisit.setText(tsuLists.get(position).getVisit().trim());
    holder.txtVisit.setSelected(true);

  /*  if (tsuLists.get(position).getTubeType().equalsIgnoreCase("")) {
      holder.txtTubetype.setText("BLOOD");
    } else {
      holder.txtTubetype.setText("URINE");
    }*/

    holder.txtTubetype.setText(tsuLists.get(position).getTubeType().trim());
    //holder.txtCohortNo.setText(tsuLists.get(position).getStatus().trim());

    holder.txtSiteNo.setText("" + tsuLists.get(position).getSiteNo());
    holder.txtCohortNo.setText(tsuLists.get(position).getCohortNo());
    holder.txtPrimartInvestigator.setText(tsuLists.get(position).getPrimaryInvestigator());
    holder.txtTransport.setText(tsuLists.get(position).getTransportLable());
    holder.txtDiscardTubeColor.setText(tsuLists.get(position).getDiscardTubeColor());
    holder.txtDiscardTubeVolume.setText(tsuLists.get(position).getDiscardTubeVolume());
    holder.txtCollectionLabel.setText("" + tsuLists.get(position).getCollectionLable());
    holder.txtTestName.setText("" + tsuLists.get(position).getStudyTitle()+ "(" + tsuLists.get(position).getStudyName() + ")");
    //holder.txtEntryDate.setText(""+ tsuLists.get(position).get());

    holder.txtTubeColor.setText("" + tsuLists.get(position).getTubeColor());

    if(tsuLists.get(position).getTubeVolume()!=null) {
      if (!tsuLists.get(position).getTubeVolume().equalsIgnoreCase("0")) {
        holder.txtTubeVol.setText("" + tsuLists.get(position).getTubeVolume());
      } else {
        holder.txtTubeVol.setText("-");
      }
    }
    holder.txtAliquotTubeColor.setText("" + tsuLists.get(position).getAliquotColorTube());

    if(tsuLists.get(position).getAliquotVolume()!=null) {
      if (!tsuLists.get(position).getAliquotVolume().equalsIgnoreCase("0")) {
        holder.txtAliquotTubeVol.setText("" + tsuLists.get(position).getAliquotVolume());
      } else {
        holder.txtAliquotTubeVol.setText("-");
      }
    }
    holder.txtAliquotTubeExt.setText("" + tsuLists.get(position).getAliquotExtNo());
    holder.txtCentrifugeProg.setText("" + tsuLists.get(position).getCentrifugeProg());
    holder.txtLabUse.setText("" + tsuLists.get(position).getLabUse());
    holder.txtEntryDate.setText
            (Utilities.splitDateFromDesired(tsuLists.get(position).getEntry_date()));


    holder.btnModify.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //showModifyDialog();

        showModifyDialog(tsuLists.get(position), getListStudy);
      }
    });

    holder.btnDuplicate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        // showDismissKitDialog(tsuLists.get(position));
        showDuplicateDialog(tsuLists.get(position), getListStudy);
      }
    });

    if (currentPosition == position) {
      //creating an animation
      Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

      //adding sliding effect
      holder.linearLayout.startAnimation(slideDown);

      //toggling visibility
      holder.linearLayout.setVisibility(View.VISIBLE);
    }


    holder.textViewHeading.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        //getting the position of the item to expand it
        currentPosition = position;

        //reloding the list
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public int getItemCount() {
    return tsuLists.size();
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    //  Toast.makeText(getContext(),spnBloodGroup[position] , Toast.LENGTH_SHORT).show();

    // TODO Auto-generated method stub
    switch(parent.getId()){

    /*  case R.id.spnStatusId :
        //Your Action Here.
        spnSelectedStudyID = String.valueOf(getListStudy.get(position).getValue());
        strStudyName = String.valueOf(getListStudy.get(position).getStudyId());
        strStudyTitle = getListStudy.get(position).getLabel();
        //Toast.makeText(getContext(), parent.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();

        if(spnSelectedStudyID !=null) {
          getKitListParamsForTSU(spnSelectedStudyID, tsuLists.get(position));
        }
        break;*/

      case R.id.spnPrimaryInvestigator :

        break;

      case R.id.spnKitLabel :
        if(kitVisitListFetchedParam!=null) {
          edtVisit.setText(kitVisitListFetchedParam.get(position).toString());
          strKitName = String.valueOf(listKitList.get(position).getKitId());
          strKitRecId = String.valueOf(listKitList.get(position).getId());
        }else {

          edtVisit.setText(" ");
          strKitName = " ";
          strKitRecId = " ";
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

  //Call callGetTSUDetails API
  private void callGetTSUDetailsAPI() {
    CommonRequestModel commonRequestModel = new CommonRequestModel();
    commonRequestModel.setAppName(AppConstants.APP_NAME);
    commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
    commonRequestModel.setDeviceType(AppConstants.APP_OS);
    commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
    commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
    commonRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
    commonRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
    commonRequestModel.setEvent(AppConstants.GET_TSU_DETAILS);
    commonRequestModel.setUserName(new PrefManager(context).getUserName());

    new NetworkingHelper(new GetTSUDetailsRequest((Activity) context, true,
            commonRequestModel)) {

      @Override
      public void serverResponseFromApi(ApiResponse serverResponse) {
        if (serverResponse.isSucess) {

          try {

            GetTSUDetailsResponse getKitDetailsResponse = JsonParser
                    .parseClass(serverResponse.jsonResponse, GetTSUDetailsResponse.class);

            if (getKitDetailsResponse.getStatus().getCODE() == 200) {

              if(getKitDetailsResponse.getTSUList().size() > 0){

                //linearLayout.setVisibility(View.VISIBLE);
                //textView.setVisibility(View.GONE);

                Logger.logError("getTSUList API success status " +
                        getKitDetailsResponse.getStatus());
                Logger.logError("getTSUList API success getSubjectList" +
                        getKitDetailsResponse.getTSUList());

                //getAllStudyID();

                tsuLists.clear();

                for (int i = 0; i < getKitDetailsResponse.getTSUList().size(); i++) {

                  if(getKitDetailsResponse.getTSUList().get(i).getIsArchived() ==0) {
                    tsuLists.add(getKitDetailsResponse.getTSUList().get(i));
                  }
                }

                if(tsuLists !=null && tsuLists.size() > 0) {
                  TSUDetailsAdapter customAdapter = new TSUDetailsAdapter(context, tsuLists, getListStudy, recyclerView);
                  recyclerView.setAdapter(customAdapter);
                }


              }else {

                Logger.logError("getTSUList API Failure " +
                        "getSubjectList" +
                        getKitDetailsResponse.getTSUList());

                //linearLayout.setVisibility(View.GONE);
                //textView.setVisibility(View.VISIBLE);

                Utils.showAlertDialog((Activity) context,  "NO DATA IN STUDY");
              }

            }else {

                           /* Logger.logError("getTSUList API Failure " +
                                    getKitDetailsResponse.getStatus().getCODE());
                            Logger.logError("getTSUList API Failure " +
                                    getKitDetailsResponse.getStatus().getMSG());*/

              Utils.showAlertDialog((Activity) context,  getKitDetailsResponse.getStatus()
                      .getERROR());
            }



          }
          catch (Exception e){
            Logger.logError("getTSUList Exception " + e.getMessage());
          }

        } else {
          Logger.logError("getTSUList API Failure " +
                  serverResponse.errorMessageToDisplay);
        }
      }
    };

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){

      case R.id.btnEntryDate:
        setEntryDate();
        break;

      case R.id.rBlood :
        boolean checked = ((RadioButton) v).isChecked();
        if(checked)
          linearLayoutTube.setVisibility(View.VISIBLE);
        linearLayoutAliquot.setVisibility(View.GONE);
        break;

      case R.id.rUrine :
        linearLayoutTube.setVisibility(View.VISIBLE);
        linearLayoutAliquot.setVisibility(View.GONE);
        break;

      case R.id.radioAliquot :
        linearLayoutTube.setVisibility(View.GONE);
        linearLayoutAliquot.setVisibility(View.VISIBLE);
        break;

    }
  }

  private void showDuplicateDialog(final TSUList tsuList, List<StudyList> lists) {

    // Create custom dialog object
    final Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.dialog_tsu_duplicate);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    Window window = dialog.getWindow();
    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    initViews(dialog, tsuList, lists);

    getTSUParamList(tsuList);

    Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Close dialog
        submitDuplicateDetails(tsuList.getId(), dialog);
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

    dialog.show();
  }

  private void showModifyDialog(final TSUList tsuList, List<StudyList> lists) {

    // Create custom dialog object
    final Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.dialog_tsu_modify);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    Window window = dialog.getWindow();
    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);



    initViews(dialog, tsuList, lists);

    getTSUParamList(tsuList);



    Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Close dialog


        submitDetails(tsuList.getId(), dialog);
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

    dialog.show();
  }


  private void initViews(Dialog v, TSUList tsuList, List<StudyList> lists) {

    String actualStudyValue = tsuList.getStudyTitle()+ " ("+ tsuList.getStudyName()+")";

    spnStudyLabel = (TextView) v.findViewById(R.id.spnStatusId);
    spnStudyLabel.setText(actualStudyValue);

    //spnStudyLabel.setOnItemSelectedListener(this);

    /*for (int i = 0; i < getListStudy.size(); i++) {

      listStudy.add(getListStudy.get(i).getLabel());

    }

    Utilities.SetSpinnerSelection(spnStudyLabel, listStudy, actualStudyValue);*/
    //spnStudyLabel.setText(tsuList.getStudyTitle()+ "("+ tsuList.getStudyName()+")");

    //getKitListParamsForTSU(String.valueOf(tsuList.getStudyId()), tsuList);

    getKitListParamsForTSU(String.valueOf(tsuList.getStudyId()), tsuList);

    spnKitLabel = (Spinner) v.findViewById(R.id.spnKitLabel);
    spnKitLabel.setOnItemSelectedListener(this);

    edtVisit = v.findViewById(R.id.edtVisit);
    edtVisit.setText(tsuList.getVisit());

    edtSiteNo = v.findViewById(R.id.edtSiteNo);
    edtSiteNo.setText(tsuList.getSiteNo());

    edtCohortNo = v.findViewById(R.id.edtCohortNo);
    edtCohortNo.setText(tsuList.getCohortNo());

    edtTimepoint = v.findViewById(R.id.edtTimepoint);
    edtTimepoint.setText(tsuList.getTimepoint());

    edtTubeVolume = v.findViewById(R.id.edtTubeVolume);
    edtTubeVolume.setText(tsuList.getTubeVolume());

    edtAliquotTubeVolume = v.findViewById(R.id.edtAliquotTubeVolume);
    edtAliquotTubeVolume.setText(tsuList.getAliquotVolume());

    edtAliquotExtNo = v.findViewById(R.id.edtAliquotTubeExt);

    if(!tsuList.getAliquotExtNo().equalsIgnoreCase("-")) {
      edtAliquotExtNo.setText(tsuList.getAliquotExtNo());
    }else {
      edtAliquotExtNo.setText("");
    }

    edtDiscardTubeVolume = v.findViewById(R.id.edtDiscardTubeVol);
    edtDiscardTubeVolume.setText(tsuList.getDiscardTubeVolume());

    edtCentrifugeProg = v.findViewById(R.id.edtCentrifugeProgramme);
    //edtCentrifugeProg.setText(tsuList.getCentrifugeProg());

    if(!tsuList.getCentrifugeProg().equalsIgnoreCase("-")) {
      edtCentrifugeProg.setText(tsuList.getCentrifugeProg());
    }else {
      edtCentrifugeProg.setText("");
    }

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
    radioButtonTubeTypeBlood=(RadioButton) v.findViewById(R.id.rBlood);
    radioButtonTubeTypeUrine=(RadioButton) v.findViewById(R.id.rUrine);


    txtEntryDate = v.findViewById(R.id.txt_entry_date);
    txtEntryDate.setText(tsuList.getEntry_date());

    btnSubmit = v.findViewById(R.id.btnSubmit);
    //btnSubmit.setOnClickListener(this);

    btnEntryDate=  v.findViewById(R.id.btnEntryDate);
    btnEntryDate.setOnClickListener(this);

    linearLayoutTube=  v.findViewById(R.id.ll_tube);
    linearLayoutAliquot=  v.findViewById(R.id.linearLayout_aliquot);

    rUrine=  v.findViewById(R.id.rUrine);
    rUrine.setOnClickListener(this);

    rBlood=  v.findViewById(R.id.rBlood);
    rBlood.setOnClickListener(this);

    rAliquot = v.findViewById(R.id.radioAliquot);
    rAliquot.setOnClickListener(this);

    if(tsuList.getTubeType().equalsIgnoreCase("BLOOD")){
      radioButtonTubeTypeBlood.setChecked(true);
    }else if (tsuList.getTubeType().equalsIgnoreCase("URINE")){
      radioButtonTubeTypeUrine.setChecked(true);
    }else {
      rAliquot.setChecked(true);
    }

    if(!rAliquot.isChecked()){
    linearLayoutTube.setVisibility(View.VISIBLE);
    linearLayoutAliquot.setVisibility(View.GONE);
    }else {
      linearLayoutTube.setVisibility(View.GONE);
      linearLayoutAliquot.setVisibility(View.VISIBLE);
    }

    spnSelectedStudyID = String.valueOf(tsuList.getStudyId());
    strStudyName = String.valueOf(tsuList.getStudyName());

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, sNumberValue);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKitLabel.setAdapter(adapter);
        spnPrimaryInvestigator.setAdapter(adapter);

        spnTubeColor.setAdapter(adapter);
        spnAliquotTubeColor.setAdapter(adapter);

        spnDiscardTubeColor.setAdapter(adapter);
        spnCollectionLabel.setAdapter(adapter);
        spnTransportLabel.setAdapter(adapter);
        spnTestName.setAdapter(adapter);
        spnLabUse.setAdapter(adapter);*/
  }

  private void setEntryDate() {

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



  private void submitDetails(int id, Dialog dialog) {

    if(!rAliquot.isChecked()) {

      if (edtSiteNo.getText().toString().trim().length() > 0) {

        if (edtCohortNo.getText().toString().trim().length() > 0) {

          if (edtTimepoint.getText().toString().trim().length() > 0) {

            if (edtTubeVolume.getText().toString().trim().length() > 0) {

              if(edtDiscardTubeVolume.getText().toString().trim().length() > 0) {

              if (!txtEntryDate.getText().toString().equalsIgnoreCase("")) {


                // int selectedId = radioTubeType.getCheckedRadioButtonId();
                // radioButtonTubeType = (RadioButton) getView().findViewById(selectedId);


                String tubeType = "";
                if (radioButtonTubeTypeBlood.isChecked()) {
                  tubeType = "BLOOD";
                } else if (radioButtonTubeTypeUrine.isChecked()) {
                  tubeType = "URINE";
                } else {
                  tubeType = "ALIQUOT";
                }

                dialog.dismiss();
                callModifyTSUapi(spnSelectedStudyID,
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
                        tubeType,
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
                        txtEntryDate.getText().toString().trim(),
                        id);

              } else {
                Toast.makeText(context, "Please select Entry Date", Toast.LENGTH_SHORT).show();
              }

            } else {
              Toast.makeText(context, "Please enter Discard Tube Volume", Toast.LENGTH_SHORT).show();
            }

            } else {
              Toast.makeText(context, "Please enter Tube Volume", Toast.LENGTH_SHORT).show();
            }

          } else {
            Toast.makeText(context, "Please enter Timpepoint", Toast.LENGTH_SHORT).show();
          }

        } else {
          Toast.makeText(context, "Please enter Cohort Number", Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(context, "Please enter Site Number", Toast.LENGTH_SHORT).show();
      }
    }else {

      if (edtSiteNo.getText().toString().trim().length() > 0) {

        if (edtCohortNo.getText().toString().trim().length() > 0) {

          if (edtTimepoint.getText().toString().trim().length() > 0) {

            if (edtAliquotTubeVolume.getText().toString().trim().length() > 0) {

              if(edtDiscardTubeVolume.getText().toString().trim().length() > 0) {

              if (!txtEntryDate.getText().toString().equalsIgnoreCase("")) {


                // int selectedId = radioTubeType.getCheckedRadioButtonId();
                // radioButtonTubeType = (RadioButton) getView().findViewById(selectedId);


                String tubeType = "";
                if (radioButtonTubeTypeBlood.isChecked()) {
                  tubeType = "BLOOD";
                } else if (radioButtonTubeTypeUrine.isChecked()) {
                  tubeType = "URINE";
                }else if(rAliquot.isChecked()){
                  tubeType = "ALIQUOT";
                } else{

                }

                dialog.dismiss();
                callModifyTSUapi(spnSelectedStudyID,
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
                        tubeType,
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
                        txtEntryDate.getText().toString().trim(),
                        id);

              } else {
                Toast.makeText(context, "Please select Entry Date", Toast.LENGTH_SHORT).show();
              }

              } else {
                Toast.makeText(context, "Please enter Discard Tube Volume", Toast.LENGTH_SHORT).show();
              }

            } else {
              Toast.makeText(context, "Please enter Tube Volume", Toast.LENGTH_SHORT).show();
            }

          } else {
            Toast.makeText(context, "Please enter Timepoint", Toast.LENGTH_SHORT).show();
          }

        } else {
          Toast.makeText(context, "Please enter Cohort Number", Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(context, "Please enter Site Number", Toast.LENGTH_SHORT).show();
      }

    }

  }


  //Call callAddTSUapi API
  private void callModifyTSUapi(String spnSelectedStudyID, String studyName, String studyTitle,
                                String spnSelectedKitID, String strKitName, String strKitTitle,
                                String visit, String siteNo, String cohortNo,
                                String prim_investegator, String strTimePoint, String rbTypeValue,
                                String tubeColor, String tubeVolume, String aliquotColor, String aliquotVol,
                                String aliquotExtNo, String spnDiscardTubeColor, String discardTubeVol,
                                String spnTestName, String spnCollectionLabel, String spnTransportLabel,
                                String centriProg, String strLabUse, String txtEntryDate, int id) {


    ModifyTSUDetailsRequestModel tsuRequestModel = new ModifyTSUDetailsRequestModel();
    tsuRequestModel.setAppName(AppConstants.APP_NAME);
    tsuRequestModel.setVersionNumber(AppConstants.APP_VERSION);
    tsuRequestModel.setDeviceType(AppConstants.APP_OS);
    tsuRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
    tsuRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
    tsuRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
    tsuRequestModel.setUserName(new PrefManager(context).getUserName());
    tsuRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
    tsuRequestModel.setEvent(AppConstants.MODIFY_TSU);

    tsuRequestModel.setVisit(visit);
    // tsuRequestModel.setStudyName(strStudyName);
    tsuRequestModel.setStudyName(strStudyName);
    tsuRequestModel.setKitId(strKitName);
    // tsuRequestModel.setStudyId(Integer.valueOf(spnSelectedStudyID));
    tsuRequestModel.setStudyId(Integer.valueOf(spnSelectedStudyID));
    tsuRequestModel.setKitRecId(Integer.valueOf(strKitRecId));
    /*if(rbTypeValue.equalsIgnoreCase("Blood")) {
      tsuRequestModel.setTubeType(1);
    }else {
      tsuRequestModel.setTubeType(0);
    }*/


    tsuRequestModel.setTubeType(rbTypeValue);
    if(rbTypeValue.equalsIgnoreCase("BLOOD")){

      tsuRequestModel.setTubeColor(tubeColor);
      tsuRequestModel.setTubeVol(tubeVolume);
      tsuRequestModel.setAliquotColor("-");
      tsuRequestModel.setAliquotVol("0");
      tsuRequestModel.setAliquotExt("-");

    }else if(rbTypeValue.equalsIgnoreCase("URINE")) {

      tsuRequestModel.setTubeColor(tubeColor);
      tsuRequestModel.setTubeVol(tubeVolume);
      tsuRequestModel.setAliquotColor("-");
      tsuRequestModel.setAliquotVol("0");
      tsuRequestModel.setAliquotExt("-");

    }else  if(rbTypeValue.equalsIgnoreCase("ALIQUOT")) {

      tsuRequestModel.setTubeColor("-");
      tsuRequestModel.setTubeVol("0");

      tsuRequestModel.setAliquotColor(aliquotColor);
      tsuRequestModel.setAliquotVol(aliquotVol);
     // tsuRequestModel.setAliquotExt(aliquotExtNo);

      if(!aliquotExtNo.isEmpty()){
        tsuRequestModel.setAliquotExt(aliquotExtNo);
      }else {
        tsuRequestModel.setAliquotExt("-");
      }
    }else{

    }
    tsuRequestModel.setIsDuplicate(0);
    tsuRequestModel.setEntryDate(txtEntryDate);
    tsuRequestModel.setSiteNo(siteNo);
    tsuRequestModel.setCohortNo(cohortNo);
    tsuRequestModel.setPi(prim_investegator);
    tsuRequestModel.setTimepoint(strTimePoint);
    /*tsuRequestModel.setTubeColor(tubeColor);
    tsuRequestModel.setTubeVol(tubeVolume);
    tsuRequestModel.setAliquotColor(aliquotColor);
    tsuRequestModel.setAliquotVol(aliquotVol);
    tsuRequestModel.setAliquotExt(aliquotExtNo);*/
    tsuRequestModel.setDiscardTubeColor(spnDiscardTubeColor);
    tsuRequestModel.setDiscardTubeVolume(discardTubeVol);
    tsuRequestModel.setTestName(spnTestName);
    tsuRequestModel.setCollectionLable(spnCollectionLabel);
    tsuRequestModel.setTransportLable(spnTransportLabel);
    //tsuRequestModel.setCentrifugeProg(centriProg);

    if(!centriProg.isEmpty()){
      tsuRequestModel.setCentrifugeProg(centriProg);
    }else {
      tsuRequestModel.setCentrifugeProg("-");
    }
    tsuRequestModel.setLabUse(strLabUse);
    tsuRequestModel.setId(id);

    new NetworkingHelper(new ModifyTSURequest((Activity) context, true, tsuRequestModel)) {

      @Override
      public void serverResponseFromApi(ApiResponse serverResponse) {
        if (serverResponse.isSucess) {

          try {

            CommonResponse commonResponse = JsonParser
                    .parseClass(serverResponse.jsonResponse, CommonResponse.class);

            if (commonResponse.getStatus().getCODE() == 200) {

              if(commonResponse.getResponse().get(0).isStatus()){

                Logger.logError("modifyTSU API success " +
                        commonResponse.getResponse().get(0).isStatus());
                Logger.logError("modifyTSU API success " +
                        commonResponse.getResponse().get(0).getMessage());

                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                callGetTSUDetailsAPI();

                // Utils.showAlertDialog(context,  commonResponse.getResponse().get(0).getMessage());

                              /*  AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                // ...Irrelevant code for customizing the buttons and title
                                LayoutInflater inflater = ((Activity) context).getParent().getLayoutInflater();
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
                                        Intent mNextActivity = new Intent(context, TSUSetupActivity.class);
                                        context.startActivity(mNextActivity);
                                        //context.finish();
                                    }
                                });

                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.show();
                                edtSiteNo.setText("");
                                edtCohortNo.setText("");
                                edtTimepoint.setText("");
                                edtTubeVolume.setText("");*/
                //txtEntryDate.setText("");

              }else {

                Logger.logError("modifyTSU API Failure " +
                        commonResponse.getResponse().get(0).isStatus());
                Logger.logError("modifyTSU API Failure " +
                        commonResponse.getResponse().get(0).getMessage());

                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
              }

            }else {

              Utils.showAlertDialog((Activity)context,  commonResponse.getStatus().geteRROR());
            }



          }
          catch (Exception e){
            Logger.logError("modifyTSU Exception " + e.getMessage());
          }

        } else {
          Logger.logError("modifyTSU API Failure " +
                  serverResponse.errorMessageToDisplay);
        }
      }
    };

  }


  private void getKitListParamsForTSU(String studyId, final TSUList tsuList){

    new NetworkingHelper(new GetKitListForTSURequest((Activity)context, true, studyId)) {

      @Override
      public void serverResponseFromApi(ApiResponse serverResponse) {
        if (serverResponse.isSucess) {

          try {

            GetKitListForTSUResponse getTSUParamsResponse = JsonParser
                    .parseClass(serverResponse.jsonResponse, GetKitListForTSUResponse.class);

            if (getTSUParamsResponse.getStatus().getCODE() == 200) {

              if(getTSUParamsResponse.getResponse().getKits().size() > 0){

                Logger.logError("GetKitListForTSUResponse  API success " +
                        getTSUParamsResponse.getResponse().getKits().size());
                Logger.logError("GetKitListForTSUResponse  API success " +
                        getTSUParamsResponse.getResponse().getKits());

                kitListFetchedParam = new ArrayList();
                kitVisitListFetchedParam = new ArrayList();
                listKitList = new ArrayList();
                listKitList = getTSUParamsResponse.getResponse().getKits();

                for (int i = 0; i < getTSUParamsResponse.getResponse().getKits().size(); i++) {

                  kitListFetchedParam.add(getTSUParamsResponse.getResponse().getKits().get(i).getKitId());
                  kitVisitListFetchedParam.add(getTSUParamsResponse.getResponse().getKits().get(i).getVisit());

                }

                if(kitVisitListFetchedParam !=null) {
                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, kitListFetchedParam);
                  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnKitLabel.setAdapter(adapter);
                  Utilities.SetSpinnerSelection(spnKitLabel, kitListFetchedParam, tsuList.getKitId());
                }

              }else {
                kitListFetchedParam.clear();
                kitVisitListFetchedParam.clear();

                if(kitVisitListFetchedParam !=null) {
                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, kitListFetchedParam);
                  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnKitLabel.setAdapter(adapter);

                  edtVisit.setText(" ");
                  strKitName = " ";
                  strKitRecId = " ";
                }

                //Utils.showAlertDialog((Activity)context,  getTSUParamsResponse.getStatus().getMSG());
              }

            }else {

              Utils.showAlertDialog((Activity)context,  getTSUParamsResponse.getStatus().getMSG());
            }



          }
          catch (Exception e){
            Logger.logError("Exception " + e.getMessage());
          }

        } else {
          Logger.logError("GetKitListForTSUResponse API Failure " +
                  serverResponse.errorMessageToDisplay);
        }
      }
    };
  }



  private void getTSUParamList(final TSUList tsuList){

    new NetworkingHelper(new GetTSUParamRequest((Activity)context, true)) {

      @Override
      public void serverResponseFromApi(ApiResponse serverResponse) {
        if (serverResponse.isSucess) {

          try {

            GetTSUParamsResponse getTSUParamsResponse = JsonParser
                    .parseClass(serverResponse.jsonResponse, GetTSUParamsResponse.class);

            if (getTSUParamsResponse.getStatus().getCODE() == 200) {

              if(getTSUParamsResponse.getResponse().getPI().size() > 0){

                Logger.logError("GetTSU PARAM API success " +
                        getTSUParamsResponse.getResponse().getPI().size());
                Logger.logError("GetTSU PARAM API success " +
                        getTSUParamsResponse.getResponse().getPI());

                listPrimaryInvestigator = new ArrayList();
                listTubeColor = new ArrayList();
                listDiscardTubeColor = new ArrayList();
                listAliquotTubeColor = new ArrayList();

                listTestName = new ArrayList();
                listCollectionTube = new ArrayList();
                listTransportTube = new ArrayList();
                listLabUse = new ArrayList();
                //listKitList = new ArrayList();


                //For Primary Investigator
                //========================
                for (int i = 0; i < getTSUParamsResponse.getResponse().getPI().size(); i++) {

                  listPrimaryInvestigator.add(getTSUParamsResponse.getResponse().getPI().get(i).getValue());
                }

                if(listPrimaryInvestigator!=null) {
                  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listPrimaryInvestigator);
                  adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnPrimaryInvestigator.setAdapter(adapter1);
                  Utilities.SetSpinnerSelection(spnPrimaryInvestigator, listPrimaryInvestigator, tsuList.getPrimaryInvestigator());

                }

                //For TubeColor
                //========================
                //listTubeColor.add("--");
                for (int i = 0; i < getTSUParamsResponse.getResponse().getTubeColor().size(); i++) {

                  listTubeColor.add(getTSUParamsResponse.getResponse().getTubeColor().get(i).getValue());
                }

                if(listTubeColor!=null) {
                  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listTubeColor);
                  adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnTubeColor.setAdapter(adapter2);
                  Utilities.SetSpinnerSelection(spnTubeColor, listTubeColor, tsuList.getTubeColor());
                }


                //For DiscardTubeColor
                //========================
                for (int i = 0; i < getTSUParamsResponse.getResponse().getDiscardTubeColor().size(); i++) {
                  listDiscardTubeColor.add(getTSUParamsResponse.getResponse().getDiscardTubeColor().get(i).getValue());
                }

                if(listDiscardTubeColor!=null) {
                  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listDiscardTubeColor);
                  adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnDiscardTubeColor.setAdapter(adapter1);
                  Utilities.SetSpinnerSelection(spnDiscardTubeColor, listDiscardTubeColor, tsuList.getDiscardTubeColor());
                }

                //For AliquotTubeColor
                //========================
                //listAliquotTubeColor.add("--");
                for (int i = 0; i < getTSUParamsResponse.getResponse().getAliquotTubeColor().size(); i++) {
                  listAliquotTubeColor.add(getTSUParamsResponse.getResponse().getAliquotTubeColor().get(i).getValue());
                }

                if(listAliquotTubeColor!=null) {
                  ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listAliquotTubeColor);
                  adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnAliquotTubeColor.setAdapter(adapter3);
                  Utilities.SetSpinnerSelection(spnAliquotTubeColor, listAliquotTubeColor, tsuList.getAliquotColorTube());

                }


                //For TestName
                //========================
                for (int i = 0; i < getTSUParamsResponse.getResponse().getTestName().size(); i++) {

                  listTestName.add(getTSUParamsResponse.getResponse().getTestName().get(i).getValue());
                }

                if(listTestName!=null) {
                  ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listTestName);
                  adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnTestName.setAdapter(adapter4);
                  Utilities.SetSpinnerSelection(spnTestName, listTestName, tsuList.getTestName());

                }

                //For CollectionLable
                //========================
                listCollectionTube.add("NA");
                for (int i = 0; i < getTSUParamsResponse.getResponse().getCollectionLable().size(); i++) {

                  listCollectionTube.add(getTSUParamsResponse.getResponse().getCollectionLable().get(i).getValue());
                }

                if(listCollectionTube!=null) {
                  ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listCollectionTube);
                  adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnCollectionLabel.setAdapter(adapter5);
                  Utilities.SetSpinnerSelection(spnCollectionLabel, listCollectionTube, tsuList.getCollectionLable());

                }


                //For TransportLable
                //========================
                listTransportTube.add("NA");
                for (int i = 0; i < getTSUParamsResponse.getResponse().getTransportLable().size(); i++) {

                  listTransportTube.add(getTSUParamsResponse.getResponse().getTransportLable().get(i).getValue());
                }

                if(listTransportTube!=null) {
                  ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listTransportTube);
                  adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnTransportLabel.setAdapter(adapter6);
                  Utilities.SetSpinnerSelection(spnTransportLabel, listTransportTube, tsuList.getTransportLable());
                }


                //For LAB USE
                //========================
                for (int i = 0; i < getTSUParamsResponse.getResponse().getLabUse().size(); i++) {

                  listLabUse.add(getTSUParamsResponse.getResponse().getLabUse().get(i).getValue());
                }

                if(listLabUse!=null) {
                  ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(context,
                          android.R.layout.simple_spinner_item, listLabUse);
                  adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnLabUse.setAdapter(adapter7);
                  Utilities.SetSpinnerSelection(spnLabUse, listLabUse, tsuList.getLabUse());

                }

              }else {
                Utils.showAlertDialog((Activity)context,  getTSUParamsResponse.getStatus().getMSG());
              }

            }else {

              Utils.showAlertDialog((Activity)context,  getTSUParamsResponse.getStatus().getMSG());
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


  private void submitDuplicateDetails(int id, Dialog dialog) {

    if(!rAliquot.isChecked()) {

      if (edtSiteNo.getText().toString().trim().length() > 0) {

        if (edtCohortNo.getText().toString().trim().length() > 0) {

          if (edtTimepoint.getText().toString().trim().length() > 0) {

            if (edtTubeVolume.getText().toString().trim().length() > 0) {

              if(edtDiscardTubeVolume.getText().toString().trim().length() > 0) {

                if (!txtEntryDate.getText().toString().equalsIgnoreCase("")) {


                  // int selectedId = radioTubeType.getCheckedRadioButtonId();
                  // radioButtonTubeType = (RadioButton) getView().findViewById(selectedId);


                  String tubeType = "";
                  if (radioButtonTubeTypeBlood.isChecked()) {
                    tubeType = "BLOOD";
                  } else if (radioButtonTubeTypeUrine.isChecked()) {
                    tubeType = "URINE";
                  } else {
                    tubeType = "ALIQUOT";
                  }

                  dialog.dismiss();
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
                          tubeType,
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
                          txtEntryDate.getText().toString().trim());

                } else {
                  Toast.makeText(context, "Please select Entry Date", Toast.LENGTH_SHORT).show();
                }

              } else {
                Toast.makeText(context, "Please enter Discard Tube Volume", Toast.LENGTH_SHORT).show();
              }

            } else {
              Toast.makeText(context, "Please enter Tube Volume", Toast.LENGTH_SHORT).show();
            }

          } else {
            Toast.makeText(context, "Please enter Timpepoint", Toast.LENGTH_SHORT).show();
          }

        } else {
          Toast.makeText(context, "Please enter Cohort Number", Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(context, "Please enter Site Number", Toast.LENGTH_SHORT).show();
      }
    }else {

      if (edtSiteNo.getText().toString().trim().length() > 0) {

        if (edtCohortNo.getText().toString().trim().length() > 0) {

          if (edtTimepoint.getText().toString().trim().length() > 0) {

            if (edtAliquotTubeVolume.getText().toString().trim().length() > 0) {

              if(edtDiscardTubeVolume.getText().toString().trim().length() > 0) {

                if (!txtEntryDate.getText().toString().equalsIgnoreCase("")) {


                  // int selectedId = radioTubeType.getCheckedRadioButtonId();
                  // radioButtonTubeType = (RadioButton) getView().findViewById(selectedId);


                  String tubeType = "";
                  if (radioButtonTubeTypeBlood.isChecked()) {
                    tubeType = "BLOOD";
                  } else if (radioButtonTubeTypeUrine.isChecked()) {
                    tubeType = "URINE";
                  }else if(rAliquot.isChecked()){
                    tubeType = "ALIQUOT";
                  } else{

                  }

                  dialog.dismiss();
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
                          tubeType,
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
                          txtEntryDate.getText().toString().trim());

                } else {
                  Toast.makeText(context, "Please select Entry Date", Toast.LENGTH_SHORT).show();
                }

              } else {
                Toast.makeText(context, "Please enter Discard Tube Volume", Toast.LENGTH_SHORT).show();
              }

            } else {
              Toast.makeText(context, "Please enter Tube Volume", Toast.LENGTH_SHORT).show();
            }

          } else {
            Toast.makeText(context, "Please enter Timepoint", Toast.LENGTH_SHORT).show();
          }

        } else {
          Toast.makeText(context, "Please enter Cohort Number", Toast.LENGTH_SHORT).show();
        }
      } else {
        Toast.makeText(context, "Please enter Site Number", Toast.LENGTH_SHORT).show();
      }

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
    tsuRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(context));
    tsuRequestModel.setUserRole(new PrefManager(context).getUserRoleType());
    tsuRequestModel.setUserName(new PrefManager(context).getUserName());
    tsuRequestModel.setTagId(new PrefManager(context).getBarCodeValue());
    tsuRequestModel.setEvent(AppConstants.ADD_TSU);

    tsuRequestModel.setVisit(visit);
    tsuRequestModel.setStudyName(strStudyName);
    //tsuRequestModel.setStudyName("SST");
    tsuRequestModel.setKitId(strKitName);
    tsuRequestModel.setStudyId(Integer.valueOf(spnSelectedStudyID));
    //tsuRequestModel.setStudyId(4);
    tsuRequestModel.setKitRecId(Integer.valueOf(strKitRecId));
        /*if(rbTypeValue.equalsIgnoreCase("Blood")) {
            tsuRequestModel.setTubeType("Blood");
        }else {
            tsuRequestModel.setTubeType(0);
        }*/
    tsuRequestModel.setTubeType(rbTypeValue);

    if(rbTypeValue.equalsIgnoreCase("BLOOD")){

      tsuRequestModel.setTubeColor(tubeColor);
      tsuRequestModel.setTubeVol(tubeVolume);
      tsuRequestModel.setAliquotColor("-");
      tsuRequestModel.setAliquotVol("0");
      tsuRequestModel.setAliquotExt("-");

    }else if(rbTypeValue.equalsIgnoreCase("URINE")) {

      tsuRequestModel.setTubeColor(tubeColor);
      tsuRequestModel.setTubeVol(tubeVolume);
      tsuRequestModel.setAliquotColor("-");
      tsuRequestModel.setAliquotVol("0");
      tsuRequestModel.setAliquotExt("-");

    }else  if(rbTypeValue.equalsIgnoreCase("ALIQUOT")) {

      tsuRequestModel.setTubeColor("-");
      tsuRequestModel.setTubeVol("0");

      tsuRequestModel.setAliquotColor(aliquotColor);
      tsuRequestModel.setAliquotVol(aliquotVol);


      if(!aliquotExtNo.isEmpty()){
        tsuRequestModel.setAliquotExt(aliquotExtNo);
      }else {
        tsuRequestModel.setAliquotExt("-");
      }

    }else{

    }

    tsuRequestModel.setIsDuplicate(0);
    tsuRequestModel.setEntryDate(txtEntryDate);
    tsuRequestModel.setSiteNo(siteNo);
    tsuRequestModel.setCohortNo(cohortNo);
    tsuRequestModel.setPi(prim_investegator);
    tsuRequestModel.setTimepoint(strTimePoint);
    tsuRequestModel.setDiscardTubeColor(spnDiscardTubeColor);
    tsuRequestModel.setDiscardTubeVolume(discardTubeVol);
    tsuRequestModel.setTestName(spnTestName);
    tsuRequestModel.setCollectionLable(spnCollectionLabel);
    tsuRequestModel.setTransportLable(spnTransportLabel);
    //tsuRequestModel.setCentrifugeProg(centriProg);
    if(!centriProg.isEmpty()){
      tsuRequestModel.setCentrifugeProg(centriProg);
    }else {
      tsuRequestModel.setCentrifugeProg("-");
    }

    tsuRequestModel.setLabUse(strLabUse);

    new NetworkingHelper(new AddTSURequest((Activity)context, true, tsuRequestModel)) {

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

                Utils.showAlertDialog((Activity) context,  commonResponse.getResponse().get(0).getMessage());
                callGetTSUDetailsAPI();

                // Utils.showAlertDialog(context,  commonResponse.getResponse().get(0).getMessage());

                /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = ((Activity) context).getParent().getLayoutInflater();
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
                    Intent mNextActivity = new Intent(context, TSUSetupActivity.class);
                    context.startActivity(mNextActivity);
                    //context.finish();
                  }
                });

                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                edtSiteNo.setText("");
                edtCohortNo.setText("");
                edtTimepoint.setText("");
                edtTubeVolume.setText("");*/
                //txtEntryDate.setText("");

              }else {

                Logger.logError("addTSU API Failure " +
                        commonResponse.getResponse().get(0).isStatus());
                Logger.logError("addTSU API Failure " +
                        commonResponse.getResponse().get(0).getMessage());

                Utils.showAlertDialog((Activity)context,  commonResponse.getResponse().get(0).getMessage());
              }

            }else {

              Utils.showAlertDialog((Activity)context,  commonResponse.getStatus().geteRROR());
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

}

