
package com.hrfid.acs.helpers.serverResponses.models.GetTSUParams;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("kits")
    @Expose
    private List<Kit> kits = null;
    @SerializedName("PI")
    @Expose
    private List<Pus> pI = null;
    @SerializedName("discardTubeColor")
    @Expose
    private List<DiscardTubeColor> discardTubeColor = null;
    @SerializedName("testName")
    @Expose
    private List<TestName> testName = null;
    @SerializedName("collectionLable")
    @Expose
    private List<CollectionLable> collectionLable = null;
    @SerializedName("transportLable")
    @Expose
    private List<TransportLable> transportLable = null;
    @SerializedName("tubeColor")
    @Expose
    private List<TubeColor> tubeColor = null;
    @SerializedName("labUse")
    @Expose
    private List<LabUse> labUse = null;
    @SerializedName("aliquotTubeColor")
    @Expose
    private List<AliquotTubeColor> aliquotTubeColor = null;

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

    public List<Pus> getPI() {
        return pI;
    }

    public void setPI(List<Pus> pI) {
        this.pI = pI;
    }

    public List<DiscardTubeColor> getDiscardTubeColor() {
        return discardTubeColor;
    }

    public void setDiscardTubeColor(List<DiscardTubeColor> discardTubeColor) {
        this.discardTubeColor = discardTubeColor;
    }

    public List<TestName> getTestName() {
        return testName;
    }

    public void setTestName(List<TestName> testName) {
        this.testName = testName;
    }

    public List<CollectionLable> getCollectionLable() {
        return collectionLable;
    }

    public void setCollectionLable(List<CollectionLable> collectionLable) {
        this.collectionLable = collectionLable;
    }

    public List<TransportLable> getTransportLable() {
        return transportLable;
    }

    public void setTransportLable(List<TransportLable> transportLable) {
        this.transportLable = transportLable;
    }

    public List<TubeColor> getTubeColor() {
        return tubeColor;
    }

    public void setTubeColor(List<TubeColor> tubeColor) {
        this.tubeColor = tubeColor;
    }

    public List<LabUse> getLabUse() {
        return labUse;
    }

    public void setLabUse(List<LabUse> labUse) {
        this.labUse = labUse;
    }

    public List<AliquotTubeColor> getAliquotTubeColor() {
        return aliquotTubeColor;
    }

    public void setAliquotTubeColor(List<AliquotTubeColor> aliquotTubeColor) {
        this.aliquotTubeColor = aliquotTubeColor;
    }

}
