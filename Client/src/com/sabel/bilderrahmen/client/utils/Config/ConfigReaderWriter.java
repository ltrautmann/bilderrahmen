package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.WebService.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class ConfigReaderWriter {
    private String confFile = "config.xml";

    public void readRemoteConfig() throws IOException {
        FileDownloader.getConfig(confFile);
        File oldConf = new File(Config.getLocalConfigDir() + confFile);
        File newConf = new File(Config.getLocalConfigDir() + "new-config.xml");
        if (sameContent(oldConf.toPath(), newConf.toPath())) {
            newConf.delete();
        } else {
            oldConf.delete();
            newConf.renameTo(new File(Config.getLocalConfigDir() + confFile));
        }
        //TODO: Config einlesen
    }

    public boolean readInitialConfig() throws IOException {
        //TODO: lokale config lesen
        System.out.println("Looking for local configuration file \"" + confFile + "\" at \"" + Config.getLocalConfigDir() + "\"");
        File config = new File(Config.getLocalConfigDir() + confFile);
        if (config.exists()) {
            System.out.println("Found local config file at \"" + config.getPath() + "\"");
        }else{
            System.out.println("Could not find local config file.");
            return false;
        }

        //TODO: config einlesen

        return true;
    }

    public void modifyLoginParams(String url, String loginName, char[] password) {
        File f = new File(Config.getLocalConfigDir() + confFile);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(url + "\n" + loginName + "\n" + new String(password));
    }

    boolean sameContent(Path file1, Path file2) throws IOException {
        final long size = Files.size(file1);
        if (size != Files.size(file2))
            return false;

        if (size < 4096)
            return Arrays.equals(Files.readAllBytes(file1), Files.readAllBytes(file2));

        try (InputStream is1 = Files.newInputStream(file1);
             InputStream is2 = Files.newInputStream(file2)) {
            // Compare byte-by-byte.
            // Note that this can be sped up drastically by reading large chunks
            // (e.g. 16 KBs) but care must be taken as InputStream.read(byte[])
            // does not neccessarily read a whole array!
            int data;
            while ((data = is1.read()) != -1)
                if (data != is2.read())
                    return false;
        }

        return true;
    }
}
