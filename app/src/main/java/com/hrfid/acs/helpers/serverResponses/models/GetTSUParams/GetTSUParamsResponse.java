
package com.hrfid.acs.helpers.serverResponses.models.GetTSUParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTSUParamsResponse {

    @SerializedName("status")
    @Expose
    private com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.Status status;
    @SerializedName("response")
    @Expose
    private com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.Response response;

    public com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public com.hrfid.acs.helpers.serverResponses.models.GetTSUParams.Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
