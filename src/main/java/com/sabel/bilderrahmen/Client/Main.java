package com.sabel.bilderrahmen.Client;

import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.helper.MouseMover;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.usb.USBService;
import com.sabel.bilderrahmen.Client.windows.ConfigWindow;
import com.sabel.bilderrahmen.Client.windows.InitWindow;
import com.sabel.bilderrahmen.Client.windows.MainWindow;

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

    public static void registerThread(Thread thread) {
        threadList.add(thread);
    }

    public static void main(String[] args) {
        threadList = new ArrayList<>();
        if (args != null && args.length > 0) {
            String s = args[0];
            if (s.equals("-h") || s.equals("--help") || s.equals("/?") || s.equals("/h") || s.equals("/help")) {
                printHelp();
                System.exit(0);
            } else if (s.equals("-t")) {
                USBService.test();
                System.exit(0);
            } else {
                Config.passArgs(args);
            }
        }
        Main.start();
        Thread mouse = new Thread(new MouseMover());
        mouse.setName("MOUSEMOVER");
        mouse.start();
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
        Thread restart = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.exit();
                Main.start();
            }
        });
        restart.setName("RESTARTING");
        restart.start();
    }

    public static synchronized void quit() {
        Thread quit = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.exit();
                System.exit(0);
            }
        });
        quit.setName("QUITTING");
        quit.start();
    }

    private static synchronized void exit() {
        try {
            Logger.appendln("Interrupting Threads...", Logger.LOGTYPE_INFO);
            for (Thread t : threadList) {
                Logger.appendln("Interrupting Thread " + t.getName(), Logger.LOGTYPE_INFO);
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

    private static void printHelp() {
        System.out.print(
                "NAME\n" +
                        "\t\tbilderrahmen\n" +
                        "\n" +
                        "SYNOPSIS\n" +
                        "\t\tbilderrahmen [-h|--help|/h|/help|/?]\n" +
                        "\t\tbilderrahmen [-s|--server|/s|/server \"server url\"] [-u|--user|/u|/user \"server login name\"] [-p|--password|/p|/password \"server login password\"] [-n|--device-name|/n|/device-name \"device name\"] [-d|--directory|/d|/directory \"working directory\"]\n" +
                        "\n" +
                        "DESCRIPTION\n" +
                        "\t\tI4A Bilderrahmen-HTML Project\n" +
                        "\n" +
                        "\tOPTIONS\n" +
                        "\t\t-h|--help|/h|/help|/?\n" +
                        "\t\t\t\tDisplays this help page\n" +
                        "\n" +
                        "\t\t-s|--server|/s|/server \"server url\"\n" +
                        "\t\t\t\tThe server to pull configuration and images from\n" +
                        "\t\t\t\tThe default is \"https://bilderrahmen.cheaterll.de/files/\"\n" +
                        "\n" +
                        "\t\t-u|--user|/u|/user \"server login name\"\n" +
                        "\t\t\t\tThe login name for the server\n" +
                        "\n" +
                        "\t\t-p|--password|/p|/password \"server login password\"\n" +
                        "\t\t\t\tThe login password for the server\n" +
                        "\n" +
                        "\t\t-n|--device-name|/n|/device-name \"device name\"\n" +
                        "\t\t\t\tThe name of this device\n" +
                        "\t\t\t\tThe default is \"testdevice\"\n" +
                        "\n" +
                        "\t\t-d|--directory|/d|/directory \"working directory\"\n" +
                        "\t\t\t\tThe directory to use for storing configuration files, images and logs\n" +
                        "\t\t\t\tThe windows default is \"%LOCALAPPDATA%\\\"\n" +
                        "\t\t\t\tThe linux default is \"/home/[username]/\"(or \"/root/\" for the root user)\n" +
                        "\n" +
                        "\t\t-i|--update-interval|/i|/update-interval updateinterval\n" +
                        "\t\t\t\tThe frequency of configuration and image downloads in seconds\n" +
                        "\t\t\t\tThe default is 1800 (30 min)\n" +
                        "\n" +
                        "\t\t--enable-usb|/enable-usb|--disable-usb|/disable-usb\n" +
                        "\t\t\t\tEnables or disables image import from connected storage media\n" +
                        "\t\t\t\tEnabled by default\n" +
                        "\n" +
                        "\t\t--usb-update-interval|/usb-update-interval updateinterval\n" +
                        "\t\t\t\tThe frequency of scans for connected storage media in seconds\n" +
                        "\t\t\t\tThe default is 60 (1 min)\n" +
                        "\n" +
                        "\t\t--random-order|/random-order|--sorted-order|/sorted-order\n" +
                        "\t\t\t\tStates whether the images should be displayed in random or original order. \n" +
                        "\t\t\t\tThe default is random\n" +
                        "\n" +
                        "BUGS\n" +
                        "\t\tReport bugs to info@cheaterll.de\n" +
                        "\n" +
                        "AUTHOR\n" +
                        "\t\tRobin Schippan\n" +
                        "\t\tLukas Trautmann\n"
        );
    }
}
