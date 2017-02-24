package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.WebService.FileDownloader;

import java.io.IOException;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class ConfigReaderWriter {

    public void readConfig(String file){

    }

    public boolean readInitialConfig() throws IOException {
        //TODO: lokale config lesen



        //Config.setServer();
        //FileDownloader.setWebAuth();
        return true;
    }

    public void modifyLoginParams(String url, String loginName, char[] password) {
        System.out.println(url + "\n" + loginName + "\n" + new String(password));
    }
}
