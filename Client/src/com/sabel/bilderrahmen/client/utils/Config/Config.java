package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageService;
import com.sabel.bilderrahmen.client.utils.WebService.MyAuthenticator;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.*;

/**
 * Created by you shall not pass on 24.02.2017.
 */
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@XmlRootElement
public class Config {

    private static ConfigReaderWriter configReaderWriter;
    private static ImageService imageService;
    private static String server; // https://bilderrahmen.cheaterll.de/files/
    private static String deviceID;
    private static String MACAddress;
    private static String localConfigDir;
    private static String localImageDir;
    private static String localResizedImageDir;
    private static MyAuthenticator webAuth;

    public static void setConfigDefault(){
        System.out.println("Using default paths to save and load config file and images");
        setServer("https://bilderrahmen.cheaterll.de/files/");
        setDeviceID("testdevice");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            setLocalConfigDir("D:\\config\\");
            setLocalImageDir("D:\\images\\");
            setLocalResizedImageDir("D:\\images\\resized\\");
        }else {
            setLocalConfigDir("/home/" + System.getProperty("user.name") + "/config/");
            setLocalImageDir("/home/" + System.getProperty("user.name") + "/images/");
            setLocalResizedImageDir("/home/" + System.getProperty("user.name") + "/images/resized/");
        }




        configReaderWriter = new ConfigReaderWriter();
    }

    public static void readMAC(){
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X", mac[i]));
            }
            MACAddress = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }
    }

    public static String getDeviceID() {
        return deviceID;
    }

    public static void setDeviceID(String deviceName) {
        if (MACAddress ==null)readMAC();
        Config.deviceID = deviceName + "-" + MACAddress;
    }

    public static MyAuthenticator getWebAuth() {
        return webAuth;
    }

    public static void setWebAuth(MyAuthenticator webAuth) {
        Config.webAuth = webAuth;
    }

    public static void setWebAuth(String username, String password){
        MyAuthenticator.setPasswordAuthentication(username, password);
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        try {
            //Check if URL is valid
            new URL(server);
            //Add / to the end if not already present
            if ('/' == server.charAt(server.length()-1)){
                Config.server = server;
            } else {
                Config.server = server + "/";
            }
        } catch (Exception e) {
            //If URL is not valid, throw exception
            throw new IllegalArgumentException("Not a valid URL");
        }
    }

    public static String getLocalConfigDir() {
        return localConfigDir;
    }

    public static void setLocalConfigDir(String localConfigDir) {
        if ('/' == localConfigDir.charAt(localConfigDir.length()-1)){
            localConfigDir = localConfigDir + '/';
        }
        Config.localConfigDir = localConfigDir;
    }

    public static String getLocalImageDir() {
        return localImageDir;
    }

    public static void setLocalImageDir(String localImageDir) {

        if ('/' == localImageDir.charAt(localImageDir.length()-1)){
            localImageDir = localImageDir + '/';
        }
        Config.localImageDir = localImageDir;
    }

    public static String getLocalResizedImageDir() {
        return localResizedImageDir;
    }

    public static void setLocalResizedImageDir(String localResizedImageDir) {

        if ('/' == localResizedImageDir.charAt(localResizedImageDir.length()-1)){
            localResizedImageDir = localResizedImageDir + '/';
        }
        Config.localResizedImageDir = localResizedImageDir;
    }

    public static ConfigReaderWriter getConfigReaderWriter() {
        return configReaderWriter;
    }

    public static String getMACAddress() {
        return MACAddress;
    }

    public static void setImageService(ImageService imageService) {
        Config.imageService = imageService;
    }

    public static ImageService getImageService() {
        return imageService;
    }
}
