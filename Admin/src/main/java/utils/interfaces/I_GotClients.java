package utils.interfaces;

import utils.Clients;

import java.util.ArrayList;

/**
 * Created by Robin on 17.02.2017.
 */
public interface I_GotClients {
    ArrayList<Clients> getClients();

    void addElement(Clients clients);

}
