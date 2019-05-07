package com.hrfid.acs.service.api.specialtestingcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;

import java.util.List;

public class SpecialTestingCodeData {

    @SerializedName("status")
    @Expose
    public Status status;

    @SerializedName("isSuccess")
    @Expose
    public boolean isSuccess;

    @SerializedName("specialTestingData")
    @Expose
    public List<SpecialTestingData> specialTestingData;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<SpecialTestingData> getSpecialTestingData() {
        return specialTestingData;
    }

    public void setSpecialTestingData(List<SpecialTestingData> specialTestingData) {
        this.specialTestingData = specialTestingData;
    }
}
