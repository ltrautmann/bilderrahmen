package com.sabel.bilderrahmen.client.utils.config;

import com.sabel.bilderrahmen.client.utils.logger.Logger;
import com.sabel.bilderrahmen.client.utils.web.MyAuthenticator;

import java.io.File;
import java.net.*;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Config {

    private static String server;
    private static String deviceID;
    private static String MACAddress;
    private static String localConfigDir;
    private static String localImageDir;
    private static String localResizedDir;
    private static String localLogDir;
    private static String localRootDir;
    private static int configUpdateInterval;
    private static boolean isUnixDevice;
    private static MyAuthenticator webAuth;

    public static void setConfigDefault() {
        Logger.appendln("Using default paths to load and save config file, images and logs.", Logger.LOGTYPE_INFO);
        setServer("https://bilderrahmen.cheaterll.de/files/");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            setLocalRootDir(System.getenv("LOCALAPPDATA") + "\\bilderrahmen\\");
            isUnixDevice = false;
            Logger.appendln("Detected operating system " + System.getProperty("os.name") + ". Using Windows paths.", Logger.LOGTYPE_INFO);
            Logger.appendln("Using directory root \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
            setLocalConfigDir(getLocalRootDir() + "config\\");
            setLocalLogDir(getLocalRootDir() + "logs\\");
            setLocalImageDir(getLocalRootDir() + "images\\");
            setLocalResizedDir(getLocalRootDir() + "images\\resized\\");
        } else {
            Logger.appendln("Detected operating system " + System.getProperty("os.name") + ". Using Linux paths.", Logger.LOGTYPE_INFO);
            isUnixDevice = true;
            String uname = System.getProperty("user.name");
            setLocalRootDir("/home/" + uname + "/bilderrahmen");
            if (uname.toLowerCase().equals("root")) {
                setLocalRootDir("/root/bilderrahmen");
            }
            Logger.appendln("Using directory root \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
            setLocalConfigDir(getLocalRootDir() + "/config/");
            setLocalLogDir(getLocalRootDir() + "/config/logs/");
            setLocalImageDir(getLocalRootDir() + "/images/");
            setLocalResizedDir(getLocalRootDir() + "/images/resized/");
        }
        setDeviceID("testdev");
        setConfigUpdateInterval(15);
        if (new File(getLocalConfigDir()).mkdirs()) {
            Logger.appendln("Directory \"" + getLocalConfigDir() + "\" did not exist yet and was created.", Logger.LOGTYPE_INFO);
        }
        if (new File(getLocalLogDir()).mkdirs()) {
            Logger.appendln("Directory \"" + getLocalLogDir() + "\" did not exist yet and was created.", Logger.LOGTYPE_INFO);
        }
        if (new File(getLocalImageDir()).mkdirs()) {
            Logger.appendln("Directory \"" + getLocalImageDir() + "\" did not exist yet and was created.", Logger.LOGTYPE_INFO);
        }
        if (new File(getLocalResizedDir()).mkdirs()) {
            Logger.appendln("Directory \"" + getLocalResizedDir() + "\" did not exist yet and was created.", Logger.LOGTYPE_INFO);
        }
    }


    private static void readMAC() {
        try {
            NetworkInterface network = null;
            byte[] mac = null;
            if (isUnixDevice) {
                String iface = null;
                if (iface == null) {
                    iface = "eth";
                } else {
                    iface = "wlan";
                }
                int ifacenum = 0;
                while (mac == null && ifacenum < 10) {
                    try {
                        network = NetworkInterface.getByName(iface + ifacenum);
                        Logger.appendln("Attempting to read MAC of interface " + iface + ifacenum, Logger.LOGTYPE_INFO);
                        mac = network.getHardwareAddress();
                    } catch (Exception e) {
                        Logger.appendln("Unsuccessful, attempting next interface", Logger.LOGTYPE_INFO);
                        ifacenum++;
                    }
                    if (mac == null) {
                        Logger.appendln("Unsuccessful, attempting next interface", Logger.LOGTYPE_INFO);
                        ifacenum++;
                    }
                }
            } else {
                InetAddress inetAddress = InetAddress.getLocalHost();
                network = NetworkInterface.getByInetAddress(inetAddress);
                Logger.appendln("Reading MAC of interface with IP " + inetAddress.getHostAddress().toString(), Logger.LOGTYPE_INFO);
                mac = network.getHardwareAddress();
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X", mac[i]));
            }
            MACAddress = sb.toString();
            Logger.appendln("Detected MAC Address " + MACAddress, Logger.LOGTYPE_INFO);
        } catch (UnknownHostException e) {
            Logger.appendln("Failed to read MAC Address(UnknownHostException)", Logger.LOGTYPE_ERROR);
            MACAddress = "";
        } catch (SocketException e) {
            Logger.appendln("Failed to read MAC Address(SocketException)", Logger.LOGTYPE_ERROR);
            MACAddress = "";
        }
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        try {
            //Check if URL is valid
            new URL(server);
            //Add / to the end if not already present
            if ('/' == server.charAt(server.length() - 1)) {
                Config.server = server;
            } else {
                Config.server = server + "/";
            }
        } catch (Exception e) {
            //If URL is not valid, throw exception
            throw new IllegalArgumentException("Not a valid URL");
        }
    }

    public static String getLocalRootDir() {
        return localRootDir;
    }

    public static void setLocalRootDir(String localRootDir) {
        Config.localRootDir = localRootDir;
    }

    public static String getLocalConfigDir() {
        return localConfigDir;
    }

    public static void setLocalConfigDir(String localConfigDir) {
        if ('/' == localConfigDir.charAt(localConfigDir.length() - 1)) {
            localConfigDir = localConfigDir + '/';
        }
        Config.localConfigDir = localConfigDir;
    }

    public static String getLocalImageDir() {
        return localImageDir;
    }

    public static void setLocalImageDir(String localImageDir) {

        if ('/' == localImageDir.charAt(localImageDir.length() - 1)) {
            localImageDir = localImageDir + '/';
        }
        Config.localImageDir = localImageDir;
    }

    public static String getLocalResizedDir() {
        return localResizedDir;
    }

    public static void setLocalResizedDir(String localResizedDir) {

        if ('/' == localResizedDir.charAt(localResizedDir.length() - 1)) {
            localResizedDir = localResizedDir + '/';
        }
        Config.localResizedDir = localResizedDir;
    }

    public static String getLocalLogDir() {
        return localLogDir;
    }

    public static void setLocalLogDir(String localLogDir) {

        if ('/' == localLogDir.charAt(localLogDir.length() - 1)) {
            localLogDir = localLogDir + '/';
        }
        Config.localLogDir = localLogDir;
    }

    public static int getConfigUpdateInterval() {
        return configUpdateInterval;
    }

    public static void setConfigUpdateInterval(int configUpdateInterval) {
        Config.configUpdateInterval = configUpdateInterval;
    }

    public static String getDeviceID() {
        return deviceID;
    }

    public static void setDeviceID(String deviceName) {
        if (MACAddress == null) readMAC();
        deviceID = deviceName + "-" + MACAddress;
    }

    public static boolean isIsUnixDevice() {
        return isUnixDevice;
    }

    public static MyAuthenticator getWebAuth() {
        return webAuth;
    }

    public static void setWebAuth(char[] username, char[] password) {
        MyAuthenticator.setPasswordAuthentication(username, password);
    }
}
