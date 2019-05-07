package com.hrfid.acs.view.user_authentication;

import com.google.gson.annotations.SerializedName;

public class TagData {

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @SerializedName("tagId")
    private String tagId;


    public TagData(String tagId) {
        this.tagId = tagId;
    }
}
