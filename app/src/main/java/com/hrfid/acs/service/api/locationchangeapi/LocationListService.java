package com.hrfid.acs.service.api.locationchangeapi;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class LocationListService {

    private static String TAG = LocationListService.class.getSimpleName();

    LocationListService.LocationListInterface mService;

    public LocationListService(LocationListInterface mService) {
        this.mService = mService;
    }

    public void getLocationListApi(String url, Map<String, Object> fields)
    {
        getObservable(url, fields).subscribe(getObserver());
    }

    private Observable<LocationListResponse> getObservable(String url, Map<String, Object> fields)
    {
        return RestNoAuthClient.RestNoAuthClient(url)
                .create(LocationListApi.class)
                .getLocationListApi(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<LocationListResponse> getObserver()
    {
        return new DisposableObserver<LocationListResponse>() {
            @Override
            public void onNext(LocationListResponse locationList) {
                if(locationList.getStatus().getCODE() == Constants.STATUS_CODE_SUCCESS){
                    if(null != locationList) {
                        mService.onLocationListResponse(locationList);
                    }else
                    {
                        mService.onLocationListError(0);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mService.onLocationListError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }
    public interface LocationListApi
    {
        @GET("/api/location/list")
        Observable<LocationListResponse> getLocationListApi(@QueryMap Map<String, Object> fields);
    }

    public interface LocationListInterface
    {
        void onLocationListResponse(LocationListResponse locationList);
        void onLocationListError(int errorCode);
    }
}
