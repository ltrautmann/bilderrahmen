package com.sabel.bilderrahmen.Client.utils.panels;

import com.sabel.bilderrahmen.Client.utils.logger.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by CheaterLL on 15.04.2017.
 */
public class ImagePanel extends JPanel {
    private JLabel picture;

    public ImagePanel() {
        setBackground(Color.BLACK);
        picture = new JLabel();
        add(picture);
    }

    public void setImage(Image i) {
        if (i == null) {
            throw new NullPointerException("Image to be updated was null.");
        } else {
            Logger.appendln("Updating displayed image", Logger.LOGTYPE_INFO);
            picture.setIcon(new ImageIcon(i));
        }
    }
}
