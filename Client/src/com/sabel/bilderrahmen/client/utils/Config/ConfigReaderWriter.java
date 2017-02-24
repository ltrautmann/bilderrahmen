package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.WebService.FileDownloader;

import java.io.File;
import java.io.IOException;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class ConfigReaderWriter {

    public void readConfig(String file){

    }

    public boolean readInitialConfig() throws IOException {
        //TODO: lokale config lesen

        System.out.println(Config.getLocalConfigDir() + Config.getDeviceID() + ".xml");
        System.out.println(new File(Config.getLocalConfigDir() + "default-config.xml"));

        if (new File(Config.getLocalConfigDir() + Config.getDeviceID() + ".xml").exists()) {
        } else if (new File(Config.getLocalConfigDir() + "default-config.xml").exists()) {
        } else {
            System.out.println("wtf");
            return false;
        }

        //Config.setServer();
        //FileDownloader.setWebAuth();
        return true;
    }

    public void modifyLoginParams(String url, String loginName, char[] password) {
        System.out.println(url + "\n" + loginName + "\n" + new String(password));
    }
}
