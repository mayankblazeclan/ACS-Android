package com.hrfid.acs.service.api.licensesapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;
import java.util.List;

public class LicenseKeyData {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("response")
    @Expose
    private List<LicenseKeyResponse> response;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<LicenseKeyResponse> getResponse() {
        return response;
    }

    public void setResponse(List<LicenseKeyResponse> response) {
        this.response = response;
    }
}
