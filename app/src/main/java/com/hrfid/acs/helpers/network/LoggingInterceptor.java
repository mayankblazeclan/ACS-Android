package com.hrfid.acs.helpers.network;

import android.util.Log;

import com.hrfid.acs.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author MS
 * @source https://gist.github.com/erickok/e371a9e0b9e702ed441d
 */
class LoggingInterceptor implements Interceptor {

  private static final String LOG_TAG = "LoggingInterceptor";

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    long t1 = System.nanoTime();


    Logger.logError(String
        .format("--> Sending request %s on %s%n%s", request.url(), chain.connection(),
            request.headers()));


    Buffer requestBuffer = new Buffer();
    if (request.body() != null) {
      request.body().writeTo(requestBuffer);
           Log.d(LOG_TAG, requestBuffer.readUtf8());
    }
    Response response = null;
    try {
      response = chain.proceed(request);

      long t2 = System.nanoTime();
      Logger.logError("Response for %s in %.1fms%n%s" + response.request().url());
      Logger.logError("Response in TIME " + (t2 - t1) / 1e6d);
//      Log.e("Response for %s in %.1fms%n%s",response.request().url(),(t2 - t1) / 1e6d,request.headers());

      MediaType contentType = null;
      contentType = response.body().contentType();
      String content = null;
      content = response.body().string();
      ResponseBody wrappedBody = null;
      wrappedBody = ResponseBody.create(contentType, content);
      return response.newBuilder().body(wrappedBody).build();
    } catch (Exception e) {
      Logger.logError("ERROR ..." + e.getMessage());
    }
    return response;
  }

}