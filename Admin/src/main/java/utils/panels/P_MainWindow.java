package utils.panels;

import utils.BilderPool;
import utils.Clients;
import utils.Gruppe;

import javax.swing.AbstractListModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Created by Robin on 23.01.2017.
 */
public class P_MainWindow extends JPanel {

    private JTabbedPane jTabbedPane;
    private P_ClientGroupSelect p_ClientGroupSelect;
    private ArrayList<Clients> clientss;
    private BilderPool bilderPool;
    private ArrayList<Gruppe> gruppes;
    private MyListModel myListModel;

    public P_MainWindow(ArrayList<Clients> arrayListClients, ArrayList<Gruppe> gruppeArrayList, BilderPool bilderPool) {
        clientss = arrayListClients;
        this.bilderPool = bilderPool;
        gruppes = gruppeArrayList;
        myListModel = new MyListModel(gruppes);


        jTabbedPane = new JTabbedPane();

        p_ClientGroupSelect = new P_ClientGroupSelect(arrayListClients, gruppeArrayList);
        jTabbedPane.addTab("Clients", null, new P_ZuordnungsPain(clientss, myListModel));
        jTabbedPane.addTab("Gruppen", null, new P_ZuordnungsPain(clientss, this.bilderPool));
        jTabbedPane.setPreferredSize(new Dimension(900, 500));
        add(jTabbedPane);
    }

    private class MyListModel extends AbstractListModel {
        ArrayList arrayList;

        MyListModel(ArrayList arrayList) {
            this.arrayList = arrayList;
        }

        public int getSize() {
            return arrayList.size();
        }

        @Override
        public Object getElementAt(int index) {
            return arrayList.get(index);
        }

    }
}
