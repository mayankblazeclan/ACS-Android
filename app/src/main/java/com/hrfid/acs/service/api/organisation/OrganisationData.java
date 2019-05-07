package com.hrfid.acs.service.api.organisation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hrfid.acs.service.model.registerblooddata.Status;
import java.util.List;

public class OrganisationData {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("list")
    @Expose
    private List<OrgList> orgLists;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrgList> getOrgLists() {
        return orgLists;
    }

    public void setOrgLists(List<OrgList> orgLists) {
        this.orgLists = orgLists;
    }
}
