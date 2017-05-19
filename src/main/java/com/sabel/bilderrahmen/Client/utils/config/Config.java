package com.sabel.bilderrahmen.Client.utils.config;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Client.Main;
import com.sabel.bilderrahmen.Client.utils.image.ImageService;
import com.sabel.bilderrahmen.Client.utils.image.ImageTools;
import com.sabel.bilderrahmen.Client.utils.image.SavedImage;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.web.WebService;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static int usbUpdateInterval;
    private static int usbDisplayTime;
    private static boolean usbEnabled;
    private static boolean unixDevice;
    private static boolean randomImageOrder;
    private static boolean ignoreServerDefinedImageOrder;
    private static boolean ignoreServerDefinedUSBTime;
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
        setConfigUpdateInterval(1800);
        WebService.setUname(new char[]{'g', 'b', 's'});
        WebService.setPasswd(new char[]{'K', 'e', 'n', 'n', 'w', 'o', 'r', 't', '0'});
        setRandomImageOrder(false);
        setIgnoreServerDefinedImageOrder(false);
        setUsbUpdateInterval(300);
        setIgnoreServerDefinedUSBTime(false);
        setUsbEnabled(true);
        setUsbDisplayTime(2);
        interpretLocalConfigFile();//TODO:??
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
            return;
        }
        if (!isUnixDevice() && isUsbEnabled()) {
            Logger.appendln("Loading from connected media devices is only supported on linux operating systems.", Logger.LOGTYPE_ERROR);
            setUsbEnabled(false);
        }
        try {
            new LocalConfigFile(getServer(), getDevicename(), getLocalRootDir(), getConfigUpdateInterval(), new String(WebService.getUname()), new String(WebService.getPasswd()), isUsbEnabled()).write(getLocalConfigDir() + "local-config.xml");
        } catch (JAXBException e) {
            Logger.appendln("Could not save changes to local configuration.", Logger.LOGTYPE_ERROR);
        }
    }

    private static void interpretLocalConfigFile() {
        try {
            String localConfig = getLocalConfigDir() + "local-config.xml";
            if (!new File(localConfig).exists()) {
                Logger.appendln("Local config file was not found. This is probably the first time the program is run. If it isn't, please check that you have sufficient permissions to write to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                LocalConfigFile lcf = new LocalConfigFile(getServer(), getDevicename(), getLocalRootDir(), getConfigUpdateInterval(), new String(WebService.getUname()), new String(WebService.getPasswd()), isUsbEnabled());
                lcf.write(localConfig);
            }
            LocalConfigFile lcf = LocalConfigFile.read(localConfig);
            int count = 0;
            if (lcf.getServer() != null && lcf.getServer().equals("") && !lcf.getServer().equals(getServer())) {
                setServer(lcf.getServer());
                Logger.appendln("Download Server changed to \"" + getServer() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.getDevicename() != null && !lcf.getDevicename().equals("") && !lcf.getDevicename().equals(getDevicename())) {
                setDevicename(lcf.getDevicename());
                Logger.appendln("Device name changed to \"" + getDevicename() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.getLocalRootDir() != null && !lcf.getLocalRootDir().equals("") && !lcf.getLocalRootDir().equals(getLocalRootDir())) {
                setLocalRootDir(lcf.getLocalRootDir());
                Logger.appendln("Working Directory changed to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.getConfigUpdateInterval() != 0 && lcf.getConfigUpdateInterval() != getConfigUpdateInterval()) {
                setConfigUpdateInterval(lcf.getConfigUpdateInterval());
                Logger.appendln("Update Interval changed to \"" + getConfigUpdateInterval() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.getUname() != null && !lcf.getUname().equals("") && !Arrays.equals(lcf.getUname().toCharArray(), WebService.getUname())) {
                WebService.setUname(lcf.getUname().toCharArray());
                Logger.appendln("Device name changed to \"" + lcf.getUname() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.getPasswd() != null && !lcf.getPasswd().equals("") && !Arrays.equals(lcf.getPasswd().toCharArray(), WebService.getPasswd())) {
                WebService.setPasswd(lcf.getPasswd().toCharArray());
                Logger.appendln("Device name changed to \"" + lcf.getPasswd() + "\", new Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                count++;
            }
            if (lcf.isUsbEnabled() != isUsbEnabled()) {
                setUsbEnabled(lcf.isUsbEnabled());
                Logger.appendln("USB now " + ((isUsbEnabled()) ? "enabled." : "disabled"), Logger.LOGTYPE_INFO);
                count++;
            }
            Logger.appendln("Local config file was found, " + count + ((count == 1) ? " setting was changed." : " settings were changed"), Logger.LOGTYPE_INFO);
        } catch (JAXBException e) {
            Logger.appendln("Local config file could not be accessed. This may result in an inability to connect to the Server.", Logger.LOGTYPE_ERROR);
            e.printStackTrace();
        }
    }

    private static void interpretCMDArgs() {
        int count = 0;
        Logger.appendln("Console arguments: " + Arrays.toString(args), Logger.LOGTYPE_INFO);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-s":
                    case "--server":
                    case "/s":
                    case "/server":
                        setServer(args[i + 1]);
                        Logger.appendln("Download Server changed to \"" + getServer() + "\".", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "-u":
                    case "--user":
                    case "/u":
                    case "/user":
                        WebService.setUname(args[i + 1].toCharArray());
                        Logger.appendln("Download Server Login Name changed.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "-p":
                    case "--password":
                    case "/p":
                    case "/password":
                        WebService.setPasswd(args[i + 1].toCharArray());
                        Logger.appendln("Download Server Password changed.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "-n":
                    case "--device-name":
                    case "/n":
                    case "/device-name":
                        setDevicename(args[i + 1]);
                        setDeviceID();
                        Logger.appendln("Device Name changed to \"" + getDevicename() + "\", Device ID is \"" + getDeviceID() + "\".", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "-d":
                    case "--directory":
                    case "/d":
                    case "/directory":
                        setLocalRootDir(args[i + 1]);
                        Logger.appendln("Working Directory changed to \"" + getLocalRootDir() + "\".", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "-i":
                    case "--update-interval":
                    case "/i":
                    case "/update-interval":
                        try {
                            int interval = Integer.parseInt(args[i + 1]);
                            if (interval > 0) {
                                setConfigUpdateInterval(interval);
                                setIgnoreServerDefinedUSBTime(true);
                                Logger.appendln("Update interval changed to " + getConfigUpdateInterval() + " seconds.", Logger.LOGTYPE_INFO);
                                count++;
                            } else {
                                Logger.appendln("Update Interval must be bigger than 0. Console argument \"" + args[i] + " " + args[i + 1] + "\" is being ignored.", Logger.LOGTYPE_WARNING);
                            }
                            break;
                        } catch (NumberFormatException e) {
                            Logger.appendln("Update Interval must be numeric. Console argument \"" + args[i] + " " + args[i + 1] + "\" is being ignored.", Logger.LOGTYPE_WARNING);
                            break;
                        }
                    case "--enable-usb":
                    case "/enable-usb":
                        setUsbEnabled(true);
                        Logger.appendln("USB enabled.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "--disable-usb":
                    case "/disable-usb":
                        setUsbEnabled(false);
                        Logger.appendln("USB disabled.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "--random-order":
                    case "/random-order":
                        setRandomImageOrder(true);
                        setIgnoreServerDefinedImageOrder(true);
                        Logger.appendln("Random image order enabled.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                    case "--sorted-order":
                    case "/sorted-order":
                        setRandomImageOrder(false);
                        setIgnoreServerDefinedImageOrder(true);
                        Logger.appendln("Random image order disabled.", Logger.LOGTYPE_INFO);
                        count++;
                        break;
                }

            }
            Logger.appendln("Detected " + args.length + " console arguments, " + count + " settings changed.", Logger.LOGTYPE_INFO);
        } else {
            Logger.appendln("No console arguments detected.", Logger.LOGTYPE_INFO);
        }
    }

    public static int testServerConnection() {
        try {
            HttpURLConnection huc = WebService.getAuthenticatedConnection(getServer());
            huc.setRequestMethod("HEAD");
            return HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return 0;
        }
    }

    public static boolean readServerConfig() {
        try {
            boolean success = true;
            success = success && WebService.getConfig("Clients.xml");
            success = success && WebService.getConfig("Groups.xml");
            if (success) {
                FileService.readClients(new File(getLocalConfigDir() + "Clients.xml"));
                FileService.readGroups(new File(getLocalConfigDir() + "Groups.xml"));
                Client thisClient = ClientPool.getInstance().getClientByMac(MACAddress);
                if (thisClient == null) {
                    Logger.appendln("Client was not found in configuration file, registering client with Server and exiting.", Logger.LOGTYPE_WARNING);
                    HttpURLConnection huc = WebService.getAuthenticatedConnection(Config.getServer() + "clients/register.php?name=" + URLEncoder.encode(getDeviceID(), "UTF-8"));
                    if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Logger.appendln("Failed to register client, HTTP Error code \"" + huc.getResponseCode() + "\" at \"" + huc.getURL() + "\".", Logger.LOGTYPE_ERROR);
                    }
                    Logger.appendln("No Configuration Available.", Logger.LOGTYPE_FATAL);
                    Main.quit();
                    return false;
                } else {
                    Logger.appendln("Successfully downloaded and read configuration file, downloading images.", Logger.LOGTYPE_INFO);
                    List<Picture_Properties> shownPictures = thisClient.getShownPictures();
                    List<SavedImage> savedImages = new ArrayList<>();
                    for (Picture_Properties p : shownPictures) {
                        try {
                            String name = p.getName();
                            if (new File(getLocalImageDir() + name).exists()) {
                                Logger.appendln("Image \"" + name + "\" already exists, skipping download.", Logger.LOGTYPE_INFO);
                                savedImages.add(new SavedImage(getLocalImageDir() + name, p));
                            } else {
                                Logger.appendln("Attempting download of image \"" + name + "\"", Logger.LOGTYPE_INFO);
                                boolean downloaded = WebService.getImage(p.getName());
                                if (downloaded) {
                                    savedImages.add(new SavedImage(getLocalImageDir() + p.getName(), p));
                                } else {
                                    Logger.appendln("Image download failed (Unable to access image), image will be ignored.", Logger.LOGTYPE_ERROR);
                                }
                            }
                        } catch (IOException e) {
                            Logger.appendln("Image download failed (IOException), image will be ignored.", Logger.LOGTYPE_ERROR);
                        }
                    }
                    setImageService(new ImageService(savedImages));
                    if (!isIgnoreServerDefinedImageOrder()) {
                        setRandomImageOrder(thisClient.isRandomImageOrder());
                    }
                    if (!isIgnoreServerDefinedUSBTime()) {
                        setUsbDisplayTime(thisClient.getDefaultanzeigedauer());
                    }
                    return true;
                }
            } else {
                Logger.appendln("Unable to get configuration files from server", Logger.LOGTYPE_ERROR);
                return false;
            }
        } catch (IOException e) {
            Logger.appendln("Could not write downloaded configuration file or download server could not be reached", Logger.LOGTYPE_ERROR);
            return false;
        }
    }

    private static void readMAC() {
        try {
            byte[] mac = null;
            if (unixDevice) {
                int ifacenum = 0;
                mac = readLinuxMac("wlp2s0");
                while (mac == null && ifacenum < 10) {
                    mac = readLinuxMac("eth" + ifacenum);
                    if (mac == null) {
                        mac = readLinuxMac("wlan" + ifacenum);
                        if (mac == null) {
                            ifacenum++;
                        }
                    }
                }
            } else {
                mac = readWindowsMac();
            }
            if (mac == null) {
                MACAddress = "";
                Logger.appendln("Failed to read MAC Address(No available Interface found)", Logger.LOGTYPE_ERROR);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X", mac[i]));
                }
                MACAddress = sb.toString();
                Logger.appendln("Detected MAC Address " + MACAddress, Logger.LOGTYPE_INFO);
            }
        } catch (UnknownHostException e) {
            Logger.appendln("Failed to read MAC Address(UnknownHostException)", Logger.LOGTYPE_ERROR);
            MACAddress = "";
        } catch (SocketException e) {
            Logger.appendln("Failed to read MAC Address(SocketException)", Logger.LOGTYPE_ERROR);
            MACAddress = "";
        }
    }

    private static byte[] readLinuxMac(String iface) {
        try {
            NetworkInterface network = NetworkInterface.getByName(iface);
            Logger.appendln("Attempting to read MAC of interface " + iface, Logger.LOGTYPE_INFO);
            return network.getHardwareAddress();
        } catch (Exception e) {
            Logger.appendln("Unsuccessful, attempting next interface", Logger.LOGTYPE_INFO);
            return null;
        }
    }

    private static byte[] readWindowsMac() throws UnknownHostException, SocketException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        NetworkInterface network = NetworkInterface.getByInetAddress(inetAddress);
        Logger.appendln("Reading MAC of interface with IP " + inetAddress.getHostAddress(), Logger.LOGTYPE_INFO);
        return network.getHardwareAddress();
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
        setLocalLogDir(getLocalRootDir() + "logs" + directorySeparator);
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

    public static int getUsbUpdateInterval() {
        return usbUpdateInterval;
    }

    public static void setUsbUpdateInterval(int usbUpdateInterval) {
        Config.usbUpdateInterval = usbUpdateInterval;
    }

    public static boolean isUsbEnabled() {
        return usbEnabled;
    }

    public static void setUsbEnabled(boolean usbEnabled) {
        Config.usbEnabled = usbEnabled;
    }

    public static int getUsbDisplayTime() {
        return usbDisplayTime;
    }

    public static void setUsbDisplayTime(int usbDisplayTime) {
        Config.usbDisplayTime = usbDisplayTime;
    }

    public static boolean isIgnoreServerDefinedImageOrder() {
        return ignoreServerDefinedImageOrder;
    }

    public static void setIgnoreServerDefinedImageOrder(boolean ignoreServerDefinedImageOrder) {
        Config.ignoreServerDefinedImageOrder = ignoreServerDefinedImageOrder;
    }

    public static boolean isIgnoreServerDefinedUSBTime() {
        return ignoreServerDefinedUSBTime;
    }

    public static void setIgnoreServerDefinedUSBTime(boolean ignoreServerDefinedUSBTime) {
        Config.ignoreServerDefinedUSBTime = ignoreServerDefinedUSBTime;
    }
}
