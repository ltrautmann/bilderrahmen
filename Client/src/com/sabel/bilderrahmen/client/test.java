package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.Windows.ConfigWindow;
import com.sabel.bilderrahmen.client.utils.Windows.InitWindow;
import com.sabel.bilderrahmen.client.utils.Windows.MainWindow;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class test {
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
        test.mainWindow = mainWindow;
    }

    public static void setConfigWindow(ConfigWindow configWindow) {
        test.configWindow = configWindow;
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
