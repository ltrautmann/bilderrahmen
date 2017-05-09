package com.sabel.bilderrahmen.Admin.resources;

import com.sabel.bilderrahmen.Admin.Client;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by robin on 18.04.17.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientPool {
    @XmlElementWrapper(name = "Clients")
    @XmlElement(name = "Client")
    private ArrayList<Client> clientArrayList;
    @XmlTransient
    private static ClientPool instance = null;


    private  ClientPool() {

        clientArrayList = new ArrayList<>();
    }
    public static ClientPool getInstance(){
        if (instance == null) {
            instance = new ClientPool();

        }
        return instance;
    }

    @XmlTransient
    public ArrayList<Client> getClientArrayList() {
        return clientArrayList;
    }

    public void setClientArrayList(ArrayList<Client> clientArrayList) {
        this.clientArrayList = clientArrayList;
    }




    public Client getClientByName(String name) {
        for (Client c : clientArrayList)
            if (c.getName().equals(name))
                return c;
        return null;
    }
    public Client getClientByMac(String mac) {
        for (Client c : clientArrayList)
            if (c.getMac().equals(mac))
                return c;
        return null;
    }

    public boolean addClient(Client client) {
        if (clientArrayList.contains(client)) {
            System.err.println("Client bereits in der Liste");
            return false;
        }
        clientArrayList.add(client);
        if (!clientArrayList.contains(client)) {
            System.err.println("Nicht hinzugefügt");
            return false;
        }

        return true;
    }

    public boolean removeClient(Client client) {
        if (!clientArrayList.contains(client)) {
            System.err.println("Client nicht in der Liste");
            return false;
        }
        clientArrayList.remove(client);
        if (clientArrayList.contains(client)) {
            System.err.println("Client nicht gelöscht");
            return false;
        }
        return true;
    }


}
