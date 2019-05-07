
package com.hrfid.acs.service.model.registerblooddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterBloodData {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("result")
    @Expose
    private boolean result;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
