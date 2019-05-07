package com.hrfid.acs.service.api.specialtestingcode;

import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class SpecialTestingCodeApi {
    private final String TAG = getClass().getSimpleName();
    SpecialTestingCodeApi.SpecialTestingCodeApiInterface mListener;

    public SpecialTestingCodeApi(SpecialTestingCodeApiInterface mListener) {
        this.mListener = mListener;
    }

    public void ApiCallSpecialTestingName(String url, String barcodeSpecialTesting)
    {
        getObservable(url,barcodeSpecialTesting).subscribe(getObserver());
    }
    private Observable<SpecialTestingCodeData> getObservable(String url, String barcodeSpecialtesting)
    {
        return RestNoAuthClient.RestNoAuthClient(url).create(SpecialTestingCodeAPI.class)
                .getSpecialTestingData(barcodeSpecialtesting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    private DisposableObserver<SpecialTestingCodeData> getObserver() {
        return new DisposableObserver<SpecialTestingCodeData>() {
            @Override
            public void onNext(SpecialTestingCodeData specialTestingCodeData) {
                mListener.onSpecialTestingCodeResponse(specialTestingCodeData);
            }

            @Override
            public void onError(Throwable e) {
                mListener.onSpecialTestingCodeApiError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public interface SpecialTestingCodeAPI {

        @GET("/api/blood/component/getSpecialTestingDetails/{type}")
        Observable<SpecialTestingCodeData> getSpecialTestingData(@Path("type") String barcodeSpeicalTesting);

    }

    public interface SpecialTestingCodeApiInterface {

        void onSpecialTestingCodeResponse(SpecialTestingCodeData specialTestingCodeData);

        void onSpecialTestingCodeApiError(int errorCode);
    }
}
