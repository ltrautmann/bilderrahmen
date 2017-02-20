package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.WebService.FileDownloader;

import java.io.IOException;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class test {
    public static String[] testimages;

    public static void main(String[] args) {
        //FileDownloader.createInstance("https://bilderrahmen.cheaterll.de/files/", "", "", "gbs", "Kennwort0");
        //FileDownloader fd = FileDownloader.getInstance();
        Window w = new Window();
    }

    public static void testimages(){
        testimages = new String[10];
        testimages[0] = "D:\\images\\Cat.jpg";
        testimages[1] = "D:\\images\\Chrysanthemum.jpg";
        testimages[2] = "D:\\images\\Desert.jpg";
        testimages[3] = "D:\\images\\Destroyer.jpg";
        testimages[4] = "D:\\images\\Hydrangeas.jpg";
        testimages[5] = "D:\\images\\Jellyfish.jpg";
        testimages[6] = "D:\\images\\Koala.jpg";
        testimages[7] = "D:\\images\\Lighthouse.jpg";
        testimages[8] = "D:\\images\\Penguins.jpg";
        testimages[9] = "D:\\images\\Tulips.jpg";
    }
}
