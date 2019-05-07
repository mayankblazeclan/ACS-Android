
package com.hrfid.acs.service.model.verifydonation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("CODE")
    @Expose
    private int CODE;
    @SerializedName("MSG")
    @Expose
    private String MSG;

    public int getCODE() {
        return CODE;
    }

    public String getMSG() {
        return MSG;
    }
}