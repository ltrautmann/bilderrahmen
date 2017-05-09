package com.sabel.bilderrahmen.Client.utils.image;

/**
 * Created by you shall not pass on 08.05.2017.
 */
public class SavedImage {
    private String path;
    private int displayTime;

    public SavedImage(String path, int displayTime) {
        this.path = path;
        this.displayTime = displayTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(int displayTime) {
        this.displayTime = displayTime;
    }
}
