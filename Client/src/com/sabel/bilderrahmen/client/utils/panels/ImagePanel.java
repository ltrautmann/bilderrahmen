package com.sabel.bilderrahmen.client.utils.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by you shall not pass on 20.02.2017.
 */
public class ImagePanel extends JPanel{
    private JLabel picLabel;

    public ImagePanel() {
        setBackground(Color.BLACK);
        picLabel = new JLabel();
        add(picLabel);
    }

    public void setImage(Image i) throws NullPointerException{
        if (i == null) {
            throw new NullPointerException("Image to be updated was null.");
        }else {
            System.out.println("Updating currently displayed image.");
            picLabel.setIcon(new ImageIcon(i));
        }
    }
}
