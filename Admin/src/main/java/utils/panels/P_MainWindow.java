package utils.panels;

import utils.BildSettings;
import utils.BilderPool;
import utils.Clients;
import utils.Gruppe;

import javax.swing.AbstractListModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        jTabbedPane.addTab("Clients bekommt gruppen", null, new P_ZuordnungsPain(clientss, myListModel));
        jTabbedPane.addTab("Client ignoriert Bilder", null, new P_ZuordnungsPain(clientss,true));
        jTabbedPane.addTab("Gruppe bekommt Bilder", null, new P_ZuordnungsPain(gruppeArrayList,bilderPool));
        jTabbedPane.setPreferredSize(new Dimension(900, 500));
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {


                        for (Clients c : clientss) {
                            c.setAngezeigteListe(jTabbedPane.getSelectedIndex());
                        }

            }
        });
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
