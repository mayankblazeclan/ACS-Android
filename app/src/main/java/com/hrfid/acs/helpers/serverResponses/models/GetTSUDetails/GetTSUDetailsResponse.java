
package com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTSUDetailsResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("TSUList")
    @Expose
    private List<TSUList> tSUList = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<TSUList> getTSUList() {
        return tSUList;
    }

    public void setTSUList(List<TSUList> tSUList) {
        this.tSUList = tSUList;
    }

}
