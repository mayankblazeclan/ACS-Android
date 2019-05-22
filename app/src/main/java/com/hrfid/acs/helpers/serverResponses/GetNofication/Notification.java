
package com.hrfid.acs.helpers.serverResponses.GetNofication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("rfid")
    @Expose
    private String rfid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("is_read_nurse")
    @Expose
    private String isReadNurse;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;
    @SerializedName("is_read_lab_staff")
    @Expose
    private String isReadLabStaff;
    @SerializedName("is_read_senior_staff")
    @Expose
    private String isReadSeniorStaff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getIsReadNurse() {
        return isReadNurse;
    }

    public void setIsReadNurse(String isReadNurse) {
        this.isReadNurse = isReadNurse;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getIsReadLabStaff() {
        return isReadLabStaff;
    }

    public void setIsReadLabStaff(String isReadLabStaff) {
        this.isReadLabStaff = isReadLabStaff;
    }

    public String getIsReadSeniorStaff() {
        return isReadSeniorStaff;
    }

    public void setIsReadSeniorStaff(String isReadSeniorStaff) {
        this.isReadSeniorStaff = isReadSeniorStaff;
    }

}
