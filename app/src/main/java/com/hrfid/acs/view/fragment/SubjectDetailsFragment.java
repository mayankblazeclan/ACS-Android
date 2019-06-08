package com.hrfid.acs.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.GetScheduleRequest;
import com.hrfid.acs.helpers.request.GetSubjectDetailsRequest;
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

/**
 * Created by MS on 2019-05-30.
 */
public class SubjectDetailsFragment extends Fragment {

    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3",
            "Person 4", "Person 5", "Person 6", "Person 7"));

    private LinearLayout linearLayout;
    private TextView textView;
    private  RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_screen_study, container, false);

        initViews(v);

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

                            if(getSubjectDetailsResponse.getStudyList().size() > 0){

                                linearLayout.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);

                                Logger.logError("getSubjectOnboardingDetails API success status " +
                                        getSubjectDetailsResponse.getStatus());
                                Logger.logError("getSubjectOnboardingDetails API success getStudyList" +
                                        getSubjectDetailsResponse.getStudyList());

                                SubjectDetailsAdapter customAdapter = new SubjectDetailsAdapter(getContext(), getSubjectDetailsResponse.getStudyList());
                                recyclerView.setAdapter(customAdapter);


                            }else {

                                Logger.logError("getSubjectOnboardingDetails API Failure " +
                                        "getStudyList" +
                                        getSubjectDetailsResponse.getStudyList());

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

}
