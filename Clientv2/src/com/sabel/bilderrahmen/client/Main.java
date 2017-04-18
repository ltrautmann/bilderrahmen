package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.logger.Logger;
import com.sabel.bilderrahmen.client.windows.ConfigWindow;
import com.sabel.bilderrahmen.client.windows.InitWindow;
import com.sabel.bilderrahmen.client.windows.MainWindow;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Main {
    private static InitWindow initWindow;
    private static MainWindow mainWindow;
    private static ConfigWindow configWindow;
    private static List<Thread> threadList;

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
        threadList = new ArrayList<>();
        start();
    }

    private static void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.initWindow = new InitWindow();
            }
        });
    }

    public static void restart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                exit();
                Main.start();
            }
        });

    }

    private static synchronized void exit() {
        try {
            Logger.appendln("Interrupting Threads...", Logger.LOGTYPE_INFO);
            for (Thread t : threadList) {
                t.interrupt();
            }
            Logger.appendln("Bye!", Logger.LOGTYPE_INFO);
            Logger.dispose();
            Logger.logProgramExit("Waiting for threads to finish.", Logger.LOGTYPE_INFO);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Logger.logProgramExit("You are a wizard! You interrupted the program in the few Milliseconds it needs to exit!", Logger.LOGTYPE_FATAL);
        } finally {
            setInitWindow(null);
            setConfigWindow(null);
            setMainWindow(null);
        }
    }

    public static synchronized void quit() {
        Thread quit = new Thread(new Runnable() {
            @Override
            public void run() {
                exit();
                System.exit(0);
            }
        });
        quit.setName("QUIT");
        quit.start();
    }
}
