
package com.hrfid.acs.helpers.serverResponses.models.GetTSUParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabUse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("field_id")
    @Expose
    private int fieldId;
    @SerializedName("value")
    @Expose
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
