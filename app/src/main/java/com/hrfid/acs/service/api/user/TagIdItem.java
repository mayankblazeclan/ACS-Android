package com.hrfid.acs.service.api.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by a.cabayao on 6/10/15.
 */
public class TagIdItem {
    @SerializedName("tag_id")
    final String tag_id;

    @SerializedName("tag_data")
    final String tag_data;

    public TagIdItem(String tag_id) {
        this.tag_id = tag_id;
        tag_data = "";
    }

    public TagIdItem(String tag_id, String tag_data) {
        this.tag_id = tag_id;
        this.tag_data = tag_data;
    }

}
