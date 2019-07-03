
package com.hrfid.acs.helpers.serverResponses.models.GetKitDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("CODE")
    @Expose
    private int cODE;
    @SerializedName("MSG")
    @Expose
    private String mSG;
    @SerializedName("ERROR")
    @Expose
    private String eRROR;

    public int getCODE() {
        return cODE;
    }

    public void setCODE(int cODE) {
        this.cODE = cODE;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }

    public String getERROR() {
        return eRROR;
    }

    public void setERROR(String eRROR) {
        this.eRROR = eRROR;
    }

}
