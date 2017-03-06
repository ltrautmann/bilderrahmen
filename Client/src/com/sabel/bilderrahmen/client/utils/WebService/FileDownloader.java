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
    public static void getImage(String file) throws IOException {
        saveFileFromURL(Config.getServer() + "images/" + file, Config.getLocalImageDir() + file);
    }

    public static void getConfig(String file) throws IOException {
        saveFileFromURL(Config.getServer() + "config/" + file, Config.getLocalConfigDir() + "new-" + file);
    }

    public static void getFile(String sourcePath, String localPath) throws IOException{
        saveFileFromURL(Config.getServer() + sourcePath, localPath);
    }

    private static void saveFileFromURL(String url, String file) throws IOException {
        Authenticator.setDefault(Config.getWebAuth());
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
