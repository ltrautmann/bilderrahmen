package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.models.IgnoreTabelModel;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        table = new JTable(new IgnoreTabelModel((Client) jComboBox.getSelectedItem()));


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
        setLayout(new BorderLayout());
        jPanelCombobox.add(jComboBox);
        add(jPanelCombobox,BorderLayout.NORTH);
        jScrollPane.setViewportView(table);
        add(jScrollPane);
        table.getColumnModel().getColumn(1).setPreferredWidth(1);
    }


}
