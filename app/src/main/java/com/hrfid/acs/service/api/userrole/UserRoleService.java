package com.hrfid.acs.service.api.userrole;


import com.google.gson.Gson;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class UserRoleService {

    private final String TAG = getClass().getSimpleName();

    private  UserRoleService.UserRoleInterface mListener;

    public UserRoleService(UserRoleInterface mListener) {
        this.mListener = mListener;
    }

    public void ApiCallGetUserRole(String url, LoginRequestModel loginRequestModel){
        getObservable(url, loginRequestModel).subscribe(getObserver());
    }

   private Observable<UserRoleData> getObservable(String url, LoginRequestModel loginRequestModel)
   {
       return RestNoAuthClient.RestNoAuthClient(url)
               .create(UserRoleAPI.class)
               .getUserRole(loginRequestModel)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());

   }

private DisposableObserver<UserRoleData> getObserver(){
       return new DisposableObserver<UserRoleData>() {
           @Override
           public void onNext(UserRoleData userRoleData) {

               if(null != userRoleData)
               {

                   LoggerLocal.error(TAG, "in userRoleData message ="+ new Gson().toJson(userRoleData));
                   if(userRoleData.getRoleList().size()>0){
                       mListener.onUserRoleResponse(userRoleData.getRoleList());
                   }else
                   {
                       mListener.onError(0);
                   }
               }else
               {
                   mListener.onError(0);
               }
           }

           @Override
           public void onError(Throwable e) {
               LoggerLocal.error(TAG, "in onError message ="+e.getMessage());
               e.printStackTrace();
               mListener.onError(0);
           }

           @Override
           public void onComplete() {

           }
       };
}

    public interface UserRoleAPI{
      /*  @GET("/api/user/userRole/{type}")
        Observable<UserRoleData> getUserRole(@Query("type") String tagID);
*/
        //@GET("/api/user/userRole/{tagid}")
        //Observable<UserRoleData> getUserRole(@Query("type") String tagID);

        @POST("/acs/api/user/userRole/")
        Observable<UserRoleData> getUserRole(@Body LoginRequestModel loginRequestModel);

    }
    public interface UserRoleInterface{
        void onUserRoleResponse(List<UserRole> userRole);
        void onError(int errorCode);
    }
}
