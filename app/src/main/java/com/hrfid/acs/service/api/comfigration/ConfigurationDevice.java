package com.hrfid.acs.service.api.comfigration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;


import java.util.List;

public class ConfigurationDevice {
    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("server")
    @Expose
    private List<ConfigurationDeviceServer> server = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ConfigurationDeviceServer> getServer() {
        return server;
    }

    public void setServer(List<ConfigurationDeviceServer> server) {
        this.server = server;
    }
}
