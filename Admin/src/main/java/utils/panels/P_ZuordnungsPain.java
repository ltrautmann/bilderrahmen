package utils.panels;

import utils.Gruppe;
import utils.interfaces.I_GotGroups;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

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

    public P_ZuordnungsPain(ArrayList<? extends AbstractListModel> drodownListe, AbstractListModel modelLinks) {
        initComponents(drodownListe, modelLinks);
        initevents();

    }

    private void initevents() {
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (jComboBox.getSelectedItem() instanceof I_GotGroups) {
                    Iterator i = listRechts.getSelectedValuesList().iterator();
                    while (i.hasNext()) {
                        System.out.println(i.next());
                        if (i instanceof Gruppe) {

                            ((I_GotGroups) jComboBox.getSelectedItem()).addElement((Gruppe) i.next());
                        }
                    }
                }

            }
        });
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void initComponents(ArrayList<? extends AbstractListModel> dropdownliste, AbstractListModel modelLinks) {
        jComboBox = new JComboBox(dropdownliste.toArray());
        this.listLiks = new JList(modelLinks);
        this.listRechts = new JList((AbstractListModel) ((jComboBox.getSelectedObjects())[0]));
        setLayout(new BorderLayout());
        jPanelLinks = new JPanel();
        jPanelRechts = new JPanel();
        jPanelMitte = new JPanel();
        center = new JPanel();
        // center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        jButtons = new JButton[2];
        jButtons[0] = new JButton("Hinzufuegen >");
        jButtons[1] = new JButton("Löschen");

        jPanelMitte.setLayout(new GridLayout(2, 1));
        jPanelMitte.add(jButtons[0]);
        jPanelMitte.add(jButtons[1]);


        jScrollPaneLinks = new JScrollPane(listLiks);
        jScrollPaneRechts = new JScrollPane(listRechts);
        jScrollPaneLinks.setPreferredSize(new Dimension(300, 200));
        jScrollPaneRechts.setPreferredSize(new Dimension(300, 200));
        jPanelLinks.add(jScrollPaneLinks);
        jPanelRechts.add(jScrollPaneRechts);


        center.add(jPanelLinks, BorderLayout.WEST);
        center.add(jPanelMitte, BorderLayout.CENTER);
        center.add(jPanelRechts, BorderLayout.EAST);
        center.add(jComboBox, BorderLayout.NORTH);

        add(center);

    }
}
