package com.hrfid.acs.view.barcode;

import android.graphics.Bitmap;

public class ReplicateModel {

    private String title;
    Bitmap barcodeBitmap;

    public ReplicateModel() {
    }

    public ReplicateModel(String title, Bitmap barcodeBitmap) {
        this.title = title;
        this.barcodeBitmap = barcodeBitmap;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Bitmap getBarcodeBitmap() {
        return barcodeBitmap;
    }

    public void setBarcodeBitmap(Bitmap barcodeBitmap) {
        this.barcodeBitmap = barcodeBitmap;
    }


}

