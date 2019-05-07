package com.hrfid.acs.service.api.locationchangeapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationList {
    @SerializedName("locId")
    @Expose
    private int locId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("status")
    @Expose
    private int status;

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
