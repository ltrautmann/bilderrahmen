package utils.panels;

import utils.Clients;
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
    private Clients clients;

    public P_Groups(Clients clients) {
        this.setLayout(new BorderLayout());
        initComponents(clients);
        setPreferences();
    }

    public P_Groups(ArrayList<Gruppe> gruppen) {
        this.setLayout(new BorderLayout());
        initComponents(gruppen);
        setPreferences();
    }

    private void setPreferences() {


    }

    private void initComponents(Clients clients) {
        this.clients = clients;
        jList = new JList<Gruppe>(clients);
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

    public void setClients(Clients clients) {
        this.clients = clients;
        jList.setModel(clients);
    }

    public List<Gruppe> getSelected() {
        return jList.getSelectedValuesList();
    }

    public void addGruppe(List<Gruppe> list) {
        if (clients != null)
            for (Gruppe g : list) {
                clients.addGroup(g);
            }
        clients.updateList();
    }

    public Gruppe rmGruppe(Gruppe gruppe) {
        Gruppe g = clients.removeGruppe(gruppe.getGruppenName());
        clients.updateList();
        jList.clearSelection();
        return g;
    }

}
