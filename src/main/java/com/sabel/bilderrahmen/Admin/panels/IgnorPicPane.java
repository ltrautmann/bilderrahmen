package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.interfaces.Got_Pictures;
import com.sabel.bilderrahmen.Admin.models.IgnoreTabelModel;
import com.sabel.bilderrahmen.Admin.models.TimeTableModel;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by robin on 09.05.17.
 */
public class IgnorPicPane extends JPanel {
    private JTable table;
    private JScrollPane jScrollPane;
    private JPanel jPanelCombobox;
    private ComboBoxModel comboBoxModel;
    private JComboBox jComboBox;

    public IgnorPicPane() {
        initComponents();
        initEvents();
        build();
    }

    private void initComponents() {
        jScrollPane = new JScrollPane();
        jPanelCombobox = new JPanel();
        comboBoxModel = new DefaultComboBoxModel(ClientPool.getInstance().getClientArrayList().toArray());
        jComboBox = new JComboBox(comboBoxModel);
        jComboBox.setSelectedIndex(0);
        table = new JTable( new IgnoreTabelModel((Client) jComboBox.getSelectedItem()));


    }

    private void initEvents() {
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setModel(new IgnoreTabelModel((Client) jComboBox.getSelectedItem()));

            }
        });
    }

    private void build() {
        jPanelCombobox.add(jComboBox);
        add(jPanelCombobox);
        jScrollPane.setViewportView(table);
        add(jScrollPane);
        table.getColumnModel().getColumn(1).setPreferredWidth(1);
    }


}
