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
    public static void main(String[] args) {

        FileService.readClients();
        FileService.readGroups();

        FileService.readPictures();
        FtpService.getInstance().disconnect();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new MainWindow();

    }


}
