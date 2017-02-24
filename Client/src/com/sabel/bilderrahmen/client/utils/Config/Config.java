package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.WebService.MyAuthenticator;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by you shall not pass on 24.02.2017.
 */
public class Config {
    private static String server; // https://bilderrahmen.cheaterll.de/files/
    private static String deviceID;
    private static String MACAddress;
    private static String localConfigDir;
    private static String localImageDir;
    private static MyAuthenticator webAuth;

    public static void testConfig(){
        setServer("https://bilderrahmen.cheaterll.de/files/");
        setDeviceID("testdevice");
        setLocalConfigDir("D:\\config\\");
        setLocalImageDir("D:\\images\\");

        System.out.println("Server:" + getServer());
        System.out.println("DeviceID:" + getDeviceID());
        System.out.println("ConfigDir:" + getLocalConfigDir());
        System.out.println("ImageDir:" + getLocalImageDir());
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

    public void setWebAuth(String username, String password){
        MyAuthenticator.setPasswordAuthentication(username, password);
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        Config.server = server;
    }

    public static String getLocalConfigDir() {
        return localConfigDir;
    }

    public static void setLocalConfigDir(String localConfigDir) {
        Config.localConfigDir = localConfigDir;
    }

    public static String getLocalImageDir() {
        return localImageDir;
    }

    public static void setLocalImageDir(String localImageDir) {
        Config.localImageDir = localImageDir;
    }
}
