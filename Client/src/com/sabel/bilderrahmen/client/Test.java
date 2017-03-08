package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.Windows.ConfigWindow;
import com.sabel.bilderrahmen.client.Windows.InitWindow;
import com.sabel.bilderrahmen.client.Windows.MainWindow;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class Test {
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
        if (Test.mainWindow != null) {
            Test.mainWindow.dispose();
            Test.mainWindow = null;
        }
        Test.mainWindow = mainWindow;
    }

    public static void setInitWindow(InitWindow initWindow) {
        if (Test.initWindow != null) {
            Test.initWindow.dispose();
            Test.initWindow = null;
        }
        Test.initWindow = initWindow;
    }

    public static void setConfigWindow(ConfigWindow configWindow) {
        if (Test.configWindow != null) {
            Test.configWindow.dispose();
            Test.configWindow = null;
        }
        Test.configWindow = configWindow;
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
