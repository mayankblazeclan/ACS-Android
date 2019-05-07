package com.hrfid.acs.service.model.componentdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

import java.util.List;

public class ComponentDetails {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("bloodComponent")
    @Expose
    private List<ComponentDetailsDatum> bloodComponent = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ComponentDetailsDatum> getBloodComponent() {
        return bloodComponent;
    }

    public void setBloodComponent(List<ComponentDetailsDatum> bloodComponent) {
        this.bloodComponent = bloodComponent;
    }
}
