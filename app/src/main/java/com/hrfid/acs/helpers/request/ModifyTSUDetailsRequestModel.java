
package com.hrfid.acs.helpers.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyTSUDetailsRequestModel {

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
    @SerializedName("tagId")
    @Expose
    private String tagId;
    @SerializedName("visit")
    @Expose
    private String visit;
    @SerializedName("studyName")
    @Expose
    private String studyName;
    @SerializedName("kitId")
    @Expose
    private String kitId;
    @SerializedName("studyId")
    @Expose
    private int studyId;
    @SerializedName("kitRecId")
    @Expose
    private int kitRecId;
    @SerializedName("tubeType")
    @Expose
    private int tubeType;
    @SerializedName("isDuplicate")
    @Expose
    private int isDuplicate;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("siteNo")
    @Expose
    private String siteNo;
    @SerializedName("cohortNo")
    @Expose
    private String cohortNo;
    @SerializedName("pi")
    @Expose
    private String pi;
    @SerializedName("aliquotVol")
    @Expose
    private String aliquotVol;
    @SerializedName("timepoint")
    @Expose
    private String timepoint;
    @SerializedName("tubeVol")
    @Expose
    private String tubeVol;
    @SerializedName("tubeColor")
    @Expose
    private String tubeColor;
    @SerializedName("aliquotColor")
    @Expose
    private String aliquotColor;
    @SerializedName("aliquotExt")
    @Expose
    private String aliquotExt;
    @SerializedName("discardTubeColor")
    @Expose
    private String discardTubeColor;
    @SerializedName("discardTubeVolume")
    @Expose
    private String discardTubeVolume;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("collectionLable")
    @Expose
    private String collectionLable;
    @SerializedName("transportLable")
    @Expose
    private String transportLable;
    @SerializedName("centrifugeProg")
    @Expose
    private String centrifugeProg;
    @SerializedName("labUse")
    @Expose
    private String labUse;
    @SerializedName("id")
    @Expose
    private int id;

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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getKitRecId() {
        return kitRecId;
    }

    public void setKitRecId(int kitRecId) {
        this.kitRecId = kitRecId;
    }

    public int getTubeType() {
        return tubeType;
    }

    public void setTubeType(int tubeType) {
        this.tubeType = tubeType;
    }

    public int getIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(int isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getCohortNo() {
        return cohortNo;
    }

    public void setCohortNo(String cohortNo) {
        this.cohortNo = cohortNo;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getAliquotVol() {
        return aliquotVol;
    }

    public void setAliquotVol(String aliquotVol) {
        this.aliquotVol = aliquotVol;
    }

    public String getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(String timepoint) {
        this.timepoint = timepoint;
    }

    public String getTubeVol() {
        return tubeVol;
    }

    public void setTubeVol(String tubeVol) {
        this.tubeVol = tubeVol;
    }

    public String getTubeColor() {
        return tubeColor;
    }

    public void setTubeColor(String tubeColor) {
        this.tubeColor = tubeColor;
    }

    public String getAliquotColor() {
        return aliquotColor;
    }

    public void setAliquotColor(String aliquotColor) {
        this.aliquotColor = aliquotColor;
    }

    public String getAliquotExt() {
        return aliquotExt;
    }

    public void setAliquotExt(String aliquotExt) {
        this.aliquotExt = aliquotExt;
    }

    public String getDiscardTubeColor() {
        return discardTubeColor;
    }

    public void setDiscardTubeColor(String discardTubeColor) {
        this.discardTubeColor = discardTubeColor;
    }

    public String getDiscardTubeVolume() {
        return discardTubeVolume;
    }

    public void setDiscardTubeVolume(String discardTubeVolume) {
        this.discardTubeVolume = discardTubeVolume;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCollectionLable() {
        return collectionLable;
    }

    public void setCollectionLable(String collectionLable) {
        this.collectionLable = collectionLable;
    }

    public String getTransportLable() {
        return transportLable;
    }

    public void setTransportLable(String transportLable) {
        this.transportLable = transportLable;
    }

    public String getCentrifugeProg() {
        return centrifugeProg;
    }

    public void setCentrifugeProg(String centrifugeProg) {
        this.centrifugeProg = centrifugeProg;
    }

    public String getLabUse() {
        return labUse;
    }

    public void setLabUse(String labUse) {
        this.labUse = labUse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
