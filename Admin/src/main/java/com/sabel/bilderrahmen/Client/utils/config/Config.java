package com.sabel.bilderrahmen.Client.utils.config;

import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Client.Main;
import com.sabel.bilderrahmen.Client.utils.image.ImageService;
import com.sabel.bilderrahmen.Client.utils.image.ImageTools;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.web.FileDownloader;
import com.sabel.bilderrahmen.Client.utils.web.MyAuthenticator;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.*;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Config {

    private static String server;
    private static String deviceID;
    private static String devicename;
    private static String MACAddress;
    private static String localConfigDir;
    private static String localImageDir;
    private static String localResizedDir;
    private static String localLogDir;
    private static String localRootDir;
    private static String remoteImageDir;
    private static String remoteConfigDir;
    private static String remoteConfigFile;
    private static int configUpdateInterval;
    private static boolean unixDevice;
    private static boolean randomImageOrder;
    private static MyAuthenticator webAuth;
    private static String[] args;
    private static ImageService imageService;

    public static void init() {
        Logger.appendln("Using default paths to load and save config file, images and logs.", Logger.LOGTYPE_INFO);
        setServer("https://bilderrahmen.cheaterll.de/files/");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            Logger.appendln("Detected operating system " + System.getProperty("os.name") + ". Using Windows paths.", Logger.LOGTYPE_INFO);
            unixDevice = false;
            setLocalRootDir(System.getenv("LOCALAPPDATA") + "\\bilderrahmen\\");
            Logger.appendln("Using directory root \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
        } else {
            Logger.appendln("Detected operating system " + System.getProperty("os.name") + ". Using Linux paths.", Logger.LOGTYPE_INFO);
            unixDevice = true;
            String uname = System.getProperty("user.name");
            if (uname.toLowerCase().equals("root")) {
                setLocalRootDir("/root/bilderrahmen/");
            } else {
                setLocalRootDir("/home/" + uname + "/bilderrahmen/");
            }
            Logger.appendln("Using directory root \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
        }
        setDevicename("testdevice");
        setDeviceID();
        setRemoteConfigDir("config/");
        setRemoteConfigFile(getRemoteConfigDir() + getDeviceID() + ".xml");
        setRemoteImageDir("images/");
        setConfigUpdateInterval(15);
        setWebAuth(new char[]{'g', 'b', 's'}, new char[]{'K', 'e', 'n', 'n', 'w', 'o', 'r', 't', '0'});
        interpretLocalConfigFile();
        interpretCMDArgs();
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
        if (!(new File(getLocalRootDir()).exists() && new File(getLocalConfigDir()).exists() && new File(getLocalImageDir()).exists() && new File(getLocalResizedDir()).exists() && new File(getLocalLogDir()).exists())) {
            Logger.appendln("Not all required directories could be created. Please check that you have sufficient permissions on the folder \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_FATAL);
            Main.quit();
        }
        new LocalConfigFile(getServer(), getDevicename(), getLocalRootDir(), getConfigUpdateInterval(), new String(MyAuthenticator.getUsername()), new String(MyAuthenticator.getPassword()));
    }

    private static void interpretLocalConfigFile() {
        String localConfig = getLocalConfigDir() + "local-config.xml";
        if (new File(localConfig).exists()) {
            try {
                LocalConfigFile lcf = LocalConfigFile.read(localConfig);
                if (lcf.getServer() != null && lcf.getServer().equals("")) {
                    setServer(lcf.getServer());
                    Logger.appendln("Download Server changed to \"" + getServer() + "\".", Logger.LOGTYPE_INFO);
                }
                if (lcf.getDevicename() != null && !lcf.getDevicename().equals("")) {
                    setDevicename(lcf.getDevicename());
                    Logger.appendln("Device name changed to \"" + getDevicename() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                }
                if (lcf.getLocalRootDir() != null && !lcf.getLocalRootDir().equals("")) {
                    setLocalRootDir(lcf.getLocalRootDir());
                    Logger.appendln("Working Directory changed to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                }
                if (lcf.getConfigUpdateInterval() != 0) {
                    setConfigUpdateInterval(lcf.getConfigUpdateInterval());
                    Logger.appendln("Update Interval changed to \"" + getConfigUpdateInterval() + "\".", Logger.LOGTYPE_INFO);
                }
                if (lcf.getUname() != null && !lcf.getUname().equals("")) {
                    MyAuthenticator.setUsername(lcf.getUname().toCharArray());
                    Logger.appendln("Device name changed to \"" + lcf.getUname() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                }
                if (lcf.getPasswd() != null && !lcf.getPasswd().equals("")) {
                    MyAuthenticator.setPassword(lcf.getPasswd().toCharArray());
                    Logger.appendln("Device name changed to \"" + lcf.getPasswd() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                }
            } catch (JAXBException e) {
                Logger.appendln("Local config file was found but could not be read. This may result in an inability to connect to the Server.", Logger.LOGTYPE_ERROR);
            }
        } else {
            Logger.appendln("Local config file was not found. This is probably the first time the program is run. If it isn't, please check that you have sufficient permissions to write to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
        }
    }

    private static void interpretCMDArgs() {
        int count = 0;
        if (args != null) {
            for (int i = 0; i + 1 < args.length; i += 2) {
                switch (args[i]) {
                    case "-s":
                    case "--server":
                    case "/s":
                    case "/server":
                        setServer(args[i + 1]);
                        Logger.appendln("Download Server changed to \"" + getServer() + "\".", Logger.LOGTYPE_INFO);
                        break;
                    case "-u":
                    case "--user":
                    case "/u":
                    case "/user":
                        MyAuthenticator.setUsername(args[i + 1].toCharArray());
                        Logger.appendln("Download Server Login Name changed.", Logger.LOGTYPE_INFO);
                        break;
                    case "-p":
                    case "--password":
                    case "/p":
                    case "/password":
                        MyAuthenticator.setPassword(args[i + 1].toCharArray());
                        Logger.appendln("Download Server Password changed.", Logger.LOGTYPE_INFO);
                        break;
                    case "-n":
                    case "--device-name":
                    case "/n":
                    case "/device-name":
                        setDevicename(args[i + 1]);
                        setDeviceID();
                        Logger.appendln("Device Name changed to \"" + getDevicename() + "\", Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                        break;
                    case "-d":
                    case "--directory":
                    case "/d":
                    case "/directory":
                        setLocalRootDir(args[i + 1]);
                        Logger.appendln("Working Directory changed to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                        break;
                    case "-i":
                    case "--update-interval":
                    case "/i":
                    case "/update-interval":
                        try {
                            int interval = Integer.parseInt(args[i + 1]);
                            if (interval > 0) {
                                setConfigUpdateInterval(interval);
                                Logger.appendln("Working Directory changed to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                            } else {
                                Logger.appendln("Update Interval must be bigger than 0. Console argument \"" + args[i] + " " + args[i + 1] + "\" is being ignored.", Logger.LOGTYPE_WARNING);
                            }
                            break;
                        } catch (NumberFormatException e) {
                            Logger.appendln("Update Interval must be numeric. Console argument \"" + args[i] + " " + args[i + 1] + "\" is being ignored.", Logger.LOGTYPE_WARNING);
                            break;
                        }
                }

            }
        }
    }

    public static int testServerConnection() {
        HttpURLConnection huc = null;
        try {
            Authenticator.setDefault(Config.getWebAuth());
            huc = (HttpURLConnection) new URL(Config.getServer()).openConnection();
            huc.setRequestMethod("HEAD");
            return HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return 0;
        }
    }

    public static void readServerConfig(){
        try {
            if (FileDownloader.getConfig()) {
                //TODO: Config Herunterladen
            } else {
                Logger.appendln("Config file could not be fetched from server at \"" + getServer() + getRemoteConfigFile() + "\". Registering clientv2 and attempting to fetch default config file.", Logger.LOGTYPE_WARNING);
                Authenticator.setDefault(Config.getWebAuth());
                HttpURLConnection huc = (HttpURLConnection) new URL(Config.getServer() + "config/register.php?name=" + URLEncoder.encode(getDeviceID(), "UTF-8")).openConnection();
                if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Logger.appendln("Failed to register clientv2, HTTP Error code \"" + huc.getResponseCode() + "\" at \"" + huc.getURL() + "\".", Logger.LOGTYPE_ERROR);
                }
                if (FileDownloader.getFile(getRemoteConfigDir() + "default.xml", Config.getLocalConfigDir() + "default.xml")) {
                    FileService.readClients(null);
                } else {
                    Logger.appendln("Default config file could not be fetched from server at \"" + getServer() + getRemoteConfigDir() + "config.xml\".", Logger.LOGTYPE_ERROR);
                }
            }
        } catch (IOException e) {
            Logger.appendln("Could not write local configuration file.", Logger.LOGTYPE_ERROR);
        }
        if (new File(getLocalConfigDir() + getDeviceID() + ".xml").exists()) {

        } else {
            Logger.appendln("Config file was not found in local config directory. Searching for default configuration file in local config directory.", Logger.LOGTYPE_WARNING);
            if (new File(getLocalConfigDir() + "default.xml").exists()) {

            } else {
                Logger.appendln("Default config file was not found in local config directory. ", Logger.LOGTYPE_FATAL);
                Logger.appendln("", Logger.LOGTYPE_FATAL);
            }
        }
        //TODO: Bilder Herunterladen
        ImageTools.resizeAllImages(false);
        //TODO: Bilder in ImageService speichern
        Config.setImageService(new ImageService());
    }

    private static void readMAC() {
        try {
            NetworkInterface network = null;
            byte[] mac = null;
            if (unixDevice) {
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
        String directorySeparator;
        if (isUnixDevice()) {
            directorySeparator = "/";
        } else {
            directorySeparator = "\\";
        }
        setLocalConfigDir(getLocalRootDir() + "config" + directorySeparator);
        setLocalLogDir(getLocalRootDir() + "config" + directorySeparator + "logs" + directorySeparator);
        setLocalImageDir(getLocalRootDir() + "images" + directorySeparator);
        setLocalResizedDir(getLocalRootDir() + "images" + directorySeparator + "resized" + directorySeparator);
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

    public static void setDeviceID() {
        if (MACAddress == null) readMAC();
        deviceID = getDevicename() + "-" + MACAddress;
    }

    public static boolean isUnixDevice() {
        return unixDevice;
    }

    public static MyAuthenticator getWebAuth() {
        return webAuth;
    }

    public static void setWebAuth(char[] username, char[] password) {
        MyAuthenticator.setPasswordAuthentication(username, password);
    }

    public static String getDevicename() {
        return devicename;
    }

    public static void setDevicename(String devicename) {
        Config.devicename = devicename;
    }

    public static String getRemoteConfigFile() {
        return remoteConfigFile;
    }

    public static void setRemoteConfigFile(String remoteConfigFile) {
        Config.remoteConfigFile = remoteConfigFile;
    }

    public static String getRemoteImageDir() {
        return remoteImageDir;
    }

    public static void setRemoteImageDir(String remoteImageDir) {
        Config.remoteImageDir = remoteImageDir;
    }

    public static String getRemoteConfigDir() {
        return remoteConfigDir;
    }

    public static void setRemoteConfigDir(String remoteConfigDir) {
        Config.remoteConfigDir = remoteConfigDir;
    }

    public static void passArgs(String[] args) {
        Config.args = args;
    }

    public static boolean isRandomImageOrder() {
        return randomImageOrder;
    }

    public static void setRandomImageOrder(boolean randomImageOrder) {
        Config.randomImageOrder = randomImageOrder;
    }

    public static ImageService getImageService() {
        return imageService;
    }

    public static void setImageService(ImageService imageService) {
        Config.imageService = imageService;
    }
}
