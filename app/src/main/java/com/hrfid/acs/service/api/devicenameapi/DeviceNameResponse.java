package com.hrfid.acs.service.api.devicenameapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class DeviceNameResponse {
    @SerializedName("deviceRegistered")
    @Expose
    private boolean deviceRegistered;

    public boolean isDeviceRegistered() {
        return deviceRegistered;
    }

    public void setDeviceRegistered(boolean deviceRegistered) {
        this.deviceRegistered = deviceRegistered;
    }
}
