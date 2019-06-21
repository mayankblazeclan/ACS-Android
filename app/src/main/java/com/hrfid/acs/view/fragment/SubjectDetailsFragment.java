package com.hrfid.acs.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetScheduleRequest;
import com.hrfid.acs.helpers.request.GetSubjectDetailsRequest;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetScheduleResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetSubjectDetails.GetSubjectDetailsResponse;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.SubjectDetailsAdapter;
import com.hrfid.acs.view.adapter.ViewScreenStudyFragmentAdapter;
import com.hrfid.acs.view.adapter.ViewTrialStudyFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MS on 2019-05-30.
 */
public class SubjectDetailsFragment extends Fragment {

    private LinearLayout linearLayout;
    private TextView textView;
    private  RecyclerView recyclerView;
    //private  List<Integer> lists = new ArrayList<>();
    private  List<StudyList> listStudy = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_screen_study, container, false);

        initViews(v);

        getAllStudyID();

        getSubjectOnboardingDetails();

        return v;
    }

    private void initViews(View v) {

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter

        linearLayout = v.findViewById(R.id.llRec);
        textView = v.findViewById(R.id.txtNoData);

        /*if(personNames.size() >0) {
            linearLayout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(getContext(), personNames);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        }else {

            linearLayout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }*/
    }



    //Call getSubjectOnboardingDetails API
    private void getSubjectOnboardingDetails() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        commonRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_SUBJECT);
        commonRequestModel.setUserName(new PrefManager(getActivity()).getUserName());

        new NetworkingHelper(new GetSubjectDetailsRequest(getActivity(), true,
                commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetSubjectDetailsResponse getSubjectDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetSubjectDetailsResponse.class);

                        if (getSubjectDetailsResponse.getStatus().getCODE() == 200) {

                            if(getSubjectDetailsResponse.getSubjectList().size() > 0){

                                linearLayout.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);

                                Logger.logError("getSubjectOnboardingDetails API success status " +
                                        getSubjectDetailsResponse.getStatus());
                                Logger.logError("getSubjectOnboardingDetails API success getStudyList" +
                                        getSubjectDetailsResponse.getSubjectList());

                                getAllStudyID();

                                SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(getActivity(), getSubjectDetailsResponse.getSubjectList(), listStudy, recyclerView);
                                recyclerView.setAdapter(customAdapter);


                            }else {

                                Logger.logError("getSubjectOnboardingDetails API Failure " +
                                        "getStudyList" +
                                        getSubjectDetailsResponse.getSubjectList());

                                linearLayout.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);

                                Utils.showAlertDialog(getActivity(),  "NO DATA IN STUDY");
                            }

                        }else {

                            Logger.logError("getSubjectOnboardingDetails API Failure " +
                                    getSubjectDetailsResponse.getStatus().getCODE());
                            Logger.logError("getSubjectOnboardingDetails API Failure " +
                                    getSubjectDetailsResponse.getStatus().getMSG());

                            Utils.showAlertDialog(getActivity(),  getSubjectDetailsResponse.getStatus()
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


                                    listStudy = commonResponse.getStudyList();

                                    for (int i = 0; i < commonResponse.getStudyList().size(); i++) {

                                       // lists.add(commonResponse.getStudyList().get(i).getValue());
                                       // listStudy.add(commonResponse.getStudyList());

                                    }


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
