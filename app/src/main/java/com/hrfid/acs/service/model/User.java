
package com.hrfid.acs.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("user_level_id")
    @Expose
    private int userLevelId;
    @SerializedName("user_status")
    @Expose
    private int userStatus;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("password_reset_token")
    @Expose
    private String passwordResetToken;
    @SerializedName("password_reset_expires")
    @Expose
    private String passwordResetExpires;
    @SerializedName("login_attemps")
    @Expose
    private int loginAttemps;
    @SerializedName("tc_status")
    @Expose
    private int tcStatus;
    @SerializedName("created_by")
    @Expose
    private int createdBy;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("updated_by")
    @Expose
    private int updatedBy;
    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public int getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(int userLevelId) {
        this.userLevelId = userLevelId;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getPasswordResetExpires() {
        return passwordResetExpires;
    }

    public void setPasswordResetExpires(String passwordResetExpires) {
        this.passwordResetExpires = passwordResetExpires;
    }

    public int getLoginAttemps() {
        return loginAttemps;
    }

    public void setLoginAttemps(int loginAttemps) {
        this.loginAttemps = loginAttemps;
    }

    public int getTcStatus() {
        return tcStatus;
    }

    public void setTcStatus(int tcStatus) {
        this.tcStatus = tcStatus;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

}
