package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.interfaces.Got_Pictures;
import com.sabel.bilderrahmen.Admin.interfaces.TimeTableModel;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by robin on 09.05.17.
 */
public class IgnorPicPane extends JPanel {
    private TimeTableModel model;
    private JTable table;
    private JScrollPane jScrollPane;
    private JPanel jPanelCombobox;
    private ComboBoxModel comboBoxModel;
    private JComboBox jComboBox;
    private GruppenBilderSammlung gruppenBilderSammlung;

    public IgnorPicPane() {
        initComponents();
    }

    private void initComponents() {
        jScrollPane = new JScrollPane();
        comboBoxModel = new DefaultComboBoxModel(ClientPool.getInstance().getClientArrayList().toArray());
        jComboBox = new JComboBox(comboBoxModel);
        jComboBox.setSelectedIndex(0);

    }

    private class GruppenBilderSammlung implements Got_Pictures {
        private Client client;
        private ArrayList<Picture_Properties> gruppenbilder;

        public GruppenBilderSammlung(Client c) {
            client = c;
            gruppenbilder = new ArrayList<Picture_Properties>();
            for (Group group : GroupPool.getInstance().getGroupByName(c.getGroups())) {
                for (Picture_Properties picture_properties : group.getPictureList()) {
                    if (!gruppenbilder.contains(picture_properties)) {
                        gruppenbilder.add(picture_properties);
                    }
                }

            }


        }

        @Override
        public Picture_Properties getPictureAt(int index) {
            return gruppenbilder.get(index);
        }

        @Override
        public int size() {
            return gruppenbilder.size();
        }
    }
}
