package com.sabel.bilderrahmen.Admin.models;

import com.sabel.bilderrahmen.Admin.services.Client_Create;
import com.sabel.bilderrahmen.Admin.services.FtpService;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by robin on 10.05.17.
 */
public class CreateClientTableModel extends AbstractTableModel {
    private ArrayList<Client_Create> newClients;

    public ArrayList<Client_Create> getNewClients() {
        return newClients;
    }

    public CreateClientTableModel() {
        newClients = new ArrayList<>();
        FTPFile[] folder = FtpService.getInstance().getFolder("files/clients");
        for (FTPFile ftpFile : folder) {

            if (!ftpFile.getName().equals(".") && !ftpFile.getName().equals("..") && !ftpFile.getName().contains(".php"))
                newClients.add(new Client_Create(ftpFile.getName()));
        }

    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return newClients.get(rowIndex).getName();
            case 1:
                return newClients.get(rowIndex).getMac();

            case 2:
                return newClients.get(rowIndex).createMe;
        }
        return true;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                newClients.get(rowIndex).setName(aValue.toString());
                break;
            case 2:
                newClients.get(rowIndex).createMe = (boolean) aValue;
                break;
        }
    }

    @Override
    public String getColumnName(int column) {

        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Mac";
            case 2:
                return "Create";

        }
        return null;
    }

    @Override
    public int getRowCount() {
        return newClients.size();
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }


}