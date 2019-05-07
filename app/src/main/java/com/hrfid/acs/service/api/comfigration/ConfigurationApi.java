package com.hrfid.acs.service.api.comfigration;

import com.hrfid.acs.service.rest.RestNoAuthClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

public class ConfigurationApi {
    private final String TAG = getClass().getSimpleName();

    private ConfigurationApi.ConfigurationAPIInterface mListener;

    public ConfigurationApi(ConfigurationAPIInterface mListener) {
        this.mListener = mListener;
    }

    public void CallAPI(String controlPoint) {
            getObservable(controlPoint).subscribe(getObserver());
    }

    private Observable<ConfigurationDevice> getObservable(String url) {
        return RestNoAuthClient.RestNoAuthClient(url).create(ConfigureDeviceAPI.class)
                .getConfigurationDeviceAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private DisposableObserver<ConfigurationDevice> getObserver() {
        return new DisposableObserver<ConfigurationDevice>() {
            @Override
            public void onNext(ConfigurationDevice configurationDevice) {
                mListener.ConfigurationDeviceResponse(configurationDevice);
            }

            @Override
            public void onError(Throwable e) {

                mListener.ConfigurationDeviceError(0);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public interface ConfigureDeviceAPI {

        @GET("/api/healthcheck/")
        Observable<ConfigurationDevice> getConfigurationDeviceAPI();
//        Observable<RegisterBloodData> postRegisterBloodData(@Field("org_id") String org_id , @Field("blood_component_id") String blood_component_id, @Field("donation_id") String donation_id, @Field("group_code") String group_code, @Field("group_name") String group_name, @Field("expiry_date_time") String expiry_date_time, @Field("special_testing_code") String special_testing_code, @Field("user_id") String user_id, @Field("created_on") String created_on, @Field("component_code") String component_code, @Field("component_name") String component_name, @Field("is_registered") int is_registered);
    }

    public interface ConfigurationAPIInterface {
        void ConfigurationDeviceError(int errorCode);

        void ConfigurationDeviceResponse(ConfigurationDevice configurationDevice);
    }


}
