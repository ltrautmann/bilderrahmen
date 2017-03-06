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

    public void setImage(Image i){
        picLabel.setIcon(new ImageIcon(i));
    }
}
