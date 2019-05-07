package com.hrfid.acs.view.user_authentication;

//import com.healthrfid.blood.control.service.api.user.PostUserAuthentication;
//import com.healthrfid.blood.control.service.model.HealthCheckResponse;



import com.hrfid.acs.service.model.VerifyTagId;
import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//import retrofit2.http.GET;

//public class PostUserValidationCheck {
//}
//import com.healthrfid.blood.control.service.model.HealthCheckResponse;
//        import com.healthrfid.blood.control.service.rest.RestNoAuthClient;
//
//        import io.reactivex.Observable;
//        import io.reactivex.android.schedulers.AndroidSchedulers;
//        import io.reactivex.observers.DisposableObserver;
//        import io.reactivex.schedulers.Schedulers;
//        import retrofit2.http.GET;

/**
 * GET HEALTHCHECK API
 */
public class PostUserValidationCheck {
    private final String TAG = getClass().getSimpleName();

    public interface UserAuthenticationAPI {
        // TODO : Sample only
        @FormUrlEncoded
        @POST("/api/user/verifyTagId")
        Observable<VerifyTagId> postUserAuth(@Field("tagId") String tagData);
    }

    public interface UserAuthInterface {
        void userAuthError(int errorCode);
        void userAuthResponse(VerifyTagId verifyTagId);
    }

    private PostUserValidationCheck.UserAuthInterface mListener;

    public PostUserValidationCheck(PostUserValidationCheck.UserAuthInterface userAuthInterface) {
        this.mListener = userAuthInterface;
    }

    public void callAPI(String url, String tagData) {
        getObservable(url, tagData).subscribe(getObserver());
    }

    private Observable<VerifyTagId> getObservable(String url, String tagData){
        return RestNoAuthClient.RestNoAuthClient(url).create(PostUserValidationCheck.UserAuthenticationAPI.class)
                .postUserAuth(tagData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<VerifyTagId> getObserver() {
        return new DisposableObserver<VerifyTagId>() {
            @Override
            public void onNext(VerifyTagId verifyTagId) {
                mListener.userAuthResponse(verifyTagId);
            }

            @Override
            public void onError(Throwable e) {
                mListener.userAuthError(0);
            }

            @Override
            public void onComplete() {
                // Nothing
            }
        };
    }
}