package com.sabel.bilderrahmen.Client.utils.web;

import com.sabel.bilderrahmen.Client.utils.config.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class FileDownloader {
    public static boolean getImage(String file) throws IOException {
        return download(Config.getServer() + Config.getRemoteImageDir() + file, Config.getLocalImageDir() + file);
    }

    public static boolean getConfig() throws IOException {
        return download(Config.getServer() + Config.getRemoteConfigFile(), Config.getLocalConfigDir() + "config.xml");
    }

    public static boolean getFile(String srcURL, String destPath) throws IOException {
        return download(Config.getServer() + srcURL, destPath);
    }

    private static boolean download(String url, String file) throws IOException {
        Authenticator.setDefault(Config.getWebAuth());
        URL web = new URL(url);
        HttpURLConnection huc = (HttpURLConnection) web.openConnection();
        huc.setRequestMethod("HEAD");
        if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ReadableByteChannel rbc = Channels.newChannel(web.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return true;
        } else {
            return false;
        }
    }
}
