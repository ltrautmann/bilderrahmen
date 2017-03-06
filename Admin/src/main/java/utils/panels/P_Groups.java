package utils.panels;

import utils.Client;
import utils.Gruppe;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 22.01.17.
 */
public class P_Groups extends JPanel {
    private JScrollPane jScrollPane;
    private JList<Gruppe> jList;
    private Client client;

    public P_Groups(Client client) {
        this.setLayout(new BorderLayout());
        initComponents(client);
        setPreferences();
    }

    public P_Groups(ArrayList<Gruppe> gruppen) {
        this.setLayout(new BorderLayout());
        initComponents(gruppen);
        setPreferences();
    }

    private void setPreferences() {


    }

    private void initComponents(Client client) {
        this.client = client;
        jList = new JList<Gruppe>(client);
        jScrollPane = new JScrollPane(jList);
        //jScrollPane.setPreferredSize(new Dimension(25, 200));
        // jScrollPane.setLayout(new FlowLayout());
        this.add(jScrollPane, BorderLayout.CENTER);
    }

    private void initComponents(ArrayList<Gruppe> gruppen) {
        DefaultListModel<Gruppe> defaultListModel = new DefaultListModel<>();
        for (Gruppe gruppe : gruppen) {
            defaultListModel.addElement(gruppe);
        }
        jList = new JList<Gruppe>(defaultListModel);
        jScrollPane = new JScrollPane(jList);
        //jScrollPane.setPreferredSize(new Dimension(25, 200));
        this.add(jScrollPane);
    }

    private void initEvents() {

    }

    public void setClient(Client client) {
        this.client = client;
        jList.setModel(client);
    }

    public List<Gruppe> getSelected() {
        return jList.getSelectedValuesList();
    }

    public void addGruppe(List<Gruppe> list) {
        if (client != null)
            for (Gruppe g : list) {
                client.addGroup(g);
            }
        client.updateList();
    }

    public Gruppe rmGruppe(Gruppe gruppe) {
        Gruppe g = client.removeGruppe(gruppe.getGruppenName());
        client.updateList();
        jList.clearSelection();
        return g;
    }

}
