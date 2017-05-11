package com.sabel.bilderrahmen.Client.utils.usb;

import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Client.utils.image.SavedImage;

/**
 * Created by you shall not pass on 11.05.2017.
 */
public class USBImage extends SavedImage {

    public USBImage(String originalPath, Picture_Properties picture_properties) {
        super(originalPath, picture_properties);
    }

    public USBImage(String originalPath, String name, int displayTime) {
        super(originalPath, new Picture_Properties(displayTime, name));
    }
}
