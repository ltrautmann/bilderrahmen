package com.sabel.bilderrahmen.client.utils.web;

import com.sabel.bilderrahmen.client.utils.config.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class FileDownloader {
    public static void getImage(String file) throws IOException {
        download(Config.getServer() + "images/" + file, Config.getLocalImageDir() + file);
    }

    public static void getConfig() throws IOException {
        download(Config.getServer() + "config/config.xml", Config.getLocalConfigDir() + "config.xml");
    }

    public static void getFile(String srcURL, String destPath) throws IOException {
        download(Config.getServer() + srcURL, destPath);
    }

    private static void download(String url, String file) throws IOException {
        Authenticator.setDefault(Config.getWebAuth());
        URL web = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(web.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
