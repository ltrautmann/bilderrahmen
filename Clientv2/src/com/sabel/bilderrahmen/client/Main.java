package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.windows.ConfigWindow;
import com.sabel.bilderrahmen.client.windows.InitWindow;
import com.sabel.bilderrahmen.client.windows.MainWindow;

import javax.swing.SwingUtilities;
import java.util.Set;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Main {
    private static InitWindow initWindow;
    private static MainWindow mainWindow;
    private static ConfigWindow configWindow;

    public static InitWindow getInitWindow() {
        return initWindow;
    }

    public static void setInitWindow(InitWindow initWindow) {
        if (Main.initWindow != null) {
            Main.initWindow.dispose();
            Main.initWindow = null;
        }
        Main.initWindow = initWindow;
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

    public static ConfigWindow getConfigWindow() {
        return configWindow;
    }

    public static void setConfigWindow(ConfigWindow configWindow) {
        if (Main.configWindow != null) {
            Main.configWindow.dispose();
            Main.configWindow = null;
        }
        Main.configWindow = configWindow;
    }

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.initWindow = new InitWindow();
            }
        });
    }

    public static void restart(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setInitWindow(null);
                setConfigWindow(null);
                setMainWindow(null);
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
                for (Thread t : threadArray) {
                    if (t != Thread.currentThread()) {
                        t.interrupt();
                    }
                }
                Main.start();
            }
        });

    }

    public static void quit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setInitWindow(null);
                setConfigWindow(null);
                setMainWindow(null);
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
                for (Thread t : threadArray) {
                    if (t != Thread.currentThread()) {
                        t.interrupt();
                    }
                }
                System.exit(0);
            }
        });
    }
}
