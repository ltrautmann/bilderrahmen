package utils.interfaces;

import utils.Client;

import java.util.ArrayList;

/**
 * Created by Robin on 17.02.2017.
 */
public interface I_GotClients {
    ArrayList<Client> getClients();

    void addElement(Client client);

}
