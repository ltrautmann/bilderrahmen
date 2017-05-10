package com.sabel.bilderrahmen.Client.utils.image;

import com.sabel.bilderrahmen.Admin.Picture_Properties;

/**
 * Created by you shall not pass on 08.05.2017.
 */
public class SavedImage {
    private String originalPath;

    private String resizedPath;
    private Picture_Properties picture_properties;

    public SavedImage(String originalPath, Picture_Properties picture_properties) {
        this.originalPath = originalPath;
        this.picture_properties = picture_properties;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public String getResizedPath() {
        return resizedPath;
    }

    public void setResizedPath(String resizedPath) {
        this.resizedPath = resizedPath;
    }

    public Picture_Properties getPicture_properties() {
        return picture_properties;
    }

    public int getDisplayTime() {
        return getPicture_properties().getPresentationTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavedImage that = (SavedImage) o;

        if (getOriginalPath() != null ? !getOriginalPath().equals(that.getOriginalPath()) : that.getOriginalPath() != null)
            return false;
        if (getResizedPath() != null ? !getResizedPath().equals(that.getResizedPath()) : that.getResizedPath() != null)
            return false;
        return getPicture_properties() != null ? getPicture_properties().equals(that.getPicture_properties()) : that.getPicture_properties() == null;
    }

    @Override
    public int hashCode() {
        int result = getOriginalPath() != null ? getOriginalPath().hashCode() : 0;
        result = 31 * result + (getResizedPath() != null ? getResizedPath().hashCode() : 0);
        result = 31 * result + (getPicture_properties() != null ? getPicture_properties().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SavedImage{" +
                "originalPath='" + originalPath + '\'' +
                ", resizedPath='" + resizedPath + '\'' +
                ", picture_properties=" + picture_properties +
                '}';
    }
}
