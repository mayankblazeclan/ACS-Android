package com.hrfid.acs.service.api.locationchangeapi;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LocationChangeService {

    public static String TAG = LocationChangeService.class.getSimpleName();
    private LocationChangeService.LocationChangeInterface mService;

    public void callRegisterLocationApi(String url, Map<String, Object> fields)
    {
        getObservable(url, fields).subscribe(getObserver());
    }
    public LocationChangeService(LocationChangeInterface mService) {
        this.mService = mService;
    }


    private Observable<LocationChange> getObservable(String url, Map<String, Object> fields) {
        return RestNoAuthClient.RestNoAuthClient(url)
                .create(LocationChangeAPI.class)
                .postLocationChangeApi(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<LocationChange> getObserver() {
        return new DisposableObserver<LocationChange>() {
            @Override
            public void onNext(LocationChange locationChange) {
                if (null != locationChange) {
                    if (Constants.STATUS_CODE_SUCCESS == locationChange.getStatus().getCODE()) {
                        for (int i = 0; i < locationChange.getResponse().size(); i++) {
                            mService.onLocationChangeResponse(locationChange.getResponse().get(i).isLocationUpdated());

                        }
                    } else {
                        mService.onLocationChangeError(0);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerLocal.error(TAG, e.getMessage());
                e.printStackTrace();
                mService.onLocationChangeError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public interface LocationChangeAPI {
        @FormUrlEncoded
        @POST("/api/device/changeLocation")
        Observable<LocationChange> postLocationChangeApi(@FieldMap Map<String, Object> fields);
    }

    public interface LocationChangeInterface {
        void onLocationChangeResponse(boolean isLocationChange);

        void onLocationChangeError(int errorCode);
    }
}
