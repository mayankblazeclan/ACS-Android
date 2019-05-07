package com.hrfid.acs.service.api.calculatesla;

import android.content.Context;


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

public class CalculateSlaService {

    private Context mContext;
    private CalculateSlaInterface mService;
    private String TAG ="CalculateSlaInterface";

    public CalculateSlaService(CalculateSlaInterface mService, Context mContext) {
        this.mService = mService;
        this.mContext = mContext;

    }

    public void postCalculateSlaApi(String url, Map<String, Object> fields) {
        getObservable(url, fields).subscribe(getObserver());

    }

    private Observable<CalculateSla> getObservable(String url, Map<String, Object> fields) {
        return RestNoAuthClient.RestNoAuthClient(url).create(CalculateSLAAPI.class)
                .postCalculateSla(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private DisposableObserver<CalculateSla> getObserver() {
        return new DisposableObserver<CalculateSla>() {

            @Override
            public void onNext(CalculateSla calculateSla) {

                if(null != calculateSla)
                {
                    if( calculateSla.getCalculateSlaData().size()>0) {
                        for (int i = 0; i < calculateSla.getCalculateSlaData().size(); i++) {
                            mService.onResponseCalculateSla(calculateSla.getCalculateSlaData().get(i));
                        }
                    }else
                    {
                        mService.onError(0);
                        LoggerLocal.error(TAG, "No data available");
                    }
                }else
                {
                    mService.onError(0);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mService.onError(0);
            }

            @Override
            public void onComplete() {

            }
        };

    }

    public interface CalculateSLAAPI {
        @FormUrlEncoded
        @POST("/api/blood/calculateSla/")
        Observable<CalculateSla> postCalculateSla(@FieldMap Map<String, Object> fields);

//        Observable<RegisterBloodData> postRegisterBloodData(@Field("org_id") String org_id , @Field("blood_component_id") String blood_component_id, @Field("donation_id") String donation_id, @Field("group_code") String group_code, @Field("group_name") String group_name, @Field("expiry_date_time") String expiry_date_time, @Field("special_testing_code") String special_testing_code, @Field("user_id") String user_id, @Field("created_on") String created_on, @Field("component_code") String component_code, @Field("component_name") String component_name, @Field("is_registered") int is_registered);
    }

    public interface CalculateSlaInterface {
        void onResponseCalculateSla(CalculateSlaData calculateSlaData);

        void onError(int errorCode);
    }


}
