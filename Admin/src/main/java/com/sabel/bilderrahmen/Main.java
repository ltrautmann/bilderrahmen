package com.sabel.bilderrahmen;

import com.sabel.bilderrahmen.panels.MainWindow;
import com.sabel.bilderrahmen.resources.ClientPool;
import com.sabel.bilderrahmen.resources.GroupPool;
import com.sabel.bilderrahmen.resources.PicturePool;
import com.sabel.bilderrahmen.services.FileService;
import com.sabel.bilderrahmen.services.FtpService;


/**
 * Created by robin on 18.04.17.
 */
public class Main {
    public static void main(String[] args) {

        FileService.readClients();
        FileService.readGroups();
        FileService.readPictures();
        FtpService.getInstance().disconnect();

        new MainWindow();

    }



}
