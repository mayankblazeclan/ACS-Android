package com.hrfid.acs.service.api.userrole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;
import java.util.List;

public class UserRoleData {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("response")
    @Expose
    private List<UserRole> response;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<UserRole> getRoleList() {
        return response;
    }

    public void setRoleList(List<UserRole> roleList) {
        this.response = roleList;
    }
}
