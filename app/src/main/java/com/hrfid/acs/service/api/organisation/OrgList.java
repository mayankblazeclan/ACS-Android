package com.hrfid.acs.service.api.organisation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrgList {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
