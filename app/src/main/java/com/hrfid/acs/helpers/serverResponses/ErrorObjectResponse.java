package com.hrfid.acs.helpers.serverResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ErrorObjectResponse {

  @SerializedName("status")
  @Expose
  public String status;
  @SerializedName("errors")
  @Expose
  public Errors errors;

  public class Errors {

    @SerializedName("full_messages")
    @Expose
    public final List<String> fullMessages = new ArrayList<>();
    @SerializedName("email")
    @Expose
    public List<String> email = new ArrayList<>();

  }

}


