package com.hrfid.acs.service.api.calculatesla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

import java.util.List;

public class CalculateSla {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("isValid")
    @Expose
    private boolean isValid;

    @SerializedName("response")
    @Expose
    private List<CalculateSlaData> calculateSlaData;


    public Status getStatus() {
        return status;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<CalculateSlaData> getCalculateSlaData() {
        return calculateSlaData;
    }
}
