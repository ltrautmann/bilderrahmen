package com.sabel.bilderrahmen.Client.utils.web;

import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class WebService {
    private static char[] uname;
    private static char[] passwd;

    public static boolean getImage(String file) throws IOException {
        return download(Config.getServer() + Config.getRemoteImageDir() + file, Config.getLocalImageDir() + file);
    }

    public static boolean getConfig(String file) throws IOException {
        return download(Config.getServer() + Config.getRemoteConfigDir() + file, Config.getLocalConfigDir() + file);
    }

    public static boolean getFile(String srcURL, String destPath) throws IOException {
        return download(Config.getServer() + srcURL, destPath);
    }

    private static boolean download(String url, String file) throws IOException {
        URL web = new URL(url);
        HttpURLConnection huc = getAuthenticatedConnection(web);
        Logger.appendln("Attempting to download file \"" + url + "\" to \"" + file + "\". Response code is " + huc.getResponseCode(), Logger.LOGTYPE_INFO);
        if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = huc.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return true;
        } else {
            return false;
        }
    }

    public static HttpURLConnection authenticateConnection(HttpURLConnection con) {
        String login = new BASE64Encoder().encode((new String(uname) + ":" + new String(passwd)).getBytes());
        con.setRequestProperty("Authorization", "Basic " + login);
        return con;
    }

    public static HttpURLConnection getAuthenticatedConnection(URL url) throws IOException {
        return authenticateConnection(getConnection(url));
    }

    public static HttpURLConnection getAuthenticatedConnection(String url) throws IOException {
        return authenticateConnection(getConnection(url));
    }

    public static HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    public static HttpURLConnection getConnection(String url) throws IOException {
        return getConnection(new URL(url));
    }

    public static char[] getUname() {
        return uname;
    }

    public static void setUname(char[] uname) {
        WebService.uname = uname;
    }

    public static char[] getPasswd() {
        return passwd;
    }

    public static void setPasswd(char[] passwd) {
        WebService.passwd = passwd;
    }
}
