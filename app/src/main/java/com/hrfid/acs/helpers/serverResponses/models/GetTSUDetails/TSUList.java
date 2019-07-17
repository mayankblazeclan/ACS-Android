
package com.hrfid.acs.helpers.serverResponses.models.GetTSUDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TSUList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("kitRecId")
    @Expose
    private int kitRecId;
    @SerializedName("study_id")
    @Expose
    private int studyId;
    @SerializedName("study_name")
    @Expose
    private String studyName;
    @SerializedName("study_title")
    @Expose
    private String studyTitle;
    @SerializedName("kit_id")
    @Expose
    private String kitId;
    @SerializedName("site_no")
    @Expose
    private String siteNo;
    @SerializedName("cohort_no")
    @Expose
    private String cohortNo;
    @SerializedName("primary_investigator")
    @Expose
    private String primaryInvestigator;
    @SerializedName("visit")
    @Expose
    private String visit;
    @SerializedName("timepoint")
    @Expose
    private String timepoint;
    @SerializedName("discard_tube_color")
    @Expose
    private String discardTubeColor;
    @SerializedName("discard_tube_volume")
    @Expose
    private String discardTubeVolume;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("collection_lable")
    @Expose
    private String collectionLable;
    @SerializedName("transport_lable")
    @Expose
    private String transportLable;
    @SerializedName("tube_color")
    @Expose
    private String tubeColor;
    @SerializedName("tube_volume")
    @Expose
    private String tubeVolume;
    @SerializedName("aliquot_color_tube")
    @Expose
    private String aliquotColorTube;
    @SerializedName("lab_use")
    @Expose
    private String labUse;
    @SerializedName("is_duplicate")
    @Expose
    private int isDuplicate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("aliquot_volume")
    @Expose
    private String aliquotVolume;
    @SerializedName("aliquot_ext_no")
    @Expose
    private String aliquotExtNo;
    @SerializedName("tube_type")
    @Expose
    private int tubeType;
    @SerializedName("centrifuge_prog")
    @Expose
    private String centrifugeProg;
    @SerializedName("is_archived")
    @Expose
    private int isArchived;



    @SerializedName("entry_date")
    @Expose
    private String entry_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKitRecId() {
        return kitRecId;
    }

    public void setKitRecId(int kitRecId) {
        this.kitRecId = kitRecId;
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

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getSiteNo() {
        return siteNo;
    }

    public void setSiteNo(String siteNo) {
        this.siteNo = siteNo;
    }

    public String getCohortNo() {
        return cohortNo;
    }

    public void setCohortNo(String cohortNo) {
        this.cohortNo = cohortNo;
    }

    public String getPrimaryInvestigator() {
        return primaryInvestigator;
    }

    public void setPrimaryInvestigator(String primaryInvestigator) {
        this.primaryInvestigator = primaryInvestigator;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(String timepoint) {
        this.timepoint = timepoint;
    }

    public String getDiscardTubeColor() {
        return discardTubeColor;
    }

    public void setDiscardTubeColor(String discardTubeColor) {
        this.discardTubeColor = discardTubeColor;
    }

    public String getDiscardTubeVolume() {
        return discardTubeVolume;
    }

    public void setDiscardTubeVolume(String discardTubeVolume) {
        this.discardTubeVolume = discardTubeVolume;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCollectionLable() {
        return collectionLable;
    }

    public void setCollectionLable(String collectionLable) {
        this.collectionLable = collectionLable;
    }

    public String getTransportLable() {
        return transportLable;
    }

    public void setTransportLable(String transportLable) {
        this.transportLable = transportLable;
    }

    public String getTubeColor() {
        return tubeColor;
    }

    public void setTubeColor(String tubeColor) {
        this.tubeColor = tubeColor;
    }

    public String getTubeVolume() {
        return tubeVolume;
    }

    public void setTubeVolume(String tubeVolume) {
        this.tubeVolume = tubeVolume;
    }

    public String getAliquotColorTube() {
        return aliquotColorTube;
    }

    public void setAliquotColorTube(String aliquotColorTube) {
        this.aliquotColorTube = aliquotColorTube;
    }

    public String getLabUse() {
        return labUse;
    }

    public void setLabUse(String labUse) {
        this.labUse = labUse;
    }

    public int getIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(int isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAliquotVolume() {
        return aliquotVolume;
    }

    public void setAliquotVolume(String aliquotVolume) {
        this.aliquotVolume = aliquotVolume;
    }

    public String getAliquotExtNo() {
        return aliquotExtNo;
    }

    public void setAliquotExtNo(String aliquotExtNo) {
        this.aliquotExtNo = aliquotExtNo;
    }

    public int getTubeType() {
        return tubeType;
    }

    public void setTubeType(int tubeType) {
        this.tubeType = tubeType;
    }

    public String getCentrifugeProg() {
        return centrifugeProg;
    }

    public void setCentrifugeProg(String centrifugeProg) {
        this.centrifugeProg = centrifugeProg;
    }

    public int getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(int isArchived) {
        this.isArchived = isArchived;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

}
