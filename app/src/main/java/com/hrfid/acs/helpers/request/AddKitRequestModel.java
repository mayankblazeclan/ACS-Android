package com.hrfid.acs.helpers.request;

/**
 * Created by MS on 2019-06-07.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddKitRequestModel {

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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("genBarcode")
    @Expose
    private String genBarcode;
    @SerializedName("tagId")
    @Expose
    private String tagId;
    @SerializedName("visit")
    @Expose
    private String visit;
    @SerializedName("studyName")
    @Expose
    private String studyName;
    @SerializedName("studyId")
    @Expose
    private int studyId;
    @SerializedName("isTrial")
    @Expose
    private int isTrial;
    @SerializedName("additionalKit")
    @Expose
    private int additionalKit;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("local")
    @Expose
    private int local;
    @SerializedName("central")
    @Expose
    private int central;
    @SerializedName("aliquot")
    @Expose
    private int aliquot;
    @SerializedName("reqForm")
    @Expose
    private int reqForm;
    @SerializedName("scanDate")
    @Expose
    private String scanDate;
    @SerializedName("expDate")
    @Expose
    private String expDate;

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

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(int isTrial) {
        this.isTrial = isTrial;
    }

    public int getAdditionalKit() {
        return additionalKit;
    }

    public void setAdditionalKit(int additionalKit) {
        this.additionalKit = additionalKit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public int getCentral() {
        return central;
    }

    public void setCentral(int central) {
        this.central = central;
    }

    public int getAliquot() {
        return aliquot;
    }

    public void setAliquot(int aliquot) {
        this.aliquot = aliquot;
    }

    public int getReqForm() {
        return reqForm;
    }

    public void setReqForm(int reqForm) {
        this.reqForm = reqForm;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

}
