
package com.hrfid.acs.helpers.serverResponses.models.GetKitListForTSU;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("CODE")
    @Expose
    private int cODE;
    @SerializedName("MSG")
    @Expose
    private String mSG;

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

}
