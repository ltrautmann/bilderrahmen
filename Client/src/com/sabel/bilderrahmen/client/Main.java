package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.Windows.ConfigWindow;
import com.sabel.bilderrahmen.client.Windows.InitWindow;
import com.sabel.bilderrahmen.client.Main;
import com.sabel.bilderrahmen.client.Windows.MainWindow;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class Main {
    private static InitWindow initWindow;
    private static MainWindow mainWindow;
    private static ConfigWindow configWindow;

    public static void main(String[] args) {
        initWindow = new InitWindow();
    }

    public static InitWindow getInitWindow() {
        return initWindow;
    }

    public static ConfigWindow getConfigWindow() {
        return configWindow;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static void setMainWindow(MainWindow mainWindow) {
        if (Main.mainWindow != null) {
            Main.mainWindow.dispose();
            Main.mainWindow = null;
        }
        Main.mainWindow = mainWindow;
    }

    public static void setInitWindow(InitWindow initWindow) {
        if (Main.initWindow != null) {
            Main.initWindow.dispose();
            Main.initWindow = null;
        }
        Main.initWindow = initWindow;
    }

    public static void setConfigWindow(ConfigWindow configWindow) {
        if (Main.configWindow != null) {
            Main.configWindow.dispose();
            Main.configWindow = null;
        }
        Main.configWindow = configWindow;
    }

    /*
    public static void restart(){
        initWindow.setVisible(false);
        mainWindow.setVisible(false);
        initWindow.dispose();
        mainWindow.dispose();
        initWindow = new InitWindow();
    }*/
}
