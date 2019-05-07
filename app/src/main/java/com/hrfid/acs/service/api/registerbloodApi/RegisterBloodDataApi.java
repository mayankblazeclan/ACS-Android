package com.hrfid.acs.service.api.registerbloodApi;

import com.hrfid.acs.service.model.registerblooddata.RegisterBloodData;
import com.hrfid.acs.service.rest.RestNoAuthClient;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class RegisterBloodDataApi {
    private final String TAG = getClass().getSimpleName();

    public interface registerBloodData {
        // TODO : Sample only
        @FormUrlEncoded
        @POST("/api/blood/registerBloodData")
        Observable<RegisterBloodData> postRegisterBloodData(@Field("org_id") String org_id, @Field("blood_component_id") String blood_component_id, @Field("donation_id") String donation_id, @Field("group_code") String group_code, @Field("group_name") String group_name, @Field("expiry_date_time") String expiry_date_time, @Field("special_testing_code") String special_testing_code, @Field("user_id") String user_id, @Field("created_on") String created_on, @Field("component_code") String component_code, @Field("component_name") String component_name, @Field("is_registered") int is_registered);
    }

    public interface registerBloodInterface {
        void registerBloodError(int errorCode);
        void registerBloodResponse(RegisterBloodData registerBloodData);
    }

    private registerBloodInterface mListener;

    public RegisterBloodDataApi(registerBloodInterface registerbloodInterface) {
        this.mListener = registerbloodInterface;
    }

    public void callAPI(String url, String org_id , String blood_component_id, String donation_id, String group_code, String group_name, String expiry_date_time, String special_testing_code , String user_id , String created_on, String component_code, String component_name , int is_registered) {
        getObservable(url, org_id , blood_component_id , donation_id ,group_code,group_name,expiry_date_time,special_testing_code,user_id,created_on, component_code,component_name, is_registered).subscribe(getObserver());
    }

    private Observable<RegisterBloodData> getObservable(String url, String org_id, String blood_component_id, String donation_id, String group_code, String group_name, String expiry_date_time, String special_testing_code , String user_id , String created_on , String component_code, String component_name, int is_registered){
        return RestNoAuthClient.RestNoAuthClient(url).create(registerBloodData.class)
                .postRegisterBloodData(org_id , blood_component_id , donation_id ,group_code,group_name,expiry_date_time,special_testing_code,user_id,created_on, component_code, component_name, is_registered)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<RegisterBloodData> getObserver() {
        return new DisposableObserver<RegisterBloodData>() {
            @Override
            public void onNext(RegisterBloodData registerBloodData) {
                mListener.registerBloodResponse(registerBloodData);
            }

            @Override
            public void onError(Throwable e) {
                mListener.registerBloodError(0);
            }

            @Override
            public void onComplete() {
                // Nothing
            }
        };
    }
}
