package com.hrfid.acs.service.api.licensesapi;

import android.content.Context;
import android.text.TextUtils;

import com.hrfid.acs.pref.SharedPrefsManager;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class LicenseKeyApi {

    private SharedPrefsManager spfManager;
    private Context mContext;
    private LicenseKeyApi.LicenseKeyAPIInterface mListener;

    public LicenseKeyApi(Context context, LicenseKeyAPIInterface mListener, SharedPrefsManager spfManager) {
        this.mContext = context;
        this.mListener = mListener;
        this.spfManager = spfManager;

    }

    public void callApi(String url, int orgId, String licenseKey) {
        getObservable(url, orgId, licenseKey).subscribe(getObserver());
    }

    public void apiIsRegisterDevice(String url, int orgID, String deviceId){
        getObservableIsDeviceRegister(url, orgID, deviceId).subscribe(getObserverIsDeviceRegister());
    }

    private Observable<LicenseKeyData> getObservable(String url, int orgId, String licenseKey) {
        return RestNoAuthClient.RestNoAuthClient(url)
                .create(LicenseKeyAPI.class)
                .postLicenseKey(orgId, licenseKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<LicenseKeyData> getObserver() {
        return new DisposableObserver<LicenseKeyData>() {
            @Override
            public void onNext(LicenseKeyData licenseKeyData) {
                if(null != licenseKeyData) {
                    mListener.onLicenseKeyResponse(licenseKeyData);
                }else
                {
                    mListener.onLicenseKeyError(0);
                }
            }

            @Override
            public void onError(Throwable e) {

                mListener.onLicenseKeyError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public interface LicenseKeyAPI {
        @FormUrlEncoded
        @POST("/api/device/verifyLicenseKey")
        Observable<LicenseKeyData> postLicenseKey(@Field("orgId") int orgId, @Field("licenseKey") String licenseKey);

//        Observable<LicenseKeyData> postLicenseKey(@Field("orgId") int orgId, @Field("licenseKey") String licenseKey)
    }

    public interface LicenseKeyAPIInterface {

        void onLicenseKeyError(int errorCode);

        void onLicenseKeyResponse(LicenseKeyData licenseKeyData);

        void onIsDeviceRegisteredError(int errorCode);

        void onIsDeviceRegisteredResponse(boolean response);
    }


    //For Check Is device is register or not

    public interface IsDeviceRegisteredAPI {
        @FormUrlEncoded
        @POST("/api/device/isDeviceRegistered")
        Observable<CheckDeviceIsRegister> postIsDeviceId(@Field("orgId") int orgId, @Field("deviceId") String deviceId);

//        Observable<LicenseKeyData> postLicenseKey(@Field("orgId") int orgId, @Field("licenseKey") String licenseKey)
    }


    private Observable<CheckDeviceIsRegister> getObservableIsDeviceRegister(String url, int orgId, String deviceId) {
        return RestNoAuthClient.RestNoAuthClient(url)
                .create(IsDeviceRegisteredAPI.class)
                .postIsDeviceId(orgId, deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<CheckDeviceIsRegister> getObserverIsDeviceRegister() {
        return new DisposableObserver<CheckDeviceIsRegister>() {
            @Override
            public void onNext(CheckDeviceIsRegister checkDeviceIsRegister) {

                boolean isDeviceRegistered = false;
                String deviceId = null;
                if(null != checkDeviceIsRegister)
                {
                    if(checkDeviceIsRegister.getResponseIsDeviceRegister().size()>0)
                    {
                        for (int i = 0; i < checkDeviceIsRegister.getResponseIsDeviceRegister().size(); i++) {
                            isDeviceRegistered=  checkDeviceIsRegister.getResponseIsDeviceRegister().get(i).isDeviceRegistered();
                            deviceId =   checkDeviceIsRegister.getResponseIsDeviceRegister().get(i).getDeviceId();
                            if(null !=spfManager) {
                                if (null != deviceId && !TextUtils.isEmpty(deviceId)) {
                                    spfManager.setDeviceId(mContext, deviceId);
                                }
                            }else
                            {
                                LoggerLocal.error("LicenseKeyApi", "Null spfManager");
                            }

                        }

                      mListener.onIsDeviceRegisteredResponse(isDeviceRegistered);
                    }else
                    {
                        mListener.onIsDeviceRegisteredError(0);
                    }
                }else
                {
                    mListener.onIsDeviceRegisteredError(0);
                }
            }

            @Override
            public void onError(Throwable e) {
            mListener.onIsDeviceRegisteredError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }



}
