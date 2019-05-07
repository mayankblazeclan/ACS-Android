package com.hrfid.acs.service.api.user;


import com.hrfid.acs.service.model.HealthCheckResponse;
import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class PostUserAuthentication {
    private final String TAG = getClass().getSimpleName();

    public interface UserAuthenticationAPI {
        // TODO : Sample only
        @POST("/api/user/login")
        Observable<HealthCheckResponse> postUserAuth(@Body HealthCheckResponse healthCheckResponse);
    }

    public interface UserAuthInterface {
        void userAuthError(int errorCode);
        void userAuthResponse(HealthCheckResponse healthCheckResponse);
    }

    private UserAuthInterface mListener;

    public PostUserAuthentication(UserAuthInterface userAuthInterface) {
        this.mListener = userAuthInterface;
    }

    public void callAPI(String url, HealthCheckResponse healthCheckResponse) {
        getObservable(url, healthCheckResponse).subscribe(getObserver());
    }

    private Observable<HealthCheckResponse> getObservable(String url, HealthCheckResponse healthCheckResponse){
        return RestNoAuthClient.RestNoAuthClient(url).create(UserAuthenticationAPI.class)
                .postUserAuth(healthCheckResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<HealthCheckResponse> getObserver() {
        return new DisposableObserver<HealthCheckResponse>() {
            @Override
            public void onNext(HealthCheckResponse healthCheckResponse) {
                mListener.userAuthResponse(healthCheckResponse);
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
