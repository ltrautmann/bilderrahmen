package utils;

import utils.interfaces.I_GotClients;

import javax.swing.AbstractListModel;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by Robin on 07.03.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientPool extends AbstractListModel implements I_GotClients {
    @XmlElementWrapper(name = "Clients")
    @XmlElement(name = "Client")
    private ArrayList<Client> clientArrayList;

    public ClientPool() {
        clientArrayList = new ArrayList<>();
    }
@XmlTransient
    public ArrayList<Client> getClientArrayList() {
        return clientArrayList;
    }

    public void setClientArrayList(ArrayList<Client> clientArrayList) {
        this.clientArrayList = clientArrayList;
    }

    public void update() {
        for (Client c : clientArrayList) {
            c.update();
        }
    }

    @Override
    public int getSize() {
        return clientArrayList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return clientArrayList.get(index);
    }

    @Override
    public ArrayList<Client> getClients() {
        return clientArrayList;
    }

    @Override
    public void addElement(Client client) {
        if (!clientArrayList.contains(client))
            clientArrayList.add(client);

    }
}
