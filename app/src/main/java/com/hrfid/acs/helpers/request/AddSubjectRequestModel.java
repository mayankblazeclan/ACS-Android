package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 2019-06-07.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSubjectRequestModel {

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
    @SerializedName("userRole")
    @Expose
    private String userRole;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("versionNumber")
    @Expose
    private String versionNumber;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("screenId")
    @Expose
    private String screenId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("genBarcode")
    @Expose
    private String genBarcode;
    @SerializedName("tagId")
    @Expose
    private String tagId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("studyId")
    @Expose
    private int studyId;
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGenBarcode() {
        return genBarcode;
    }

    public void setGenBarcode(String genBarcode) {
        this.genBarcode = genBarcode;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
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
