package com.hrfid.acs.helpers.serverResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/12/16.
 */
public class ErrorArrayResponse {

  @SerializedName("errors")
  @Expose
  public final List<String> errors = new ArrayList<>();

}
