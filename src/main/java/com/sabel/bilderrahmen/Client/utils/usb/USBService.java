package com.sabel.bilderrahmen.Client.utils.usb;


import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.image.ImageTools;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by you shall not pass on 10.05.2017.
 */
public class USBService {


    public static synchronized void update() {
        try {
            System.out.println(ImageTools.getSupportedExtensions());
            Process mount = Runtime.getRuntime().exec("lsblk");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            String s = null;
            List<String> deviceList = new ArrayList<>();
            while ((s = stdInput.readLine()) != null) {
                if (s.contains("part /media")) {
                    deviceList.add(s.substring(s.indexOf("part /media") + 5));
                    Logger.appendln("Found device " + deviceList.get(deviceList.size() - 1), Logger.LOGTYPE_INFO);
                }
            }
            List<String> images = new ArrayList<>();
            for (String d : deviceList) {
                List<Path> collect = Files.find(Paths.get(d),
                        Integer.MAX_VALUE,
                        (filePath, fileAttr) -> fileAttr.isRegularFile())
                        .collect(Collectors.toList());
                for (Path p : collect) {
                    String path = p.toString();
                    Logger.appendln("Found \"" + path + "\".", Logger.LOGTYPE_INFO);
                    if (path.contains(".") && ImageTools.getSupportedExtensions().contains(path.substring(path.lastIndexOf('.') + 1))) {
                        images.add(path);
                        Logger.appendln("\"" + path + "\" is an image and was added to the list.", Logger.LOGTYPE_INFO);
                    }
                }
            }
            for (String i : images) {
                Config.getImageService().addImage(new USBImage(i, i.substring(i.lastIndexOf('/')), Config.getUsbDisplayTime()));
                System.out.println("Found image " + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
