package com.sabel.bilderrahmen.Client.utils.image;

import com.sabel.bilderrahmen.Admin.Picture_Properties;

/**
 * Created by you shall not pass on 08.05.2017.
 */
public class SavedImage {
    private String path;
    private Picture_Properties picture_properties;

    public SavedImage(String path, Picture_Properties picture_properties) {
        this.path = path;
        this.picture_properties = picture_properties;
    }

    public String getPath() {
        return path;
    }

    public Picture_Properties getPicture_properties() {
        return picture_properties;
    }

    public int getDisplayTime(){
        return getPicture_properties().getPresentationTime();
    }
}
