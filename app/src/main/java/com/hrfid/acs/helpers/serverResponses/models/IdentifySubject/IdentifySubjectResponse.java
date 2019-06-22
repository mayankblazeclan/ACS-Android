
package com.hrfid.acs.helpers.serverResponses.models.IdentifySubject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentifySubjectResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("subjectList")
    @Expose
    private List<SubjectList> subjectList = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<SubjectList> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<SubjectList> subjectList) {
        this.subjectList = subjectList;
    }

}
