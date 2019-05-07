package com.hrfid.acs.service.api.specialtestingcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialTestingData {
    @SerializedName("specialTestingCode")
    @Expose
    public String specialTestingCode;

    @SerializedName("specialTestingName")
    @Expose
    public String specialTestingName;

    public String getSpecialTestingCode() {
        return specialTestingCode;
    }

    public void setSpecialTestingCode(String specialTestingCode) {
        this.specialTestingCode = specialTestingCode;
    }

    public String getSpecialTestingName() {
        return specialTestingName;
    }

    public void setSpecialTestingName(String specialTestingName) {
        this.specialTestingName = specialTestingName;
    }
}
