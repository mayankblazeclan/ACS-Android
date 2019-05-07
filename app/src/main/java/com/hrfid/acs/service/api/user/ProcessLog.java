package com.hrfid.acs.service.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by francisdfernando on 01/08/2017.
 */

public class ProcessLog {

    @SerializedName("collection_id")
    @Expose
    private Integer collectionId;

    @SerializedName("location_id")
    @Expose
    private Integer locationId;

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("module")
    @Expose
    private int module;

    @SerializedName("event_type")
    @Expose
    private int eventType;

    @SerializedName("data_type")
    @Expose
    private int dataType;

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("date_created")
    @Expose
    private String dateCreated;

    @SerializedName("user_full_name")
    @Expose
    private String userFullName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }


    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }
}
