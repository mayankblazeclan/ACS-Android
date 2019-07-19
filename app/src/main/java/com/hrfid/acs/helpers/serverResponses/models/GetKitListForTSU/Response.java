
package com.hrfid.acs.helpers.serverResponses.models.GetKitListForTSU;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("kits")
    @Expose
    private List<Kit> kits = null;

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

}
