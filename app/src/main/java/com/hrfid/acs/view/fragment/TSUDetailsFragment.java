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
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetKitDetailsRequest;
import com.hrfid.acs.helpers.request.GetTSUDetailsRequest;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.GetAllStudyIdResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID.StudyList;
import com.hrfid.acs.helpers.serverResponses.models.GetKitDetails.GetKitDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails.GetTSUDetailsResponse;
import com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails.TSUList;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.PrefManager;
import com.hrfid.acs.util.Utilities;
import com.hrfid.acs.util.Utils;
import com.hrfid.acs.view.adapter.KitDetailsAdapter;
import com.hrfid.acs.view.adapter.TSUArchiveDetailsAdapter;
import com.hrfid.acs.view.adapter.TSUDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MS on 2019-05-30.
 */
public class TSUDetailsFragment extends Fragment {

    private LinearLayout linearLayout;
    private TextView textView;
    private  RecyclerView recyclerView;
    private  List<Integer> listGetStudyList = new ArrayList<>();
    private  List<StudyList> listStudy = new ArrayList<>();
    private  List<TSUList> tsuLists = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_screen_study, container, false);

        initViews(v);

        getAllStudyID();

        callGetTSUDetailsAPI();

        return v;
    }

    private void initViews(View v) {

        // get the reference of RecyclerView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayout = v.findViewById(R.id.llRec);
        textView = v.findViewById(R.id.txtNoData);
    }



    //Call callGetTSUDetails API
    private void callGetTSUDetailsAPI() {
        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppName(AppConstants.APP_NAME);
        commonRequestModel.setVersionNumber(AppConstants.APP_VERSION);
        commonRequestModel.setDeviceType(AppConstants.APP_OS);
        commonRequestModel.setModel(Build.MANUFACTURER + " - " + Build.MODEL);
        commonRequestModel.setDeviceNumber(Utilities.getDeviceUniqueId(getActivity()));
        commonRequestModel.setUserRole(new PrefManager(getActivity()).getUserRoleType());
        commonRequestModel.setTagId(new PrefManager(getActivity()).getBarCodeValue());
        commonRequestModel.setEvent(AppConstants.GET_TSU_DETAILS);
        commonRequestModel.setUserName(new PrefManager(getActivity()).getUserName());

        new NetworkingHelper(new GetTSUDetailsRequest(getActivity(), true,
                commonRequestModel)) {

            @Override
            public void serverResponseFromApi(ApiResponse serverResponse) {
                if (serverResponse.isSucess) {

                    try {

                        GetTSUDetailsResponse getKitDetailsResponse = JsonParser
                                .parseClass(serverResponse.jsonResponse, GetTSUDetailsResponse.class);

                        if (getKitDetailsResponse.getStatus().getCODE() == 200) {

                            if(getKitDetailsResponse.getTSUList().size() > 0){

                               // linearLayout.setVisibility(View.VISIBLE);
                                //textView.setVisibility(View.GONE);

                                Logger.logError("getTSUList API success status " +
                                        getKitDetailsResponse.getStatus());
                                Logger.logError("getTSUList API success getSubjectList" +
                                        getKitDetailsResponse.getTSUList());

                                getAllStudyID();

                                //TSUDetailsAdapter customAdapter = new TSUDetailsAdapter(getContext(), getKitDetailsResponse.getTSUList(), listStudy, recyclerView);
                                //recyclerView.setAdapter(customAdapter);


                                tsuLists = new ArrayList<>();
                                for (int i = 0; i < getKitDetailsResponse.getTSUList().size(); i++) {

                                    if(getKitDetailsResponse.getTSUList().get(i).getIsArchived() ==0) {
                                        tsuLists.add(getKitDetailsResponse.getTSUList().get(i));
                                    }
                                }


                                if(tsuLists !=null && tsuLists.size() > 0) {
                                    linearLayout.setVisibility(View.VISIBLE);
                                    textView.setVisibility(View.GONE);

                                    TSUDetailsAdapter tsuDetailsAdapter = new TSUDetailsAdapter(getContext(), tsuLists, listStudy, recyclerView);
                                    recyclerView.setAdapter(tsuDetailsAdapter);
                                }else {

                                    linearLayout.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);
                                }



                            }else {

                                Logger.logError("getTSUList API Failure " +
                                        "getSubjectList" +
                                        getKitDetailsResponse.getTSUList());

                                linearLayout.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);

                                Utils.showAlertDialog(getActivity(),  "NO DATA IN STUDY");
                            }

                        }else {

                           /* Logger.logError("getTSUList API Failure " +
                                    getKitDetailsResponse.getStatus().getCODE());
                            Logger.logError("getTSUList API Failure " +
                                    getKitDetailsResponse.getStatus().getMSG());*/

                           if(!getKitDetailsResponse.getStatus()
                                   .getERROR().equalsIgnoreCase("No relevant data found")) {

                               Utils.showAlertDialog(getActivity(), getKitDetailsResponse.getStatus()
                                       .getERROR());
                           }
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

                                        listGetStudyList.add(commonResponse.getStudyList().get(i).getValue());

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
