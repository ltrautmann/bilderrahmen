package com.sabel.bilderrahmen.Admin.resources;

import com.sabel.bilderrahmen.Admin.Picture_Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by robin on 18.04.17.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PicturePool {

    private static PicturePool instance = null;
    private ArrayList<Picture_Properties> pictureList;

    private PicturePool() {
        this.pictureList = new ArrayList<Picture_Properties>();
    }

    public static PicturePool getInstance() {
        if (instance == null) {
            instance = new PicturePool();
        }
        return instance;
    }

    public ArrayList<Picture_Properties> getPictureList() {
        return pictureList;
    }

    public void setPictureList(ArrayList<Picture_Properties> pictureList) {
        this.pictureList = pictureList;
    }

    public boolean addPicture(Picture_Properties picture_properties) {
        if (pictureList.contains(picture_properties)) {
            return false;
        }
        pictureList.add(picture_properties);
        return pictureList.contains(picture_properties);
    }

    public boolean addPicture(String picture_properties_name) {
        if (!picture_properties_name.isEmpty()) {
            Picture_Properties picture_properties = new Picture_Properties(10, picture_properties_name);
            if (pictureList.contains(picture_properties)) {
                return false;
            }
            pictureList.add(picture_properties);
            return pictureList.contains(picture_properties);
        }
        return false;
    }
}
