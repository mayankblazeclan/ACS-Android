package com.hrfid.acs.service.api.licensesapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;
import java.util.List;

public class CheckDeviceIsRegister {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("response")
    @Expose
    private List<CheckDeviceIsRegisterResponse> response;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<CheckDeviceIsRegisterResponse> getResponseIsDeviceRegister() {
        return response;
    }

    public void setResponse(List<CheckDeviceIsRegisterResponse> response) {
        this.response = response;
    }
}
