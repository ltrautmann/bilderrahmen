package com.sabel.bilderrahmen.Admin.models;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by robin on 09.05.17.
 */
public class IgnoreTabelModel extends AbstractTableModel {
    private Client client;
    private ArrayList<Picture_Properties> groupPictures;

    public IgnoreTabelModel(Client c) {
        client = c;
        init();
    }

    private void init() {
        groupPictures = new ArrayList<>();
        for (Group group : GroupPool.getInstance().getGroupByName(client.getGroups())) {
            for (Picture_Properties picture_properties : group.getPictureList()) {
                if (!groupPictures.contains(picture_properties)) {
                    groupPictures.add(picture_properties);
                }
            }

        }


    }

    @Override
    public int getRowCount() {
        return groupPictures.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return groupPictures.get(rowIndex);
            case 1:
                return client.getIgnoredPictures().contains(groupPictures.get(rowIndex));
                default:
                    return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
                default:
                    return null;
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue instanceof Boolean) {
            if (((Boolean) aValue).booleanValue()) {
                client.addIgnoredPicture(groupPictures.get(rowIndex));
            } else {

                client.removeIgnoredPicture(groupPictures.get(rowIndex));

            }
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Bilder aus Gruppen";
            case 1:
                return "Verborgen";
                default:
                    return null;
        }
    }
}
