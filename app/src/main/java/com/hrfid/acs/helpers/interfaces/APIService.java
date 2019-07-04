package com.hrfid.acs.helpers.interfaces;


import com.hrfid.acs.helpers.request.AddKitRequest;
import com.hrfid.acs.helpers.request.AddKitRequestModel;
import com.hrfid.acs.helpers.request.AddSubjectRequestModel;
import com.hrfid.acs.helpers.request.CommonRequestModel;
import com.hrfid.acs.helpers.request.CreateScheduleModel;
import com.hrfid.acs.helpers.request.CreateScheduleRequest;
import com.hrfid.acs.helpers.request.DismissKitRequestModel;
import com.hrfid.acs.helpers.request.IdentifySubjectRequestModel;
import com.hrfid.acs.helpers.request.LogoutRequest;
import com.hrfid.acs.helpers.request.MapKitRequestModel;
import com.hrfid.acs.helpers.request.MapSubjectRequestModel;
import com.hrfid.acs.helpers.request.ModifyKitRequestModel;
import com.hrfid.acs.helpers.request.SearchSubjectRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.DeleteScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.ModifyScheduleRequestModel;
import com.hrfid.acs.helpers.serverResponses.models.ModifySubjectRequestModel;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Akshay Kalpe on 15/04/17.
 */

public interface APIService {


  //  LOG OUT
  @POST("/acs/api/user/logout/")
  Call<ResponseBody> logoutApi(@Body CommonRequestModel commonRequestModel);

  //  LOG OUT
  @POST("/acs/api/getNotifications")
  Call<ResponseBody> getNotification(@Body CommonRequestModel commonRequestModel);

  //resetNotificationCount
  @POST("/acs/api/resetNotificationCount")
  Call<ResponseBody> resetNotificationCount(@Body CommonRequestModel commonRequestModel);

  //createSchedule
  @POST("/acs/api/createSchedule")
  Call<ResponseBody> createSchedule(@Body CreateScheduleModel createScheduleModel);

  //getStudySchedule
  @POST("/acs/api/getStudySchedule")
  Call<ResponseBody> getStudySchedule(@Body CommonRequestModel commonRequestModel);

  //modifySchedule
  @POST("/acs/api/modifySchedule")
  Call<ResponseBody> modifySchedule(@Body ModifyScheduleRequestModel modifyScheduleRequestModel);

  //deleteStudySchedule
  @POST("/acs/api/deleteStudySchedule")
  Call<ResponseBody> deleteStudySchedule(@Body DeleteScheduleRequestModel deleteScheduleRequestModel);

  //getStudyIds
  @POST("/acs/api/getStudyIds")
  Call<ResponseBody> getStudyIds(@Body CommonRequestModel commonRequestModel);

  //getSubjectOnboard
  @POST("/acs/api/subjectOnboard")
  Call<ResponseBody> subjectOnboard(@Body AddSubjectRequestModel addSubjectRequestModel);

  //getAllSubjects
  @POST("/acs/api/getAllSubjects")
  Call<ResponseBody> getAllSubjects(@Body CommonRequestModel commonRequestModel);

  //mapSubjects
  @POST("/acs/api/mapSubject")
  Call<ResponseBody> mapSubject(@Body MapSubjectRequestModel mapSubjectRequestModel);

  //deleteSubject
  @POST("/acs/api/deleteSubject")
  Call<ResponseBody> deleteSubject(@Body DeleteScheduleRequestModel deleteScheduleRequestModel);

  //deleteSubject
  @POST("/acs/api/modifySubject")
  Call<ResponseBody> modifySubject(@Body ModifySubjectRequestModel modifySubjectRequestModel);

  //searchSubject
  @POST("/acs/api/searchSubject")
  Call<ResponseBody> searchSubject(@Body SearchSubjectRequestModel searchSubjectRequestModel);

  //verifySubjectBarcode
  @POST("/acs/api/verifySubjectBarcode")
  Call<ResponseBody> verifySubjectBarcode(@Body IdentifySubjectRequestModel identifySubjectRequestModel);

  //addKit
  @POST("/acs/api/addKit")
  Call<ResponseBody> addKit(@Body AddKitRequestModel addKitRequestModel);

  //getKitList
  @POST("/acs/api/getKitList")
  Call<ResponseBody> getKitList(@Body CommonRequestModel commonRequestModel);

  //modifyKit
  @POST("/acs/api/modifyKit")
  Call<ResponseBody> modifyKit(@Body ModifyKitRequestModel modifyKitRequestModel);

  //mapKit
  @POST("/acs/api/mapKit")
  Call<ResponseBody> mapKit(@Body MapKitRequestModel mapKitRequestModel);

  //dismissKit
  @POST("/acs/api/dismissKit")
  Call<ResponseBody> dismissKit(@Body DismissKitRequestModel dismissKitRequestModel);

 /* @POST("api/facebook/fblogin")
  Call<ResponseBody> loginFBToSSOAPI(@Body RequestBody body);

  @POST("api/sso/validatessoticket")
  Call<ResponseBody> loginToWebSSOAPI(@Body Map<String, Object> map);

  //  Celebrity List
  @GET("celebrity/list")
  Call<ResponseBody> celebrityListAPI();

  //  LOG OUT
  @POST("api/sso/logout")
  Call<ResponseBody> logoutToSSOAPI(@Body Map<String, Object> map);

  //
  @POST("api/sso/ssoheartbeat")
  Call<ResponseBody> getHeartBeatAPI(@Body Map<String, Object> map);

  //    //  Get Article List
  @GET("articles/list?page=1&pagesize=50")
  Call<ResponseBody> getArticleListAPI();

  //  Favourite Article
  @POST("articles/favorite/{articlecmsid}")
  Call<ResponseBody> postFavoriteArticleAPI(@Path("articlecmsid") String articleID);

  //  SAVED Video Article List
  @GET("ArticlesVideosSaved/Savedlist?page=1&pagesize=50")
  Call<ResponseBody> getSavedListAPI();

  //  SAVED Video Article List
  @GET("ArticlesVideosSaved/Savedlist?")
  Call<ResponseBody> getSavedListLazyLoadAPI(@Query("page") String page,
                                             @Query("pagesize") String pagesize);

  //  Calendar List
  @GET("Calendar/list?page=1&pagesize=50")
  Call<ResponseBody> getCalendarAPI();


  //  UNSAVED Video Article List
  @POST("ArticlesVideosSaved/mediaUnSaved?")
  Call<ResponseBody> getUnSavedListAPI(@Query("objectType") String mArticleID,
                                       @Query("id") String mEmojiID);

  // Article Category API
  @GET("articles/listbycategory?page=1&pagesize=50")
  Call<ResponseBody> getArticleCategoryAPI(@Query("categoryid") String articleID);

  @GET("articles/listbycategory?pagesize=50")
  Call<ResponseBody> getArticleCategoryLazyLoadAPI(@Query("page") String page,
                                                   @Query("categoryid") String articleID);

  //Media Saved API(Favourite)
  @POST("ArticlesVideosSaved/mediaSaved?")
  Call<ResponseBody> postArticleVideoSavedAPI(@Query("id") String mArticleID,
                                              @Query("objectType") String mEmojiID);

  // Videos Category API
  @GET("Videos/listbycategory?page=1&pagesize=50")
  Call<ResponseBody> getVideoCategoryAPI(@Query("categoryid") String videoID);

  // Videos Category Lazy Load API
  @GET("Videos/listbycategory?pagesize=50")
  Call<ResponseBody> getVideoCategoryLazyLoadAPI(@Query("page") String page,
                                                 @Query("categoryid") String videoID);

  @GET("videos/list?page=1&pagesize=50")
  Call<ResponseBody> getVideoListAPI();

  @GET("articles/listbyid?page=1&pagesize=50")
  Call<ResponseBody> getArticleWithIDAPI(@Query("articleid") int mArticleID);

  @GET("HotSpotHome/search?take=10&skip=0&")
  Call<ResponseBody> getSearchResultAPI(@Query("term") String mStrSearchText);

  @GET("Videos/tag?")
  Call<ResponseBody> getTagsList(@Query("tag") String mTagName);

  @GET("articles/getArticlesByTagAPI?")
  Call<ResponseBody> getArticleTagsList(@Query("tag") String mTagName);

  @GET("Videos/ListById?pagesize=100")
  Call<ResponseBody> getVideoListByID(@Query("page") int mPageCount,
                                      @Query("mediaid") String mMediaID,
                                      @Query("categoryid") String mCategoryID);


  //  Get Home List
  @GET("HotSpothome/list?page=1&pagesize=100")
  Call<ResponseBody> getHomeListAPI();

  //  Get FAQ List
  @GET
  Call<ResponseBody> getFAQListAPI(@Url String url);

  // GET LiveStreaming and Archived Videos List
  @GET("HotSpotLiveStreaming/LiveStreamingList")
  Call<ResponseBody> getLiveStreamingListAPI();*/

  // PUSH NOTIFICATIONS BASED API
/*
  @POST("HotSpotDevice/Set_DeviceDetails")
  Call<ResponseBody> setDeviceDetailsAPI(@Body SetDeviceDetailsRequestModel setDeviceDetailsRequestModel);

  @POST("HotSpotCategory/list")
  Call<ResponseBody> getCategoriesByDevice(@Body GetCategoriesByDeviceRequestModel getCategoriesByDeviceRequestModel);

  @POST("Category/Set_CategoryStatusByDevice")
  Call<ResponseBody> setCategoryStatusByDevice(@Body SetCategoryStatusByDeviceRequestModel setCategoryStatusByDeviceRequestModel);

  @POST("HotSpotCategory/Set_CategoryStatus")
  Call<ResponseBody> setCategoryStatus(@Body SetCategoryStatusRequestModel setCategoryStatusRequestModel);

  @POST("HotSpotDevice/Set_globalNotification")
  Call<ResponseBody> setGlobalNotification(@Body SetGlobalNotificationRequestModel setGlobalNotificationRequestModel);*/

}
