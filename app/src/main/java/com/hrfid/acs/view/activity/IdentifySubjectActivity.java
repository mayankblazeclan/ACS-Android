package com.hrfid.acs.view.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.components.BaseActivity;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.IdentifySubjectRequest;
import com.hrfid.acs.helpers.request.IdentifySubjectRequestModel;
import com.hrfid.acs.helpers.request.SearchSubjectRequest;
import com.hrfid.acs.helpers.request.SearchSubjectRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.IdentifySubject.IdentifySubjectResponse;
import com.hrfid.acs.helpers.serverResponses.models.IdentifySubject.SubjectList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.SubjectDetailsAdapter;

import java.util.List;

/**
 * Created by MS on 2019-06-22.
 */
public class IdentifySubjectActivity extends BaseActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nurse_identify_subject);

        editText = (EditText) findViewById(R.id.et_barcode_number);
        button = findViewById(R.id.btn_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().length()!=0){

                    callIdentifySubjectAPI(editText.getText().toString());

                }else {
                    showToastMessage("Scan / Enter Barcode");
                }
            }
        });

    }

    private void callIdentifySubjectAPI(String barcodeValue) {

        IdentifySubjectRequestModel identifySubjectRequestModel = new IdentifySubjectRequestModel();
        identifySubjectRequestModel.setAppName(AppConstants.APP_NAME);
        identifySubjectRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        identifySubjectRequestModel.setDeviceType(AppConstants.APP_OS);
        identifySubjectRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        identifySubjectRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(this));
        identifySubjectRequestModel.setUserRole(new PrefManager(this).getUserRoleType());
        identifySubjectRequestModel.setTagId(new PrefManager(this).getBarCodeValue());
        identifySubjectRequestModel.setEvent(AppConstants.IDENTIFY_SUBJECT);
        identifySubjectRequestModel.setUserName(new PrefManager(this).getUserName());
        identifySubjectRequestModel.setSubjectBarcode(barcodeValue);

        new NetworkingHelper(new IdentifySubjectRequest(this, true, identifySubjectRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        IdentifySubjectResponse identifySubjectResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, IdentifySubjectResponse.class);

                        if (identifySubjectResponse.getStatus().getCODE() == 200) {

                            if(identifySubjectResponse.getSubjectList().size() > 0){

                                Logger.logError("identifySubject API success " +
                                        identifySubjectResponse.getSubjectList().get(0).getScreenId());
                                Logger.logError("identifySubject API success " +
                                        identifySubjectResponse.getSubjectList().get(0).getGroupId());

                                //Utils.showAlertDialog(getActivity(),  commonResponse.getResponse().get(0).getMessage());

                                //Utils.showAlertDialog(IdentifySubjectActivity.this,  identifySubjectResponse.getStatus().getMSG());

                                showIdentifiedData(identifySubjectResponse.getSubjectList());


                            }else {



                                Logger.logError("identifySubject API Failure " +
                                        identifySubjectResponse.getSubjectList());
                                Logger.logError("identifySubject API Failure " +
                                        identifySubjectResponse.getStatus());

                                Utils.showAlertDialog(IdentifySubjectActivity.this,  identifySubjectResponse.getStatus().getMSG());
                            }

                        }else {

                            Logger.logError("identifySubject API Failure " +
                                    identifySubjectResponse.getStatus().getMSG());
                            Logger.logError("identifySubject API Failure " +
                                    identifySubjectResponse.getStatus().getCODE());

                            Utils.showAlertDialog(IdentifySubjectActivity.this,  identifySubjectResponse.getStatus().getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("identifySubject Exception " + e.getMessage());
                    }

                } else {

                    Logger.logError("identifySubject API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };



    }

    private void showIdentifiedData(List<SubjectList> subjectList) {

        final TextView textView, textView1, textView2, textView3, textView4, textView5, textView6;
        final  Button btnOK;

        // Create custom dialog object
        final Dialog dialog = new Dialog(IdentifySubjectActivity.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.dialog_identify_subject);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView = dialog.findViewById(R.id.screenId);
        textView1 = dialog.findViewById(R.id.screenId1);
        textView2 = dialog.findViewById(R.id.screenId2);
        textView3 = dialog.findViewById(R.id.screenId3);
        textView4 = dialog.findViewById(R.id.screenId4);
        textView5 = dialog.findViewById(R.id.screenId5);
        textView6 = dialog.findViewById(R.id.screenId6);
        btnOK = dialog.findViewById(R.id.btnOK);

        if(subjectList!=null){

            textView.setText("TITLE :" +subjectList.get(0).getGenBarcodeVal());
            textView1.setText("GROUP :"+subjectList.get(0).getGroupId());
            textView2.setText("SCREEN ID :"+subjectList.get(0).getScreenId());
            textView3.setText("STUDY ID :"+subjectList.get(0).getStudyId());
            textView4.setText("DOB :"+subjectList.get(0).getDOB());
            textView5.setText("GENDER :"+subjectList.get(0).getGender());
            textView6.setText("STATUS :"+subjectList.get(0).getStatus());
            //textView.setText(subjectList.get(0).getId());
            //textView.setText(subjectList.get(0).getId());
        }

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();


    }
}
