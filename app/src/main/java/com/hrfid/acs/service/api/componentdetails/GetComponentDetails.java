package com.hrfid.acs.service.api.componentdetails;

import com.hrfid.acs.service.model.componentdetails.ComponentDetails;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GetComponentDetails {
    private final String TAG = getClass().getSimpleName();
    private GetComponentDetails.ComponentDetailsAPIInterface mListener;
    private String mURL;
    private ComponentDetails componentDetails;


    public GetComponentDetails(GetComponentDetails.ComponentDetailsAPIInterface listener) {
        this.mListener = listener;
    }

    public void callAPI(String url , String componentCode) {
        getObservable(url,componentCode).subscribe(getObserver());
    }


    private Observable<ComponentDetails> getObservable(String url, String componentCode){
        // return RestNoAuthClient.RestNoAuthClient(url+"/api/blood/verifyDonationId/"+"A520017123459"+"/").create(VerifyDonationIdAPI.class)

        return RestNoAuthClient.RestNoAuthClient(url).create(GetComponentDetails.ComponentDetailsAPI.class)
                .getComponentDetails(componentCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<ComponentDetails> getObserver() {
        return new DisposableObserver<ComponentDetails>() {
            @Override
            public void onNext(ComponentDetails componentDetails) {

                // Return
                mListener.ComponentDetailsResponse(componentDetails);
            }

            @Override
            public void onError(Throwable e) {
                // Error
                mListener.ComponentDetailsError(0);
            }

            @Override
            public void onComplete() {
            }
        };
    }


    public interface ComponentDetailsAPI {
        @GET("/api/blood/component/getComponentDetails/{type}")
        Observable<ComponentDetails> getComponentDetails(@Path("type") String componentCode);

    }


    public interface ComponentDetailsAPIInterface {
        void ComponentDetailsError(int errorCode);
        void ComponentDetailsResponse(ComponentDetails componentDetails);
    }

}
