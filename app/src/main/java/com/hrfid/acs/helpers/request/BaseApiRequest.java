package com.hrfid.acs.helpers.request;

import android.app.Activity;
import android.content.Context;

public class BaseApiRequest {

  public String apiName = "DefaultName";

  public Context mActivity;
  public int apiToCall = 0;
  public boolean showProgressDialog = true;

  BaseApiRequest() {
    super();
  }


  public BaseApiRequest(Activity cmgActivity, int cmgApiToCall, boolean cmgShowProgressDialog) {
    //no instance
    super();
    mActivity = cmgActivity;
    apiToCall = cmgApiToCall;
    showProgressDialog = cmgShowProgressDialog;

    switch (cmgApiToCall) {

      default:
        apiName = "BASEAPIREQUEST";
        break;
    }
  }
}
