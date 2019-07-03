
package com.hrfid.acs.helpers.serverResponses.models.GetKitDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKitDetailsResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("kitList")
    @Expose
    private List<KitList> kitList = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<KitList> getKitList() {
        return kitList;
    }

    public void setKitList(List<KitList> kitList) {
        this.kitList = kitList;
    }

}
