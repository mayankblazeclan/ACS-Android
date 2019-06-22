package com.hrfid.acs.helpers.network;

import android.app.ProgressDialog;


import com.hrfid.acs.helpers.request.AddSubjectRequest;
import com.hrfid.acs.helpers.request.BaseApiRequest;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.DeleteScheduleRequest;
import com.hrfid.acs.helpers.request.DeleteSubjectRequest;
import com.hrfid.acs.helpers.request.GetAllStudyIdRequest;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.GetScheduleRequest;
import com.hrfid.acs.helpers.request.GetSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.IdentifySubjectRequest;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.request.MapSubjectDetailsRequest;
import com.hrfid.acs.helpers.request.MapSubjectRequestModel;
import com.hrfid.acs.helpers.request.ModifyScheduleRequest;
import com.hrfid.acs.helpers.request.ModifySubjectRequest;
import com.hrfid.acs.helpers.request.ResetNotificationCountRequest;
import com.hrfid.acs.helpers.request.SearchSubjectRequest;
import com.hrfid.acs.helpers.serverResponses.ErrorArrayResponse;
import com.hrfid.acs.helpers.serverResponses.ErrorObjectResponse;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Akshay Kalpe on 15/04/17.
 */

public abstract class NetworkingHelper {


  public static final String INTERNET_MSG = "Please check your internet connection!";
  public static final String TIMEOUT_MSG = "Timeout occured please try again";
  /*
      =================
   */
  public static final int LOGIN = 1;
  public static final int LOG_OUT = 2;
  public static final int GET_NOTIFCATION = 3;
  public static final int RESET_NOTIFCATION_COUNT = 4;
  public static final int CREATE_SCHEDULE = 5;
  public static final int GET_SCHEDULE = 6;
  public static final int MODIFY_SCHEDULE = 7;
  public static final int DELETE_SCHEDULE = 8;
  public static final int GET_ALL_STUDY_ID = 9;
  public static final int ADD_SUBJECT_ONBOARDING = 10;
  public static final int GET_SUBJECT_DETAILS = 11;
  public static final int MAP_SUBJECT_DETAILS = 12;
  public static final int DELETE_SUBJECT = 13;
  public static final int MODIFY_SUBJECT = 14;
  public static final int SEARCH_SUBJECT_ONBOARDING = 15;
  public static final int IDENTIFY_SUBJECT_ONBOARDING = 16;


    private Call<ResponseBody> apiInterface;
  private String TAG = "NetworkingHelper = ";
  private BaseApiRequest mCmgRequest;
  private ProgressDialog pd;

    /*
        =================
     */


  public NetworkingHelper(BaseApiRequest cmgRequest) {
    mCmgRequest = cmgRequest;
    apiInterface = null;

    switch (cmgRequest.apiToCall) {

      case LOG_OUT:
        LogoutRequest logoutRequest = (LogoutRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().logoutApi(logoutRequest.commonRequestModel);
        break;

      case GET_NOTIFCATION:
        GetNotificationRequest getNotificationRequest = (GetNotificationRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getNotification(getNotificationRequest.commonRequestModel);
        break;

      case RESET_NOTIFCATION_COUNT:
        ResetNotificationCountRequest resetNotificationCountRequest = (ResetNotificationCountRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().resetNotificationCount(resetNotificationCountRequest.commonRequestModel);
        break;

      case CREATE_SCHEDULE:
        CreateScheduleRequest createScheduleRequest = (CreateScheduleRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().createSchedule(createScheduleRequest.createScheduleModel);
        break;

      case GET_SCHEDULE:
        GetScheduleRequest getScheduleRequest = (GetScheduleRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getStudySchedule(getScheduleRequest.commonRequestModel);
        break;

      case MODIFY_SCHEDULE:
        ModifyScheduleRequest modifyScheduleRequest = (ModifyScheduleRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().modifySchedule(modifyScheduleRequest.createScheduleModel);
        break;

      case DELETE_SCHEDULE:
        DeleteScheduleRequest deleteScheduleRequest = (DeleteScheduleRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().deleteStudySchedule(deleteScheduleRequest.deleteScheduleRequestModel);
        break;

      case GET_ALL_STUDY_ID:
        GetAllStudyIdRequest getAllStudyIdRequest = (GetAllStudyIdRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getStudyIds(getAllStudyIdRequest.commonRequestModel);
        break;

      case ADD_SUBJECT_ONBOARDING:
        AddSubjectRequest addSubjectRequest = (AddSubjectRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().subjectOnboard(addSubjectRequest.addSubjectRequestModel);
        break;

      case GET_SUBJECT_DETAILS:
        GetSubjectDetailsRequest getSubjectDetailsRequest = (GetSubjectDetailsRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getAllSubjects(getSubjectDetailsRequest.commonRequestModel);
        break;

      case MAP_SUBJECT_DETAILS:
        MapSubjectDetailsRequest mapSubjectDetailsRequest = (MapSubjectDetailsRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().mapSubject(mapSubjectDetailsRequest.mapSubjectRequestModel);
        break;

      case DELETE_SUBJECT:
        DeleteSubjectRequest deleteSubjectRequest = (DeleteSubjectRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().deleteSubject(deleteSubjectRequest.deleteScheduleRequestModel);
        break;

        //modifySubject
      case MODIFY_SUBJECT:
        ModifySubjectRequest modifySubjectRequest = (ModifySubjectRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().modifySubject(modifySubjectRequest.modifySubjectRequestModel);
        break;

      //searchSubject
      case SEARCH_SUBJECT_ONBOARDING:
        SearchSubjectRequest searchSubjectRequest = (SearchSubjectRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().searchSubject(searchSubjectRequest.searchSubjectRequestModel);
        break;

      //verifySubjectBarcode
      case IDENTIFY_SUBJECT_ONBOARDING:
        IdentifySubjectRequest identifySubjectRequest = (IdentifySubjectRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().verifySubjectBarcode(identifySubjectRequest.searchSubjectRequestModel);
        break;

      default:
        Utilities.showToast(cmgRequest.mActivity, "Initialize Request class properly!");
        break;
    }
    getResponseFromServerAndPassItBack(apiInterface);
  }

  public static RequestBody getAPIRequestInJSONFormat(Map<String, Object> mapForApi) {

    RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("text/json"),
        (new JSONObject((mapForApi)).toString()));
    Logger.log("body  " + requestBody.toString());
    return requestBody;
  }

  private static JSONObject convertMapToJson(Map map) throws JSONException {

    JSONObject obj = new JSONObject();
    JSONObject main = new JSONObject();
    Set set = map.keySet();

    for (Object aSet : set) {
      String key = (String) aSet;
      obj.accumulate(key, map.get(key));
    }
//    main.accumulate("", obj);
    return obj;
  }

  //@Subscribe
    /*public void onDateItemTappedEvent() {
        try {
            Logger.log("NEW DATE ITEM CLICKED ===  " + this.mCmgRequest.apiName);
            this.cancelCurrentRunningRequest();
        } catch (Exception e) {
            Logger.logError(e.getMessage());
        }
    }*/

  private void cancelCurrentRunningRequest() {
    try {
      if (apiInterface != null) {
        apiInterface.cancel();
      }
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
  }

  public abstract void serverResponseFromApi(ApiResponse serverResponse);

  private void getResponseFromServerAndPassItBack(Call<ResponseBody> apiInterface) {

    try {
      if (mCmgRequest.showProgressDialog) {
        pd = new ProgressDialog(mCmgRequest.mActivity);
        if (!pd.isShowing()) {
          pd.setMessage("Loading...");
          pd.setIndeterminate(true);
          pd.setCancelable(false);
          pd.show();                // Commented due to the WindowLeaked Exception which was crashing the app
        }

        /*pd = CustomNetworkProgressDialog.ctor(mCmgRequest.mActivity);
        if (!pd.isShowing()) {
          pd.show();
        }*/
      }
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }

    apiInterface.enqueue(new Callback<ResponseBody>() {

      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        //EventBus.getDefault().unregister(this);
        if (pd != null) {
          pd.dismiss();
        }
        String serverRes = null, errorRs = null;
        try {
          if (response.body() != null) {
            serverRes = response.body().string();
          }

        } catch (Exception e) {
          Logger.logError(e.getMessage());
        }

        try {
          if (response.errorBody() != null) {

            errorRs = response.errorBody().string();
          }

        } catch (Exception e) {
          Logger.logError(e.getMessage());
        }
        Logger.log("API NAME  = " + mCmgRequest.apiName);
        Logger.log("response.body() = " + serverRes);
        if (errorRs != null) {
          Logger.log("response.errorBody() = " + errorRs);
        }
        if (serverRes != null) {
          serverResponseFromApi(new ApiResponse(true, serverRes, response.headers()));
        }
        if (errorRs != null) {
          String finalErrorMsg = null;
          try {
            ErrorArrayResponse errorMsg = JsonParser.parseClass(errorRs, ErrorArrayResponse.class);
            finalErrorMsg = errorMsg.errors.get(0);
          } catch (Exception e) {
            Logger.logError(e.getMessage());
          }
          try {
            if (finalErrorMsg == null) {
              ErrorObjectResponse errorMsg = JsonParser
                  .parseClass(errorRs, ErrorObjectResponse.class);
              finalErrorMsg = errorMsg.errors.fullMessages.get(0);
            }
          } catch (Exception e) {
            Logger.logError(e.getMessage());
          }
          if (finalErrorMsg == null) {
            finalErrorMsg = "Some internal server error occurred";
          }
          serverResponseFromApi(new ApiResponse(false, finalErrorMsg));
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {

        //EventBus.getDefault().unregister(this);

        if (pd != null) {
          pd.dismiss();
        }
        if (call.isCanceled()) {
          Logger.log("Request is cancelled " + call.toString());
          return;
        }
        String error = t.getMessage();
          Logger.log(TAG + t.getCause());
        Logger.log(TAG + error);
        if (error != null) {
          if (error.contains("failed to connect to")) {
            serverResponseFromApi(new ApiResponse(false, TIMEOUT_MSG));
          } else if (error.contains("Unable to resolve host")) {
//            Utilities.showSnackBar(mCmgRequest.mActivity.getCurrentFocus(), "NO");
            serverResponseFromApi(new ApiResponse(false, INTERNET_MSG));
          } else {
            serverResponseFromApi(new ApiResponse(false, "Code Issue"));
          }
        } else {

          serverResponseFromApi(new ApiResponse(false, "Other Issue"));
        }
      }
    });
  }
}
