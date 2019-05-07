package com.hrfid.acs.service.api.locationchangeapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

import java.util.List;

public class LocationListResponse {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("locList")
    @Expose
    private List<LocationList> locList;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<LocationList> getLocList() {
        return locList;
    }

    public void setLocList(List<LocationList> locList) {
        this.locList = locList;
    }
}
