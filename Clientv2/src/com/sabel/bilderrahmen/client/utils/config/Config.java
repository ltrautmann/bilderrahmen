package com.sabel.bilderrahmen.client.utils.config;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Config {

    private static String localConfigDir;

    public static String getLocalConfigDir() {
        return localConfigDir;
    }

    public static void setLocalConfigDir(String localConfigDir) {
        Config.localConfigDir = localConfigDir;
    }
}
