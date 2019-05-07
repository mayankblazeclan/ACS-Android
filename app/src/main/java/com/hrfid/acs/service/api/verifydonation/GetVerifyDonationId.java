package com.hrfid.acs.service.api.verifydonation;


import com.hrfid.acs.service.model.verifydonation.VerifyDonationId;
import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GetVerifyDonationId {
    private final String TAG = getClass().getSimpleName();

    private GetVerifyDonationId.DonationIdAPIInterface mListener;
    private String mURL;
    private VerifyDonationId verifyDonationId;

    public GetVerifyDonationId(DonationIdAPIInterface listener) {
        this.mListener = listener;
    }

    public void callAPI(String url , String tagId) {
        getObservable(url,tagId).subscribe(getObserver());
    }

    private Observable<VerifyDonationId> getObservable(String url, String tagId){
       // return RestNoAuthClient.RestNoAuthClient(url+"/api/blood/verifyDonationId/"+"A520017123459"+"/").create(VerifyDonationIdAPI.class)

        return RestNoAuthClient.RestNoAuthClient(url).create(VerifyDonationIdAPI.class)
                .getDonationIdCheck(tagId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<VerifyDonationId> getObserver() {
        return new DisposableObserver<VerifyDonationId>() {
            @Override
            public void onNext(VerifyDonationId verifyDonationId) {
                // Return
                mListener.DonationIdResponse(verifyDonationId);
            }

            @Override
            public void onError(Throwable e) {
                // Error
                mListener.DonationIdError(0);
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public interface VerifyDonationIdAPI {
        @GET("/api/blood/verifyDonationId/{type}")
        Observable<VerifyDonationId> getDonationIdCheck(@Path("type") String tagId);
    }

    public interface DonationIdAPIInterface {
        void DonationIdError(int errorCode);
        void DonationIdResponse(VerifyDonationId verifyDonationId);
    }
}