package com.hrfid.acs.service.api.licensesapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDeviceIsRegisterResponse {

    @SerializedName("isDeviceRegistered")
    @Expose
    private boolean isDeviceRegistered;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;


    public boolean isDeviceRegistered() {
        return isDeviceRegistered;
    }

    public void setDeviceRegistered(boolean deviceRegistered) {
        isDeviceRegistered = deviceRegistered;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
