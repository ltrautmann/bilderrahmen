package com.sabel.bilderrahmen.Admin;

import com.sabel.bilderrahmen.Admin.panels.AcceptClient;
import com.sabel.bilderrahmen.Admin.panels.MainWindow;
import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Admin.services.FtpService;

import javax.swing.*;


/**
 * Created by robin on 18.04.17.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        FtpService.getInstance("bilderrahmen@bilderrahmen.cheaterll.de","Kennwort0","ftp.strato.de");
        FileService.readClients();
        FileService.readGroups();

        FileService.readPictures();
        FtpService.getInstance().disconnect();



            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        new MainWindow();

    }


}
