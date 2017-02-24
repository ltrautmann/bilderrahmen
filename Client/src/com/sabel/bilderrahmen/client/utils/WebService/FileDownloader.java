package com.sabel.bilderrahmen.client.utils.WebService;

import com.sabel.bilderrahmen.client.utils.Config.Config;

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
    private static FileDownloader instance;

    private FileDownloader(String server, String localConfigDir, String localImageDir, String username, String password){
        Config.setServer(server);
        Config.setLocalConfigDir(localConfigDir);
        Config.setLocalImageDir(localImageDir);
        MyAuthenticator.setPasswordAuthentication(username, password);
        Config.setWebAuth(new MyAuthenticator());
    }

    public static void createInstance(String server, String localConfigDir, String localImageDir, String username, String password) {
        if (instance == null) {
            instance = new FileDownloader(server, localConfigDir, localImageDir, username, password);
        }
    }

    public static FileDownloader getInstance(){
        return instance;
    }

    public void getImage(String file) throws IOException {
        saveFileFromURL(Config.getServer() + "images/" + file, Config.getLocalImageDir() + file);
    }

    public void getConfig(String file) throws IOException {
        saveFileFromURL(Config.getServer() + "config/" + file, Config.getLocalConfigDir() + file);
    }

    public void getFile(String sourcePath, String localPath) throws IOException{
        saveFileFromURL(Config.getServer() + sourcePath, localPath);
    }

    private void saveFileFromURL(String url, String file) throws IOException {
        Authenticator.setDefault(Config.getWebAuth());
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public void setServer(String url){
        try {
            //Check if URL is valid
            new URL(url);
            //Add / to the end if not already present
            if ('/' == url.charAt(url.length()-1)){
                Config.setServer(url);
            } else {
                Config.setServer(url + "/");
            }
        } catch (Exception e) {
            //If URL is not valid, throw exception
            throw new IllegalArgumentException("Not a valid URL");
        }
    }
}
