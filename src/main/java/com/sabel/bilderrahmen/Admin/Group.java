package com.sabel.bilderrahmen.Admin;

import com.sabel.bilderrahmen.Admin.interfaces.Got_Pictures;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 18.04.17.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Group implements Got_Pictures {
    public Group(String groupName) {
        this.groupName = groupName;
        pictureList = new ArrayList<Picture_Properties>();
    }

    public Group() {
    }

    private String groupName;

    @XmlElementWrapper(name = "GroupPictures")
    @XmlElement(name = "Picture")
    private ArrayList<Picture_Properties> pictureList;

    public ArrayList<Picture_Properties> getPictureList() {
        return pictureList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addPicture(Picture_Properties picture_properties) {
        if (!pictureList.contains(picture_properties)) {
            pictureList.add(new Picture_Properties(picture_properties.getPresentationTime(),picture_properties.getName()));
        }
    }

    public void removePicture(Picture_Properties picture_propertie) {
        if (!pictureList.contains(picture_propertie)) {
            System.err.println("Bild nicht in gruppe enthalten:" + picture_propertie);
        }
        pictureList.remove(picture_propertie);
        if (pictureList.contains(picture_propertie)) {
            System.err.println("Löschen von " + picture_propertie + " fehlgeschlagen");
        }
    }

    public void setPictureList(ArrayList<Picture_Properties> pictureList) {
        this.pictureList = pictureList;
    }

    public Picture_Properties getPictureByID(int pictureIndex) {
        return pictureList.get(pictureIndex);
    }

    public Picture_Properties getPictureByName(String name) {

        for (Picture_Properties p : pictureList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        System.err.println("Bild nicht gefunden");
        return null;
    }

    @Override
    public String toString() {
        return groupName;
    }

    public void addPicture(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                addPicture((Picture_Properties) o);

            }
            else {
                System.err.println("Its not a Picture its a " + o);
            }
        }

    }

    public void removePicture(List selectedValuesList) {
        for (Object o : selectedValuesList) {
            if (o instanceof Picture_Properties) {
                removePicture((Picture_Properties) o);

            }
            else {
                System.err.println("Its not a picture its a " + o);
            }
        }

    }

    @Override
    public Picture_Properties getPictureAt(int index) {
        return pictureList.get(index);
    }


    @Override
    public int size() {
        return pictureList.size();
    }
}
