package com.hrfid.acs.service.api.userrole;


import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class UserRoleService {

    private final String TAG = getClass().getSimpleName();

    private  UserRoleService.UserRoleInterface mListener;

    public UserRoleService(UserRoleInterface mListener) {
        this.mListener = mListener;
    }

    public void ApiCallGetUserRole(String url, String tagId){
        getObservable(url,tagId).subscribe(getObserver());
    }

   private Observable<UserRoleData> getObservable(String url, String tagId)
   {
       return RestNoAuthClient.RestNoAuthClient(url)
               .create(UserRoleAPI.class)
               .getUserRole(tagId)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());

   }

private DisposableObserver<UserRoleData> getObserver(){
       return new DisposableObserver<UserRoleData>() {
           @Override
           public void onNext(UserRoleData userRoleData) {

               if(null != userRoleData)
               {
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
        @GET("/api/user/userRole/{tagid}")
        Observable<UserRoleData> getUserRole(@Path("tagid") String tagID);

    }
    public interface UserRoleInterface{
        void onUserRoleResponse(List<UserRole> userRole);
        void onError(int errorCode);
    }
}
