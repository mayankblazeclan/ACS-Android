package com.hrfid.acs.helpers.network;


import okhttp3.Headers;

/**
 * Created by pramodsarafdar on 4/7/16.
 */
public class ApiResponse<T> {

  public boolean isSucess = false;
  public String jsonResponse = null;
  public String errorMessageToDisplay = "";

  private Headers headers = null;


  public ApiResponse(boolean isSucess, String jsonRes, Headers cmgHeaders) {
    this.isSucess = isSucess;
    this.jsonResponse = jsonRes;
    this.headers = cmgHeaders;
  }


  public ApiResponse(boolean isSucess, String errormsg) {
    this.isSucess = isSucess;
    this.errorMessageToDisplay = errormsg;
  }


}
