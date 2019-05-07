
package com.hrfid.acs.service.model.verifydonation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class    VerifyDonationId {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("isDonationIdValid")
    @Expose
    private boolean isDonationIdValid;
    @SerializedName("donationData")
    @Expose
    private List<DonationDatum> donationData = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isIsDonationIdValid() {
        return isDonationIdValid;
    }

    public void setIsDonationIdValid(boolean isDonationIdValid) {
        this.isDonationIdValid = isDonationIdValid;
    }

    public List<DonationDatum> getDonationData() {
        return donationData;
    }

    public void setDonationData(List<DonationDatum> donationData) {
        this.donationData = donationData;
    }

}