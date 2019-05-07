package com.hrfid.acs.service.api.locationchangeapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

import java.util.List;

class LocationChange {

    @SerializedName("status")
    @Expose
    private Status status;


    @SerializedName("response")
    @Expose
    private List<Response> response;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }
}
