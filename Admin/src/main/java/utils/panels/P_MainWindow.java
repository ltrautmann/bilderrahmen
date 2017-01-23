package utils.panels;

import utils.Clients;
import utils.Gruppe;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.util.ArrayList;

/**
 * Created by Robin on 23.01.2017.
 */
public class P_MainWindow extends JPanel {

    private JTabbedPane jTabbedPane;
    private P_ClientGroupSelect p_ClientGroupSelect;

    public P_MainWindow(boolean testlauf, ArrayList<Clients> arrayListClients, ArrayList<Gruppe> gruppeArrayList) {
        jTabbedPane = new JTabbedPane();

        p_ClientGroupSelect = new P_ClientGroupSelect(testlauf, arrayListClients, gruppeArrayList);
        jTabbedPane.addTab("Clients", null, p_ClientGroupSelect);
        jTabbedPane.addTab("Gruppen", null, new JPanel());
        add(jTabbedPane);
    }
}
