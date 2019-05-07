package com.hrfid.acs.service.api.sendtolocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestBodySendToLocationDestination {
    @SerializedName("donation_id")
    @Expose
    private String donation_id;

    public String getDonation_id() {
        return donation_id;
    }

    public void setDonation_id(String donation_id) {
        this.donation_id = donation_id;
    }
}
