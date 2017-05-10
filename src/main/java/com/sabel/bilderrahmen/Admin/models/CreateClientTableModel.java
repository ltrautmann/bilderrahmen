package com.sabel.bilderrahmen.Admin.models;

import com.sabel.bilderrahmen.Admin.services.FtpService;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 10.05.17.
 */
public class CreateClientTableModel extends AbstractTableModel {
    private List<String> files;

    public CreateClientTableModel() {
        files = new ArrayList<>();
        FTPFile[] folder = FtpService.getInstance().getFolder("files/clients");
        for (FTPFile ftpFile : folder) {
            files.add(ftpFile.getName());

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
                return files.get(rowIndex+2).toString().substring(0, files.get(rowIndex+2).toString().lastIndexOf("-") - 1);
            case 1:
                return files.get(rowIndex+2).toString().substring( files.get(rowIndex+2).toString().lastIndexOf("-") + 1,files.get(rowIndex+2).toString().length()-1);

            case 2:return true;
        }
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
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
        return files.size()-2;
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }
}
