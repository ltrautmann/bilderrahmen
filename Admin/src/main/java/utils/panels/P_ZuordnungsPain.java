package utils.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by robin on 10.02.17.
 */
public class P_ZuordnungsPain extends JPanel {
    private JPanel center;
    private JPanel jPanelLinks;
    private JPanel jPanelRechts;
    private JPanel jPanelMitte;
    private JList listLiks;
    private JList listRechts;
    private JScrollPane jScrollPaneLinks;
    private JScrollPane jScrollPaneRechts;
    private JComboBox jComboBox;
    private JButton[] jButtons;

    public P_ZuordnungsPain( ArrayList<AbstractListModel> drodownListe, AbstractListModel modelLinks) {
        initComponents(drodownListe,modelLinks);
        initevents();

    }

    private void initevents() {
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((MyAbstractListModel)listRechts.getModel()).addElement();

            }
        });
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    private void initComponents(ArrayList<AbstractListModel> dropdownliste, AbstractListModel modelLinks) {
        setLayout(new BorderLayout());
        jPanelLinks = new JPanel();
        jPanelRechts = new JPanel();
        jPanelMitte = new JPanel();
        center = new JPanel();
        center.setLayout(new BoxLayout(center,BoxLayout.X_AXIS));
        jButtons = new JButton[2];
        jButtons[0] = new JButton("Hinzufuegen >");
        jButtons[1] = new JButton("Löschen");
        jPanelMitte.setLayout(new GridLayout(2, 1));
        jPanelMitte.add(jButtons[0]);
        jPanelMitte.add(jButtons[1]);
        center.add(jPanelLinks);
        center.add(jPanelMitte);
        center.add(jPanelRechts);
        add(center);
        jPanelLinks.add(jScrollPaneLinks);
        jPanelRechts.add(jScrollPaneRechts);
        jComboBox = new JComboBox(dropdownliste.toArray());
        this.listLiks = new JList(modelLinks);
        this.listRechts = new JList(dropdownliste.get(0));
        jScrollPaneLinks = new JScrollPane(listLiks);
        jScrollPaneRechts = new JScrollPane(listRechts);

    }
}
