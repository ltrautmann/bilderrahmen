package com.sabel.bilderrahmen.client.utils.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public void setImage(Image i){
        picLabel.setIcon(new ImageIcon(i));
    }
}
