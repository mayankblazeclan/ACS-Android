
package com.hrfid.acs.helpers.serverResponses.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifyScheduleRequestModel {

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("isTrial")
    @Expose
    private int isTrial;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(int isTrial) {
        this.isTrial = isTrial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
