
package com.hrfid.acs.helpers.serverResponses.models.GetKitDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KitList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("study_id")
    @Expose
    private int studyId;
    @SerializedName("study_name")
    @Expose
    private String studyName;
    @SerializedName("study_title")
    @Expose
    private String studyTitle;
    @SerializedName("visit")
    @Expose
    private String visit;
    @SerializedName("is_trial")
    @Expose
    private int isTrial;
    @SerializedName("additional_kit")
    @Expose
    private int additionalKit;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("local")
    @Expose
    private int local;
    @SerializedName("central")
    @Expose
    private int central;
    @SerializedName("aliquot")
    @Expose
    private int aliquot;
    @SerializedName("requisition_form")
    @Expose
    private int requisitionForm;
    @SerializedName("scan_date")
    @Expose
    private String scanDate;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("gen_barcode")
    @Expose
    private String genBarcode;
    @SerializedName("is_cancelled")
    @Expose
    private int isCancelled;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("is_mapped")
    @Expose
    private int isMapped;
    @SerializedName("is_returned")
    @Expose
    private int isReturned;
    @SerializedName("return_reason")
    @Expose
    private Object returnReason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("kit_id")
    @Expose
    private String kitId;
    @SerializedName("ext_number")
    @Expose
    private String extNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public int getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(int isTrial) {
        this.isTrial = isTrial;
    }

    public int getAdditionalKit() {
        return additionalKit;
    }

    public void setAdditionalKit(int additionalKit) {
        this.additionalKit = additionalKit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public int getCentral() {
        return central;
    }

    public void setCentral(int central) {
        this.central = central;
    }

    public int getAliquot() {
        return aliquot;
    }

    public void setAliquot(int aliquot) {
        this.aliquot = aliquot;
    }

    public int getRequisitionForm() {
        return requisitionForm;
    }

    public void setRequisitionForm(int requisitionForm) {
        this.requisitionForm = requisitionForm;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getGenBarcode() {
        return genBarcode;
    }

    public void setGenBarcode(String genBarcode) {
        this.genBarcode = genBarcode;
    }

    public int getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(int isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public int getIsMapped() {
        return isMapped;
    }

    public void setIsMapped(int isMapped) {
        this.isMapped = isMapped;
    }

    public int getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(int isReturned) {
        this.isReturned = isReturned;
    }

    public Object getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Object returnReason) {
        this.returnReason = returnReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getExtNumber() {
        return extNumber;
    }

    public void setExtNumber(String extNumber) {
        this.extNumber = extNumber;
    }

}
