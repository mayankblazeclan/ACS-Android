package com.hrfid.acs.helpers.network;


import android.text.TextUtils;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Akshay Kalpe on 4/11/16.
 */
class HeaderAddingInterceptor implements Interceptor {

  @Override
  public Response intercept(Chain chain) throws IOException {
    String strUserID = "", strTemp = "";
    //strTemp = HotspotApplicationClass.get()
      //  .getUserID();
    if (strTemp != null) {
      strUserID = strTemp;
    }
    Request original = chain.request();
    Request request;

    if (TextUtils.isEmpty(strUserID)) {

      request = original.newBuilder()
//        .header("Accept", "application/json")
//        .header("Content-Type", "text/json")
//                    //QA site uuid

          .header("x-astroid", strUserID)  //AppConstants.USER_ID   "9B6716D95245BDC9DD1C4938CA2701"
//              .header("x-astroid", "9B6716D95245BDC9DD1C4938CA2701")  //AppConstants.USER_ID   "9B6716D95245BDC9DD1C4938CA2701"
          .method(original.method(), original.body())
          .build();

    } else {

      request = original.newBuilder()
//        .header("Accept", "application/json")
//        .header("Content-Type", "text/json")
//                    //QA site uuid
          .header("x-astroid", strUserID)  //AppConstants.USER_ID   "9B6716D95245BDC9DD1C4938CA2701"
          .method(original.method(), original.body())
          .build();

    }
    return chain.proceed(request);
  }
}