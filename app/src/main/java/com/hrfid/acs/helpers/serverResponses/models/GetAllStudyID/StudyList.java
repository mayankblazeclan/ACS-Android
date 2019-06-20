
package com.hrfid.acs.helpers.serverResponses.models.GetAllStudyID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyList {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("isTrial")
    @Expose
    private String isTrial;
    @SerializedName("value")
    @Expose
    private int value;
    @SerializedName("studyId")
    @Expose
    private String studyId;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(String isTrial) {
        this.isTrial = isTrial;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

}
