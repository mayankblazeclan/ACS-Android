package com.hrfid.acs.service.api.devicenameapi;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.service.rest.RestNoAuthClient;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class DeviceNameService {

    private DeviceNameService.DeviceNameInterface mListener;

    public DeviceNameService(DeviceNameInterface mListener) {
        this.mListener = mListener;
    }

    public void postDeviceNameApi(String url, Map<String, Object> fields) {
        getObservable(url, fields).subscribe(getObserver());

    }

    private Observable<DeviceName> getObservable(String url, Map<String, Object> fields) {
        return RestNoAuthClient.RestNoAuthClient(url).create(DeviceNameAPI.class)
                .postDeviceName(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private DisposableObserver<DeviceName> getObserver() {
        return new DisposableObserver<DeviceName>() {

            @Override
            public void onNext(DeviceName deviceName) {


                if(null != deviceName) {
                    if (null != deviceName.getStatus() && deviceName.getStatus().getCODE() == 200) {
                        if (deviceName.getDeviceNameResponses().size() > 0) {
                            if (deviceName.getDeviceNameResponses().get(0).isDeviceRegistered()) {
                                mListener.onDeviceNameResponse(deviceName);
                            } else {
                                mListener.onDeviceNameError(Constants.ERROR_DEVICE_NOT_REGISTERED);
                            }
                        } else {
                            mListener.onDeviceNameError(0);
                        }
                    }else if(deviceName.getStatus().getCODE() == Constants.ERROR_DEVICE_ABRADE_REGISTERED_TO_DIFFERENT_ORG){
                        mListener.onDeviceNameError(Constants.ERROR_DEVICE_ABRADE_REGISTERED_TO_DIFFERENT_ORG);
                    }
                }

                else
                {
                    mListener.onDeviceNameError(0);
                }

            }

            @Override
            public void onError(Throwable e) {
                mListener.onDeviceNameError(0);
            }

            @Override
            public void onComplete() {

            }
        };

    }

    public interface DeviceNameAPI {
        @FormUrlEncoded
        @POST("/api/device/registerDevice")
            Observable<DeviceName> postDeviceName(@FieldMap Map<String, Object> fields);
    }

    public interface DeviceNameInterface {
        void onDeviceNameResponse(DeviceName deviceName);

        void onDeviceNameError(int errorCode);

    }
}
