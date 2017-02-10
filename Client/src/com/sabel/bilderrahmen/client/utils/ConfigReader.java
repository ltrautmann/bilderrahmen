package com.sabel.bilderrahmen.client.utils;

import com.sabel.bilderrahmen.client.utils.WebService.FileDownloader;

import java.io.IOException;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class ConfigReader {

    public void readConfig(String file){

    }

    public void readInitialConfig() throws IOException {
        FileDownloader.getConfig("default-config.xml");



        //FileDownloader.setServer();
        //FileDownloader.setWebAuth();
    }
}
