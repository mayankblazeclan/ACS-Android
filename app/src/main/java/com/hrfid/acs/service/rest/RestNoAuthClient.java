package com.hrfid.acs.service.rest;

import com.hrfid.acs.data.Constants;
import com.hrfid.acs.service.rest.RestNoAuthClient;
import com.hrfid.acs.util.LoggerLocal;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestNoAuthClient {

    // Retrofit
    private static Retrofit retrofitNonAuth;
    // String
    private static String mProtocol = "";

    public static Retrofit RestNoAuthClient(String baseURL) {
        // For development change protocol
        if (baseURL.contains("8080")) {
            mProtocol = Constants.HTTP_PROTOCOL;
        } else {
            mProtocol = Constants.HTTP_PROTOCOL;
//            mProtocol = Constants.HTTPS_PROTOCOL;
        }

        // Setup OkHttpClient
        OkHttpClient.Builder builderNonAuth = new OkHttpClient().newBuilder();
        OkHttpClient clientNonAuth;

        // Setup Logger for Request/Response in background
        HttpLoggingInterceptor loggingNonAuth = new HttpLoggingInterceptor();
        loggingNonAuth.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Add the Logger in OkHttplClient
        // builderNonAuth.addInterceptor(loggingNonAuth);
        //builderNonAuth.addInterceptor(new StethoInterceptor());
        clientNonAuth = builderNonAuth.build();


        // Setup Retrofit Adapter for the client connection
        retrofitNonAuth = new Retrofit.Builder()
                .baseUrl(mProtocol + baseURL)
                .client(clientNonAuth)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        LoggerLocal.error("RestNoAuthClient","Base URL =="+retrofitNonAuth.baseUrl().toString());


        return retrofitNonAuth;
    }
}
