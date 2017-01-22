package utils.panels;

import utils.Clients;
import utils.Gruppe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class P_ClientGroupSelect extends JPanel {


    private ArrayList<Clients> arrayListClients;
    private ArrayList<Gruppe>gruppeArrayList;
    private P_Groups p_Groups;
    private P_ClientSelect p_ClientSelect;



    public P_ClientGroupSelect(boolean testlauf ,ArrayList<Clients> arrayListClients ,ArrayList<Gruppe> gruppeArrayList) {
        this.arrayListClients = arrayListClients;
        this.gruppeArrayList = gruppeArrayList;
        if(testlauf)testdaten();

        initComponents();

    }

    private void initComponents() {
        setLayout(new BorderLayout());

        p_ClientSelect = new P_ClientSelect(arrayListClients);
        p_Groups = new P_Groups(gruppeArrayList);
        add(p_ClientSelect, BorderLayout.WEST);
        add(p_Groups, BorderLayout.EAST);
    }

    public void testdaten(){
        this.arrayListClients = new ArrayList<Clients>();
        this.gruppeArrayList = new ArrayList<Gruppe>();
        Clients c[] = new Clients[10];
        Gruppe g[] = new Gruppe[10];

        for (int i = 0 ; i < c.length; i++) {
            c[i] = new Clients();
            g[i]= new Gruppe("Gruppe_"+i);
            c[i].setName("Client_Nr:" + i);
            arrayListClients.add(c[i]);
            gruppeArrayList.add(g[i]);
        }
    }
}
