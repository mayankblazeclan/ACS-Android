package com.hrfid.acs.service.api.locationchangeapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Response {
    @SerializedName("isLocationUpdated")
    @Expose
    private boolean isLocationUpdated;

    public boolean isLocationUpdated() {
        return isLocationUpdated;
    }

    public void setLocationUpdated(boolean locationUpdated) {
        isLocationUpdated = locationUpdated;
    }
}
