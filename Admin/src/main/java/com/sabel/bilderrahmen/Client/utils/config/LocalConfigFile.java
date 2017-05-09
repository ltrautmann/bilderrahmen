package com.sabel.bilderrahmen.Client.utils.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

/**
 * Created by you shall not pass on 08.05.2017.
 */
@XmlRootElement
public class LocalConfigFile {
    private String server;
    private String devicename;
    private String localRootDir;
    private int configUpdateInterval;
    private String uname;
    private String passwd;

    public static LocalConfigFile read(String path) throws JAXBException {
        Unmarshaller um = JAXBContext.newInstance(LocalConfigFile.class).createUnmarshaller();
        return  (LocalConfigFile) um.unmarshal(new File(path));
    }

    public void write(String path) throws JAXBException {
        Marshaller m = JAXBContext.newInstance(LocalConfigFile.class).createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, new File(path));
    }

    public LocalConfigFile(String server, String devicename, String localRootDir, int configUpdateInterval, String uname, String passwd) {
        this.server = server;
        this.devicename = devicename;
        this.localRootDir = localRootDir;
        this.configUpdateInterval = configUpdateInterval;
        this.uname = uname;
        this.passwd = passwd;
    }

    public String getServer() {
        return server;
    }

    @XmlElement
    public void setServer(String server) {
        this.server = server;
    }

    public String getDevicename() {
        return devicename;
    }

    @XmlElement
    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getLocalRootDir() {
        return localRootDir;
    }

    @XmlElement
    public void setLocalRootDir(String localRootDir) {
        this.localRootDir = localRootDir;
    }

    public int getConfigUpdateInterval() {
        return configUpdateInterval;
    }

    @XmlElement
    public void setConfigUpdateInterval(int configUpdateInterval) {
        this.configUpdateInterval = configUpdateInterval;
    }

    public String getUname() {
        return uname;
    }

    @XmlElement
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    @XmlElement
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Server: " + getServer() +
                "\nDevice Name: " + getDevicename() +
                "\nDirectory: " + getLocalRootDir() +
                "\nUpdate Interval: " + getConfigUpdateInterval();
    }
}
