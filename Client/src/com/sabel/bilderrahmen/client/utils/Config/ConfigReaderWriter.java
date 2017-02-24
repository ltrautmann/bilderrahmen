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
        String localDeviceConfig = Config.getDeviceID() + ".xml";
        String localDefaultConfig = "default-config.xml";
        System.out.println("Looking for local configuration file at \"" + Config.getLocalConfigDir() + "\"");
        File config = new File(Config.getLocalConfigDir() + localDeviceConfig);
        if (config.exists()) {
            System.out.println("Found local device-specific config file at \"" +localDeviceConfig + "\"");
        }else{
            config = new File(Config.getLocalConfigDir() + localDefaultConfig);
            if (config.exists()) {
                System.out.println("Found local default config file at \"" +localDefaultConfig + "\"");
            }else {
                System.out.println("Could not find local config file.");
                return false;
            }
        }

        //Config.setServer();
        //FileDownloader.setWebAuth();
        return true;
    }

    public void modifyLoginParams(String url, String loginName, char[] password) {
        File f = new File(Config.getLocalConfigDir() + "default-config.xml");
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(url + "\n" + loginName + "\n" + new String(password));
    }
}
