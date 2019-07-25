
package com.hrfid.acs.helpers.serverResponses.models.GetTSUParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTSUParamsResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("response")
    @Expose
    private Response response;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}