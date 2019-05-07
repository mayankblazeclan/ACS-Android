package com.hrfid.acs.service.api.calculatesla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateSlaData {

    @SerializedName("donationId")
    @Expose
    private String donationId;

    @SerializedName("componentCode")
    @Expose
    private String componentCode;

    @SerializedName("componentName")
    @Expose
    private String componentName;

    @SerializedName("bloodExpiryDate")
    @Expose
    private String bloodExpiryDate;

    @SerializedName("bloodGroupName")
    @Expose
    private String bloodGroupName;
    @SerializedName("bloodGroupCode")
    @Expose
    private String bloodGroupCode;

    @SerializedName("crossMatchExpiryDate")
    @Expose
    private String crossMatchExpiryDate;

    @SerializedName("specialTestingCode")
    @Expose
    private String specialTestingCode;

    @SerializedName("specialTestingName")
    @Expose
    private String specialTestingName;

    @SerializedName("slaType")
    @Expose
    private String slaType;

    @SerializedName("slaGood")
    @Expose
    private String slaGood;

    @SerializedName("slaWaring")
    @Expose
    private String slaWaring;

    @SerializedName("slaExpired")
    @Expose
    private boolean slaExpired;

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getDonationId() {
        return donationId;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getBloodExpiryDate() {
        return bloodExpiryDate;
    }

    public String getBloodGroupName() {
        return bloodGroupName;
    }

    public void setBloodGroupName(String bloodGroupName) {
        this.bloodGroupName = bloodGroupName;
    }

    public String getBloodGroupCode() {
        return bloodGroupCode;
    }

    public void setBloodGroupCode(String bloodGroupCode) {
        this.bloodGroupCode = bloodGroupCode;
    }

    public String getCrossMatchExpiryDate() {
        return crossMatchExpiryDate;
    }

    public String getSpecialTestingCode() {
        return specialTestingCode;
    }

    public String getSpecialTestingName() {
        return specialTestingName;
    }

    public String getSlaType() {
        return slaType;
    }

    public boolean isSlaExpired() {
        return slaExpired;
    }

    public String getSlaGood() {
        return slaGood;
    }

    public String getSlaWaring() {
        return slaWaring;
    }
}
