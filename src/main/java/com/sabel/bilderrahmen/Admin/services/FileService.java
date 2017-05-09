package com.sabel.bilderrahmen.Admin.services;

import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;
import com.sabel.bilderrahmen.Admin.resources.PicturePool;
import org.apache.commons.net.ftp.FTPFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by robin on 19.04.17.
 */
public class FileService {


    public static PicturePool readPictures() {
        FtpService ftpService = FtpService.getInstance();
        FTPFile[] folder = ftpService.getFolder("/files/images");
        PicturePool picturePool = PicturePool.getInstance();
        for (FTPFile file : folder) {
            if (!file.getName().equals(""))
                picturePool.addPicture(file.getName());
        }
        return picturePool;
    }

    public static GroupPool readGroups() {
        FtpService ftpService = FtpService.getInstance();
        File xml = ftpService.getFile("/files/config/Groups.xml");
        GroupPool groups = readGroups(xml);
        xml.delete();
        return groups;
    }

    public static GroupPool readGroups(File GroupsXml) {
        try {
            JAXBContext jc = JAXBContext.newInstance(GroupPool.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            GroupPool groups = GroupPool.getInstance();
            groups.setGroupArrayList(((GroupPool) unmarshaller.unmarshal(GroupsXml)).getGroupArrayList());
            return groups;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeGroups() {

        File tmp = new File("Groups.xml");
        FtpService ftpService = FtpService.getInstance();
        JAXBContext jaxbContext = null;
        try {
            Marshaller jaxbMarshaller;
            jaxbContext = JAXBContext.newInstance(GroupPool.class);
            jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(GroupPool.getInstance(), tmp);
            ftpService.upload("files/config", tmp);
            tmp.delete();
            System.out.println("Temp Datei geloescht: " + !tmp.exists());
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static ClientPool readClients() {

        FtpService ftpService = FtpService.getInstance();
        File xml = ftpService.getFile("/files/config/Clients.xml");
        ClientPool clients = readClients(xml);
        xml.delete();

        return clients;
    }


    public static ClientPool readClients(File ClientsXml) {
        try {
            JAXBContext jc = JAXBContext.newInstance(ClientPool.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            ClientPool clients = ClientPool.getInstance();
            clients.setClientArrayList(((ClientPool) unmarshaller.unmarshal(ClientsXml)).getClientArrayList());
            return clients;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean writeClients() {
        FtpService ftpService = FtpService.getInstance();
        File tmp = new File("Clients.xml");
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(ClientPool.class);
            Marshaller jaxbMarshaller;
            jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(ClientPool.getInstance(), tmp);
            ftpService.upload("files/config", tmp);
            tmp.delete();
            System.out.println("Temp Datei geloescht: " + !tmp.exists());
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
