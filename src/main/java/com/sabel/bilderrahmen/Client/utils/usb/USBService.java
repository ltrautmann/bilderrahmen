package com.sabel.bilderrahmen.Client.utils.usb;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by you shall not pass on 10.05.2017.
 */
public class USBService {


    public static void test() {
        try {
            Process mount = Runtime.getRuntime().exec("lsblk");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(mount.getInputStream()));

// read the output from the command
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                if (s.contains("part /media")) {
                    System.out.println("Habe gefindet: \"" + s.substring(s.indexOf("part /media") + 5) + "\"");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
