package com.hrfid.acs.helpers.network;

import android.app.ProgressDialog;


import com.hrfid.acs.helpers.request.BaseApiRequest;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.GetNotificationRequest;
import com.hrfid.acs.helpers.request.GetScheduleRequest;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.request.ModifyScheduleRequest;
import com.hrfid.acs.helpers.request.ResetNotificationCountRequest;
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


    private Call<ResponseBody> apiInterface;
  private String TAG = "NetworkingHelper = ";
  private BaseApiRequest mCmgRequest;
  private ProgressDialog pd;

    /*
        =================
     */


  public NetworkingHelper(BaseApiRequest cmgRequest) {

    mCmgRequest = cmgRequest;

    //EventBus.getDefault().register(this);

    apiInterface = null;

    switch (cmgRequest.apiToCall) {

      /*case LOGIN:
        LoginRequestModel loginRequest = (LoginRequestModel) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .loginToWebSSOAPI(loginRequest.map);
        break;
      case SSO_HEART_BEAT:
        SSOHeartBeatRequest ssoHeartBeatRequest = (SSOHeartBeatRequest) cmgRequest;

        apiInterface = ApiRouter.get().getRetrofitService()
            .getHeartBeatAPI(ssoHeartBeatRequest.map);
        break;

      case GET_HOME_LIST:
        apiInterface = ApiRouter.get().getRetrofitService().getHomeListAPI();

        break;

      case GET_ARTICLE_LIST:
        apiInterface = ApiRouter.get().getRetrofitService().getArticleListAPI();
        break;

      case POST_FAVORITE_ARTICLE:

        RequestFavoriteArticle requestFavoriteArticle = (RequestFavoriteArticle) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .postFavoriteArticleAPI(requestFavoriteArticle.articleNo);
        break;

      case SAVED_LIST_RESULT:
        apiInterface = ApiRouter.get().getRetrofitService().getSavedListAPI();
        break;

      case GET_CALENDAR_LIST:
        apiInterface = ApiRouter.get().getRetrofitService().getCalendarAPI();
        break;

      case UNSAVED_LIST_RESULT:
        // apiInterface = ApiRouter.get().getRetrofitService().getUnSavedListAPI();

        UnsavedListRequest unsavedListRequest = (UnsavedListRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getUnSavedListAPI(unsavedListRequest.id, unsavedListRequest.contentType);
        break;
      case ARTICLE_CATEGORY:

        ArticleCategoryListRequest articleCategoryListRequest = (ArticleCategoryListRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getArticleCategoryAPI(String.valueOf(articleCategoryListRequest.articleNo));
        break;

      case GET_VIDEO_SUBCATEGORY:

        VideoCategoryListRequest videoCategoryListRequest = (VideoCategoryListRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getVideoCategoryAPI(String.valueOf(videoCategoryListRequest.videoId));
        break;

      case GET_VIDEO_LIST:
        apiInterface = ApiRouter.get().getRetrofitService().getVideoListAPI();
        break;

      case ARTICLE_VIDEO_SAVED:

        ArticleVideoSavedRequest articleVideoSavedRequest = (ArticleVideoSavedRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .postArticleVideoSavedAPI(articleVideoSavedRequest.id,
                articleVideoSavedRequest.contentType);
        break;

      case ARTICLE_DETAILS:
        RequestArticleDetails requestArticleIDAPI = (RequestArticleDetails) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getArticleWithIDAPI(requestArticleIDAPI.articleID);
        break;

      case GET_TAG_LIST:
        RequestTagsAPI requestTagsAPI = (RequestTagsAPI) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getTagsList(requestTagsAPI.mTagName);
        break;

      case ARTICLE_TAG_LIST:
        ArticleTagsRequest articleTagsRequest = (ArticleTagsRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
                .getArticleTagsList(articleTagsRequest.mTagName);
        break;

      case LOG_OUT:
        SignOutRequest logoutRequest = (SignOutRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().logoutToSSOAPI(logoutRequest.map);
        break;

      case VIDEO_LIST_BY_ID_API:

        VideoListByIDApiRequest videoListByIDApiRequest = (VideoListByIDApiRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getVideoListByID(videoListByIDApiRequest.mPageCount, videoListByIDApiRequest.mMediaID,
                videoListByIDApiRequest.mCategoryID);
        break;

      case GET_SEARCH_RESULT:
        SearchResultRequest requestSearchAPI = (SearchResultRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getSearchResultAPI(requestSearchAPI.mStrSearchText);
        break;

      case ARTICLE_CATEGORY_LAZY_LOAD:

        ArticleCategoryListLazyLoadRequest articleCategoryListLazyLoadRequest = (ArticleCategoryListLazyLoadRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getArticleCategoryLazyLoadAPI(String.valueOf(articleCategoryListLazyLoadRequest.page),
                String.valueOf(articleCategoryListLazyLoadRequest.categoryId));
        break;

      case VIDEO_SUBCATEGORY_LAZY_LOAD:
        VideoCategoryListLazyLoadRequest videoCategoryListLazyLoadRequest = (VideoCategoryListLazyLoadRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getVideoCategoryLazyLoadAPI(String.valueOf(videoCategoryListLazyLoadRequest.page),
                String.valueOf(videoCategoryListLazyLoadRequest.categoryId));
        break;

      case SAVED_LIST_RESULT_LAZY_LOAD:
        SavedListLazyLoadRequest savedListLazyLoadRequest = (SavedListLazyLoadRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService()
            .getSavedListLazyLoadAPI(String.valueOf(savedListLazyLoadRequest.page),
                String.valueOf(savedListLazyLoadRequest.pagesize));
        break;

      case GET_FAQ_LIST:
        RequestFAQAPI requestFAQAPI = (RequestFAQAPI) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getFAQListAPI(requestFAQAPI.mURL);
        break;

      case LIVE_STREAMING_LIST_API:
        LiveStreamingListRequest liveStreamingListRequest = (LiveStreamingListRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getLiveStreamingListAPI();
        break;

      case SET_DEVICE_DETAILS:
        SetDeviceDetailsRequest setDeviceDetailsRequest = (SetDeviceDetailsRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().setDeviceDetailsAPI(setDeviceDetailsRequest.setDeviceDetailsRequestModel);
        break;

      case GET_CATEGORIES_BY_DEVICE:
        GetCategoriesByDeviceRequest getCategoriesByDeviceRequest = (GetCategoriesByDeviceRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getCategoriesByDevice(getCategoriesByDeviceRequest.getCategoriesByDeviceRequestModel);
        break;

      case SET_CATEGORY_STATUS_BY_DEVICE:
        SetCategoryStatusByDeviceRequest setCategoryStatusByDevice = (SetCategoryStatusByDeviceRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().setCategoryStatusByDevice(setCategoryStatusByDevice.setCategoryStatusByDeviceRequestModel);
        break;

      case SET_CATEGORY_STATUS:
        SetCategoryStatusRequest setCategoryStatusRequest = (SetCategoryStatusRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().setCategoryStatus(setCategoryStatusRequest.setCategoryStatusRequestModel);
        break;


      case SET_GLOBAL_NOTIFICATION:
        SetGlobalNotificationRequest setGlobalNotificationRequest = (SetGlobalNotificationRequest) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().setGlobalNotification(setGlobalNotificationRequest.setGlobalNotificationRequestModel);
        break;

      case GET_WEBVIEW_CHECK_STATUS:
        RequestWebviewCheckerAPI requestWebviewCheckerAPI = (RequestWebviewCheckerAPI) cmgRequest;
        apiInterface = ApiRouter.get().getRetrofitService().getFAQListAPI(requestWebviewCheckerAPI.mURL);
        break;*/


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
