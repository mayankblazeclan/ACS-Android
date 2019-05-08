package com.hrfid.acs.helpers.network;

import android.util.Base64;


import com.hrfid.acs.util.AppConstants;
import com.hrfid.acs.util.Logger;
import com.hrfid.acs.util.Utilities;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Akshay Kalpe on 16/03/17.
 */

class AuthenticationInterceptor implements Interceptor {

  private String authorizationValue;

  @Override
  public Response intercept(Chain chain) throws IOException {
//    String authorizationValue = encodeCredentialsForBasicAuthorization();

//    String fields = "gempakfaq" + ":" + "gempakfaq";
    try {

      String userName = Utilities
          .encrypt(AppConstants.GEMPAK_RANDOM_DATA, AppConstants.IV, AppConstants.TEXT_GEMPAK);
      String password = Utilities
              .encrypt(AppConstants.GEMPAK_RANDOM_DATA, AppConstants.IV, AppConstants.TEXT_GEMPAK_P);

//      Logger.logError(" userName Encryption " + userName);

      authorizationValue =
          Utilities.decrypt(AppConstants.GEMPAK_RANDOM_DATA, AppConstants.IV, userName) + ":" + ""
              + Utilities
              .decrypt(AppConstants.GEMPAK_RANDOM_DATA, AppConstants.IV, password);
//      Logger.logError(" authorizationValue Decryption " + authorizationValue);

    } catch (Exception e) {
      Logger.logError(e.getMessage());
    }

    String authorizationValue1 =
        "Basic " + Base64.encodeToString(authorizationValue.getBytes(), Base64.NO_WRAP);
//    Logger.logError(" authorizationValue1 Decryption " + authorizationValue1);

    Request original = chain.request();
    Request request = original.newBuilder()
        .header("Authorization", authorizationValue1)
//        .header("Accept", "application/json")
        .method(original.method(), original.body())
        .build();
    return chain.proceed(request);
  }

}
