package com.hrfid.acs.service.api.userrole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRole {

    @SerializedName("isTagIdValid")
    @Expose
    private boolean isTagIdValid;

    @SerializedName("userRole")
    @Expose
    private String userRole;

    @SerializedName("userName")
    @Expose
    private String userName;

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getUserRole() {
        return userRole;
    }

    public boolean isTagIdValid() {
        return isTagIdValid;
    }

    public void setTagIdValid(boolean tagIdValid) {
        isTagIdValid = tagIdValid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
