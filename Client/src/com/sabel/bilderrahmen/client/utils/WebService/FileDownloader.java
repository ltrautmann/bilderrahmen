package com.sabel.bilderrahmen.client.utils.WebService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class FileDownloader {
    private static String server; // https://bilderrahmen.cheaterll.de/files/
    private static String localConfigDir;
    private static String localImageDir;
    private static MyAuthenticator webAuth;
    private static FileDownloader instance;

    private FileDownloader(String server, String localConfigDir, String localImageDir, String username, String password){
        setServer(server);
        setLocalConfigDir(localConfigDir);
        setLocalImageDir(localImageDir);
        MyAuthenticator.setPasswordAuthentication(username, password);
        webAuth = new MyAuthenticator();
    }

    public static void createInstance(String server, String localConfigDir, String localImageDir, String username, String password) {
        if (instance == null) {
            instance = new FileDownloader(server, localConfigDir, localImageDir, username, password);
        }
    }

    public static FileDownloader getInstance(){
        return instance;
    }

    public static void getImage(String file) throws IOException {
        saveFileFromURL(server + "images/" + file, localImageDir + file);
    }

    public static void getConfig(String file) throws IOException {
        saveFileFromURL(server + "config/" + file, localConfigDir + file);
    }

    public static void getFile(String sourcePath, String localPath) throws IOException{
        saveFileFromURL(server + sourcePath, localPath);
    }

    private static void saveFileFromURL(String url, String file) throws IOException {
        Authenticator.setDefault(webAuth);
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public static void setServer(String url){
        try {
            //Check if URL is valid
            new URL(url);
            //Add / to the end if not already present
            if ('/' == url.charAt(url.length()-1)){
                server = url;
            } else {
                server = url + "/";
            }
        } catch (Exception e) {
            //If URL is not valid, throw exception
            throw new IllegalArgumentException("Not a valid URL");
        }
    }

    public static String getServer(){
        return server;
    }

    public static String getLocalConfigDir() {
        return localConfigDir;
    }

    public static void setLocalConfigDir(String localConfigDir) {
        FileDownloader.localConfigDir = localConfigDir;
    }

    public static String getLocalImageDir() {
        return localImageDir;
    }

    public static void setLocalImageDir(String localImageDir) {
        FileDownloader.localImageDir = localImageDir;
    }

    public static void setWebAuth(String username, String password){
        MyAuthenticator.setPasswordAuthentication(username, password);
    }
}
