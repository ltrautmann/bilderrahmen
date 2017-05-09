package com.sabel.bilderrahmen.Admin.interfaces;

import com.sabel.bilderrahmen.Admin.Client;

import javax.swing.table.AbstractTableModel;

/**
 * Created by robin on 09.05.17.
 */
public class IgnoreTabelModel extends AbstractTableModel {
    private Client client;

    public IgnoreTabelModel(Client c) {
        client = c;
        init();
    }

    private void init() {

    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return super.getColumnName(column);
    }
}
