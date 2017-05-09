package com.sabel.bilderrahmen.panels;

import com.sabel.bilderrahmen.Picture_Properties;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Created by robin on 05.05.17.
 */
public class ModPictureTime extends JPanel {
    private JLabel name;
    private JFormattedTextField numberfield;
    private NumberFormat format;
    private NumberFormatter formatter;
    private Picture_Properties properties;

    private ModPictureTime() {
    }

    public ModPictureTime(Picture_Properties picture_properties) {

        initComponents();
        properties = picture_properties;
        numberfield.setValue(picture_properties.getPresentationTime());
        name = new JLabel(picture_properties.getName());
        add(name);
        add(numberfield);
        setLayout(new FlowLayout(FlowLayout.RIGHT));


    }

    private void initComponents() {

        format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        numberfield = new JFormattedTextField(formatter);
        numberfield.setColumns(5);
        numberfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (0 < Integer.parseInt(numberfield.getValue().toString())) {
                    properties.setPresentationTime(Integer.parseInt(numberfield.getValue().toString()));

                }
            }
        });

    }


}
