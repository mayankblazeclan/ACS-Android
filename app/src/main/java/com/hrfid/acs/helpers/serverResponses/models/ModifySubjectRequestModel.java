package com.hrfid.acs.helpers.serverResponses.models;

/**
 * Created by MS on 2019-06-20.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifySubjectRequestModel {

    @SerializedName("appName")
    @Expose
    private String appName;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("deviceNumber")
    @Expose
    private String deviceNumber;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("tagId")
    @Expose
    private String tagId;
    @SerializedName("userRole")
    @Expose
    private String userRole;
    @SerializedName("versionNumber")
    @Expose
    private String versionNumber;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("isMapped")
    @Expose
    private int isMapped;
    @SerializedName("studyId")
    @Expose
    private String studyId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("studyName")
    @Expose
    private String studyName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("isApproved")
    @Expose
    private String isApproved;
    @SerializedName("initials")
    @Expose
    private String initials;
    @SerializedName("antigenStatus")
    @Expose
    private String antigenStatus;
    @SerializedName("PKSubStudy")
    @Expose
    private String pKSubStudy;
    @SerializedName("Leuka")
    @Expose
    private String leuka;
    @SerializedName("genomic")
    @Expose
    private String genomic;
    @SerializedName("FResearch")
    @Expose
    private String fResearch;
    @SerializedName("randNum")
    @Expose
    private String randNum;
    @SerializedName("isOptional")
    @Expose
    private int isOptional;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getIsMapped() {
        return isMapped;
    }

    public void setIsMapped(int isMapped) {
        this.isMapped = isMapped;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getAntigenStatus() {
        return antigenStatus;
    }

    public void setAntigenStatus(String antigenStatus) {
        this.antigenStatus = antigenStatus;
    }

    public String getPKSubStudy() {
        return pKSubStudy;
    }

    public void setPKSubStudy(String pKSubStudy) {
        this.pKSubStudy = pKSubStudy;
    }

    public String getLeuka() {
        return leuka;
    }

    public void setLeuka(String leuka) {
        this.leuka = leuka;
    }

    public String getGenomic() {
        return genomic;
    }

    public void setGenomic(String genomic) {
        this.genomic = genomic;
    }

    public String getFResearch() {
        return fResearch;
    }

    public void setFResearch(String fResearch) {
        this.fResearch = fResearch;
    }

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }

    public int getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(int isOptional) {
        this.isOptional = isOptional;
    }
}
