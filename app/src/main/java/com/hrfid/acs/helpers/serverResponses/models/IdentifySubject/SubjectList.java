
package com.hrfid.acs.helpers.serverResponses.models.IdentifySubject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("screen_id")
    @Expose
    private String screenId;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("study_id")
    @Expose
    private int studyId;
    @SerializedName("study_name")
    @Expose
    private String studyName;
    @SerializedName("gen_barcode_val")
    @Expose
    private String genBarcodeVal;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_mapped")
    @Expose
    private int isMapped;
    @SerializedName("is_approved")
    @Expose
    private Object isApproved;
    @SerializedName("is_approved_study")
    @Expose
    private Object isApprovedStudy;
    @SerializedName("study_title")
    @Expose
    private String studyTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getGenBarcodeVal() {
        return genBarcodeVal;
    }

    public void setGenBarcodeVal(String genBarcodeVal) {
        this.genBarcodeVal = genBarcodeVal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsMapped() {
        return isMapped;
    }

    public void setIsMapped(int isMapped) {
        this.isMapped = isMapped;
    }

    public Object getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Object isApproved) {
        this.isApproved = isApproved;
    }

    public Object getIsApprovedStudy() {
        return isApprovedStudy;
    }

    public void setIsApprovedStudy(Object isApprovedStudy) {
        this.isApprovedStudy = isApprovedStudy;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

}