package com.hrfid.acs.view.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrfid.acs.R;
import com.hrfid.acs.helpers.network.ApiResponse;
import com.hrfid.acs.helpers.network.JsonParser;
import com.hrfid.acs.helpers.network.NetworkingHelper;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.CreateScheduleModel;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.GetScheduleRequest;
import com.hrfid.acs.helpers.serverResponses.models.CommonResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetScheduleResponse;
import com.hrfid.acs.helpers.serverResponses.models.StudyList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.activity.NotificationActivity;
import com.hrfid.acs.view.adapter.ViewScreenStudyFragmentAdapter;
import com.hrfid.acs.view.adapter.ViewTrialStudyFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by MS on 2019-05-30.
 */
public class ViewScreenStudyFragment extends Fragment {

    private RecyclerView recyclerView;
    private boolean isFromScreening;
    private List<StudyList> listScreen =null;
    private List<StudyList> listTrial = null;
    private LinearLayout linearLayout;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_screen_study, container, false);

        initViews(v);

        getScheduleDetails();

        return v;
    }

    private void initViews(View v) {

        isFromScreening =  getArguments().getBoolean("isFromScreening");
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        linearLayout = v.findViewById(R.id.llRec);
        textView = v.findViewById(R.id.txtNoData);
    }



    //Call getScheduleDetails API
    private void getScheduleDetails() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        commonRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_SCHEDULE);
        commonRequestModel.setUserName(new PrefManager(getActivity()).getUserName());

        new NetworkingHelper(new GetScheduleRequest(getActivity(), true,
                commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetScheduleResponse getScheduleResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetScheduleResponse.class);

                        if (getScheduleResponse.getStatus().getCODE() == 200) {

                            if(getScheduleResponse.getStudyList().size() > 0){

                                linearLayout.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);

                                Logger.logError("getScheduleDetails API success status " +
                                        getScheduleResponse.getStatus());
                                Logger.logError("getScheduleDetails API success getStudyList" +
                                        getScheduleResponse.getStudyList());

                                listScreen = new ArrayList<>();
                                listTrial = new ArrayList<>();

                                for (int i = 0; i < getScheduleResponse.getStudyList().size(); i++) {
                                    if(getScheduleResponse.getStudyList().get(i).getIsTrial()
                                            .equalsIgnoreCase("0")){
                                        listScreen.add(getScheduleResponse.getStudyList().get(i));
                                    }else if(getScheduleResponse.getStudyList().get(i).getIsTrial()
                                            .equalsIgnoreCase("1")) {
                                        listTrial.add(getScheduleResponse.getStudyList().get(i));
                                    }else {
                                    }

                                }

                                if(isFromScreening == true){
                                    if(listScreen.size() >0) {
                                        linearLayout.setVisibility(View.VISIBLE);
                                        textView.setVisibility(View.GONE);

                                        ViewScreenStudyFragmentAdapter customAdapter
                                                = new ViewScreenStudyFragmentAdapter(getContext(),
                                                listScreen, isFromScreening, recyclerView);
                                        recyclerView.setAdapter(customAdapter);
                                    }else {
                                        linearLayout.setVisibility(View.GONE);
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    if(listTrial.size() >0) {

                                        linearLayout.setVisibility(View.VISIBLE);
                                        textView.setVisibility(View.GONE);

                                        ViewTrialStudyFragmentAdapter viewTrialStudyFragmentAdapter
                                                = new ViewTrialStudyFragmentAdapter(getContext(),
                                                listTrial, isFromScreening, recyclerView);
                                        recyclerView.setAdapter(viewTrialStudyFragmentAdapter);
                                    }else {
                                        linearLayout.setVisibility(View.GONE);
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }
                            }else {

                                Logger.logError("getScheduleDetails API Failure " +
                                        "getStudyList" +
                                        getScheduleResponse.getStudyList());

                                linearLayout.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);

                                Utils.showAlertDialog(getActivity(),  "NO DATA IN STUDY");
                            }

                        }else {

                            Logger.logError("getScheduleDetails API Failure " +
                                    getScheduleResponse.getStatus().getCODE());
                            Logger.logError("getScheduleDetails API Failure " +
                                    getScheduleResponse.getStatus().getMSG());

                            Utils.showAlertDialog(getActivity(),  getScheduleResponse.getStatus()
                                    .getMSG());
                        }



                    }
                    catch (Exception e){
                        Logger.logError("Exception " + e.getMessage());
                    }

                } else {
                    Logger.logError("getScheduleDetails API Failure " +
                            serverResponse.errorMessageToDisplay);
                }
            }
        };

    }

}
