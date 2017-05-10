package com.sabel.bilderrahmen.Admin;

import com.sabel.bilderrahmen.Admin.panels.AcceptClient;
import com.sabel.bilderrahmen.Admin.panels.MainWindow;
import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Admin.services.FtpService;


/**
 * Created by robin on 18.04.17.
 */
public class Main {
    public static void main(String[] args) {

        FileService.readClients();
        FileService.readGroups();

        FileService.readPictures();
        FtpService.getInstance().disconnect();

        new AcceptClient();
        new MainWindow();

    }


}
