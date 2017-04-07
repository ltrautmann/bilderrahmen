package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.windows.ConfigWindow;
import com.sabel.bilderrahmen.client.windows.InitWindow;
import com.sabel.bilderrahmen.client.windows.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Main {
    private static InitWindow initWindow;
    private static MainWindow mainWindow;
    private static ConfigWindow configWindow;
    public static List<Thread> threadList;

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

    private static void start() {
        threadList = new ArrayList<>();
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
                setInitWindow(null);
                setConfigWindow(null);
                setMainWindow(null);
                for (Thread t : Main.threadList) {
                    if (t != Thread.currentThread()) {
                        t.interrupt();
                    }
                }
                Main.start();
            }
        });

    }

    public static boolean confirmQuit(JFrame parentComponent) {
        int b = JOptionPane.showConfirmDialog(parentComponent, "Möchten sie wirklich beenden?", "Bilderrahmen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (b == JOptionPane.OK_OPTION) {
            return true;
        }
        return false;
    }

    public static void quit() {
        Thread quit = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": Closing Threads and exiting.");
                for (Thread t : threadList) {
                    System.out.println("Interrupting Thread: " + t.getName());
                    t.interrupt();
                }
                setInitWindow(null);
                setConfigWindow(null);
                setMainWindow(null);
                try {
                    System.out.println("Waiting for threads to finish");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("This should not have happened.");
                    e.printStackTrace();
                }
                System.out.println("Bye");
                System.exit(0);
            }
        });
        quit.setName("QUIT");
        quit.start();
    }
}
