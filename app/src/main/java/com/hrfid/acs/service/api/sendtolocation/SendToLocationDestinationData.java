package com.hrfid.acs.service.api.sendtolocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

public class SendToLocationDestinationData {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("result")
    @Expose
    private boolean result;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
