
package com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllStudyIdResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("studyList")
    @Expose
    private List<StudyList> studyList = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<StudyList> getStudyList() {
        return studyList;
    }

    public void setStudyList(List<StudyList> studyList) {
        this.studyList = studyList;
    }

}
