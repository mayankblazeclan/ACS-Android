
package com.hrfid.acs.service.model.verifydonation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationDatum {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("org_id")
    @Expose
    private int orgId;

    @SerializedName("blood_component_id")
    @Expose
    private int bloodComponentId;

    @SerializedName("donation_id")
    @Expose
    private String donationId;

    @SerializedName("group_code")
    @Expose
    private String groupCode;

    @SerializedName("group_name")
    @Expose
    private String groupName;

    @SerializedName("expiry_date_time")
    @Expose
    private String expiryDateTime;

    @SerializedName("special_testing_code")
    @Expose
    private String specialTestingCode;

    @SerializedName("special_testing_name")
    @Expose
    private String specialTestingName;

    @SerializedName("blood_status")
    @Expose
    private Object bloodStatus;

    @SerializedName("created_by")
    @Expose
    private Object createdBy;

    @SerializedName("created_on")
    @Expose
    private String createdOn;

    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;

    @SerializedName("updated_on")
    @Expose
    private String updatedOn;

    @SerializedName("is_registered")
    @Expose
    private int isRegistered;

    @SerializedName("current_location")
    @Expose
    private Object currentLocation;

    @SerializedName("destination_location")
    @Expose
    private Object destinationLocation;

    @SerializedName("product_status")
    @Expose
    private Object productStatus;


    @SerializedName("component_code")
    @Expose
    private Object componentCode;


    @SerializedName("component_name")
    @Expose
    private Object componentName;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getBloodComponentId() {
        return bloodComponentId;
    }

    public void setBloodComponentId(int bloodComponentId) {
        this.bloodComponentId = bloodComponentId;
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(String expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public String getSpecialTestingCode() {
        return specialTestingCode;
    }

    public void setSpecialTestingCode(String specialTestingCode) {
        this.specialTestingCode = specialTestingCode;
    }

    public String getSpecialTestingName() {
        return specialTestingName;
    }

    public void setSpecialTestingName(String specialTestingName) {
        this.specialTestingName = specialTestingName;
    }

    public Object getBloodStatus() {
        return bloodStatus;
    }

    public void setBloodStatus(Object bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public Object getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Object currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Object getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(Object destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Object getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Object productStatus) {
        this.productStatus = productStatus;
    }


    public Object getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(Object componentCode) {
        this.componentCode = componentCode;
    }

    public Object getComponentName() {
        return componentName;
    }

    public void setComponentName(Object componentName) {
        this.componentName = componentName;
    }
}