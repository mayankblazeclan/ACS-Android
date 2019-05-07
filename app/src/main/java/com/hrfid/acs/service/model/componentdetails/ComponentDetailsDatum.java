package com.hrfid.acs.service.model.componentdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComponentDetailsDatum {

    @SerializedName("componentId")
    @Expose
    private int componentId;

      @SerializedName("componentName")
    @Expose
    private String componentName;

      @SerializedName("componentCode")
    @Expose
    private String componentCode;

    @SerializedName("bloodCompnentCatId")
    @Expose
    private int bloodCompnentCatId;

    @SerializedName("bloodComponentCatName")
    @Expose
    private String bloodComponentCatName;

    @SerializedName("barcodeType")
    @Expose
    private int barcodeType;


    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public int getBloodCompnentCatId() {
        return bloodCompnentCatId;
    }

    public void setBloodCompnentCatId(int bloodCompnentCatId) {
        this.bloodCompnentCatId = bloodCompnentCatId;
    }

    public String getBloodComponentCatName() {
        return bloodComponentCatName;
    }

    public void setBloodComponentCatName(String bloodComponentCatName) {
        this.bloodComponentCatName = bloodComponentCatName;
    }

    public int getBarcodeType() {
        return barcodeType;
    }

    public void setBarcodeType(int barcodeType) {
        this.barcodeType = barcodeType;
    }
}
