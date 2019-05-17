package com.hrfid.acs.helpers.network;



import com.hrfid.acs.helpers.interfaces.APIService;
import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author MS
 */
class ApiRouter {


  private static ApiRouter instance;
  private APIService service;

  private ApiRouter() {
        /* IGNORED */
  }

  static ApiRouter get() {
    if (instance == null) {
      instance = new ApiRouter();
    }
    return instance;
  }

  APIService getRetrofitService() {
    try {
      if (Objects.equals(AppConstants.API_TO_HIT, AppConstants.API_ASTRO)) {

//      ASTRO API
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(new LoggingInterceptor())
            .addInterceptor(new HeaderAddingInterceptor())
            .build();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.ENDPOINT_URL_ASTRO)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
        service = retrofit.create(APIService.class);

      } else if (Objects.equals(AppConstants.API_TO_HIT, AppConstants.API_KENTICO)) {

        //KENTICO API
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .addNetworkInterceptor(new LoggingInterceptor())
            .addInterceptor(new HeaderAddingInterceptor())
            .build();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
        service = retrofit.create(APIService.class);
      } else if (Objects.equals(AppConstants.API_TO_HIT, AppConstants.API_ASTRO_FAQ)) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(new LoggingInterceptor())
            //.addInterceptor(new HeaderAddingInterceptor())
            .addInterceptor(new AuthenticationInterceptor())
            .build();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConstants.ENDPOINT_URL_ASTRO_FAQ)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
        service = retrofit.create(APIService.class);
      }else if (Objects.equals(AppConstants.API_TO_HIT, AppConstants.API_ASTRO_WEBVIEW_CHECKER)) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(new LoggingInterceptor())
                //.addInterceptor(new HeaderAddingInterceptor())
                .addInterceptor(new AuthenticationForWebviewInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.ENDPOINT_URL_ASTRO_WEBVIEW_CHECKER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(APIService.class);
      }
      else {
      }
      return service;
    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }
    return null;
  }
}
