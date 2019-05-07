package com.hrfid.acs.service.api.devicenameapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;
import java.util.List;

public class DeviceName {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("response")
    @Expose
    private List<DeviceNameResponse> deviceNameResponses;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<DeviceNameResponse> getDeviceNameResponses() {
        return deviceNameResponses;
    }

    public void setDeviceNameResponses(List<DeviceNameResponse> deviceNameResponses) {
        this.deviceNameResponses = deviceNameResponses;
    }
}
