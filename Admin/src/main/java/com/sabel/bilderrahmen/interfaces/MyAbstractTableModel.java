package com.sabel.bilderrahmen.interfaces;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Robin on 08.05.2017.
 */
public class MyAbstractTableModel  extends AbstractTableModel{
    private Got_Pictures obj;
    private String[] titel = {"Name", "Dauer"};

    public MyAbstractTableModel(Got_Pictures obj) {
        this.obj = obj;
    }

    @Override
    public int getRowCount() {
        return obj.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:return obj.getPictureAt(rowIndex).getName();


            case 1: return obj.getPictureAt(rowIndex).getPresentationTime();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        obj.getPictureAt(rowIndex).setPresentationTime((Integer) aValue);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
        }
        return null;
    }


}
