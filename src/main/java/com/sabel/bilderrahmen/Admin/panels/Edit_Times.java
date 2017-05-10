package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.interfaces.Got_Pictures;
import com.sabel.bilderrahmen.Admin.models.TimeTableModel;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Robin on 08.05.2017.
 */
public class Edit_Times extends JPanel {
    public final static int MOD_CLIENT = 1;
    public final static int MOD_Gruppe = 2;
    public JComboBox jComboBox;
    private JPanel jPanel;
    private ComboBoxModel comboBoxModel;
    private int modus;
    private JScrollPane jScrollPane;
    private JTable table;
    private TimeTableModel model;

    public Edit_Times(int modi) {
        modus = modi;
        jPanel = new JPanel();
        jComboBox = new JComboBox();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        switch (modus) {
            case 1:
                comboBoxModel = new DefaultComboBoxModel(ClientPool.getInstance().getClientArrayList().toArray());
                break;
            case 2:
                comboBoxModel = new DefaultComboBoxModel(GroupPool.getInstance().getGroupArrayList().toArray());
                break;
        }
        jComboBox.setModel(comboBoxModel);
        jComboBox.validate();
        jScrollPane = new JScrollPane();
        model = new TimeTableModel((Got_Pictures) jComboBox.getSelectedItem());
        table = new JTable(model);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = new TimeTableModel((Got_Pictures) jComboBox.getSelectedItem());
                table.setModel(model);

            }
        });
        jPanel.add(jComboBox);
        add(jPanel);
        add(jScrollPane);
        jScrollPane.setViewportView(table);
    }

}
