package com.hrfid.acs.service.api.organisation;


import com.hrfid.acs.service.rest.RestNoAuthClient;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class OrganisationApi {
    private final String TAG = getClass().getSimpleName();

    private OrganisationApi.OrganisationAPIInterface mListener;


    public OrganisationApi(OrganisationAPIInterface mListener) {
        this.mListener = mListener;
    }

    public void callApi(String url)
    {
        getObservable(url).subscribe(gteObserver());

    }
    private Observable<OrganisationData> getObservable(String url){

         Map<String, Integer> data = new HashMap<>();
        data.put("status", 1);

        return RestNoAuthClient.RestNoAuthClient(url).create(OrganisationAPI.class)
                .getOrganisationListApi(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<OrganisationData> gteObserver()
    {
        return new DisposableObserver<OrganisationData>(){

            @Override
            public void onNext(OrganisationData organisationData) {
                mListener.onOrganisationResponse(organisationData);
            }

            @Override
            public void onError(Throwable e) {
                mListener.onOrganisationError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public interface OrganisationAPI{

        @GET("/api/organisation")
        Observable<OrganisationData> getOrganisationListApi(@QueryMap Map<String, Integer> value);

    }

    public interface OrganisationAPIInterface{

        void onOrganisationError(int errorCode);

        void onOrganisationResponse(OrganisationData organisationData);

    }
}
