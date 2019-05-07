package com.hrfid.acs.service.api.healthcheck;

import com.hrfid.acs.service.model.HealthCheckResponse;
import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

/**
 * GET HEALTHCHECK API
 */
public class GetHealthCheck {
    private final String TAG = getClass().getSimpleName();

    private HealthcheckInterface mListener;
    private String mURL;
    private HealthCheckResponse healthCheckResponse;

    public GetHealthCheck(HealthcheckInterface listener) {
        this.mListener = listener;
    }

    public void callAPI(String url) {
        getObservable(url).subscribe(getObserver());
    }

    private Observable<HealthCheckResponse> getObservable(String url){
        return RestNoAuthClient.RestNoAuthClient(url).create(HealhtCheckAPI.class)
                .getHealthCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<HealthCheckResponse> getObserver() {
        return new DisposableObserver<HealthCheckResponse>() {
            @Override
            public void onNext(HealthCheckResponse healthCheckResponse) {
                // Return
                mListener.healthcheckResponse(healthCheckResponse);
            }

            @Override
            public void onError(Throwable e) {
                // Error
                mListener.healthcheckError(0);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public interface HealhtCheckAPI {
        @GET("/api/healthcheck/")
        Observable<HealthCheckResponse> getHealthCheck();
    }

    public interface HealthcheckInterface {
        void healthcheckError(int errorCode);
        void healthcheckResponse(HealthCheckResponse healthCheckResponse);
    }
}
