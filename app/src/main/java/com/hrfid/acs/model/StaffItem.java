package com.hrfid.acs.model;

/**
 * Created by MS on 09/05/19.
 */
public class StaffItem {

    String tagName;
    int tagImage;

    public StaffItem(String tagName,int tagImage)
    {
        this.tagImage=tagImage;
        this.tagName=tagName;
    }
    public String getTagName()
    {
        return tagName;
    }
    public int getTagImage()
    {
        return tagImage;
    }
}
