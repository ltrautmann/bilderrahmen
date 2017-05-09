package com.sabel.bilderrahmen.Admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by robin on 18.04.17.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Picture_Properties {
    @XmlElement
    private int presentationTime;
    @XmlElement
    private String name;
    public Picture_Properties(int presentationTime, String name) {
        this.presentationTime = presentationTime;
        this.name = name;
    }

    private Picture_Properties() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Picture_Properties that = (Picture_Properties) o;

//        if (presentationTime != that.presentationTime) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = presentationTime;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPresentationTime() {
        return presentationTime;
    }

    public void setPresentationTime(int presentationTime) {
        this.presentationTime = presentationTime;
    }

    @Override
    public String toString() {
        return
                name;
    }
}
