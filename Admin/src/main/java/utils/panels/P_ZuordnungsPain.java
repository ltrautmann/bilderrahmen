package utils.panels;

import utils.BildSettings;
import utils.BilderPool;
import utils.Client;
import utils.Gruppe;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
    private JPanel jPanelMitte;/**/
    private JList listLiks;
    private JList listRechts;
    private JScrollPane jScrollPaneLinks;
    private JScrollPane jScrollPaneRechts;
    private JComboBox jComboBox;
    private JButton[] jButtons;
    private boolean ignorPaneFlag = false;

    public P_ZuordnungsPain(ArrayList<? extends AbstractListModel> drodownListe, AbstractListModel modelLinks) {

        initComponents(drodownListe, modelLinks);
        initevents();

    }

    public P_ZuordnungsPain(ArrayList<Client> clientArrayList, boolean isIgnorPane) {
        jComboBox = new JComboBox(clientArrayList.toArray());
        ignorPaneFlag = isIgnorPane;


        this.listLiks = new JList(collectGroupPictures().toArray());//todo alle Gruppenbilder hinzufügen
        this.listRechts = new JList((AbstractListModel) ((jComboBox.getSelectedObjects())[0]));
        setLayout(new BorderLayout());
        jPanelLinks = new JPanel();
        jPanelRechts = new JPanel();
        jPanelMitte = new JPanel();
        center = new JPanel();
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
        initevents();
        add(center);


    }

    private ArrayList<BildSettings> collectGroupPictures() {
        ArrayList<Gruppe> gruppesOfClient = ((Client) jComboBox.getSelectedItem()).getGruppen();
        //  System.out.println(gruppesOfClient);
        ArrayList<BildSettings> bildSettingss = new ArrayList<>();
        for (Gruppe g : gruppesOfClient) {
            for (BildSettings b : g.getGruppenBildArrayList()) {
                if (!bildSettingss.contains(b))
                    bildSettingss.add(b);
            }
        }
        return bildSettingss;
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

    private void initevents() {
        jButtons[0].addActionListener(new ActionListener() { // Hinzufuegen button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBox.getSelectedItem() instanceof Client) {
                    while (listLiks.getSelectedValue() != null) {
                        if (listLiks.getSelectedValue() instanceof BildSettings) {
                            ((Client) jComboBox.getSelectedItem()).addElement((BildSettings) listLiks.getSelectedValue());
                        } else if (listLiks.getSelectedValue() instanceof Gruppe) {
                            ((Client) jComboBox.getSelectedItem()).addElement((Gruppe) listLiks.getSelectedValue());
                        } else
                            JOptionPane.showConfirmDialog(null, "Fuck", "da stimmt was mit den typen nicht", JOptionPane.YES_OPTION);
                        listLiks.removeSelectionInterval(listLiks.getSelectedIndex(), listLiks.getSelectedIndex());
                    }
                }
                if (jComboBox.getSelectedItem() instanceof Gruppe) {
                    while (listLiks.getSelectedValue() != null) {
                        if (listLiks.getSelectedValue() instanceof BildSettings) {
                            ((Gruppe) jComboBox.getSelectedItem()).addElement((BildSettings) listLiks.getSelectedValue());
                            //}
                            //else if (listLiks.getSelectedValue() instanceof Gruppe) {
                            //  ((Gruppe) jComboBox.getSelectedItem()).addElement((Gruppe) listLiks.getSelectedValue());
                        } else
                            JOptionPane.showConfirmDialog(null, "Fuck", "da stimmt was mit den typen nicht", JOptionPane.YES_OPTION);
                        listLiks.removeSelectionInterval(listLiks.getSelectedIndex(), listLiks.getSelectedIndex());
                    }
                }

            }
        });
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               // System.out.println("liste links pre: "+listLiks.getModel());
                //System.out.println("combobox pre: "+jComboBox.getSelectedItem());
                if (ignorPaneFlag) {
                    ((Client) jComboBox.getSelectedItem()).setAngezeigteListe(1);
                    System.out.println("Ignore Seite");

                    listLiks.setModel(new ListModel() {
                        @Override
                        public int getSize() {
                            return collectGroupPictures().size();
                        }

                        @Override
                        public Object getElementAt(int index) {
                            return collectGroupPictures().get(index);
                        }

                        @Override
                        public void addListDataListener(ListDataListener l) {

                        }

                        @Override
                        public void removeListDataListener(ListDataListener l) {

                        }
                    });
                }

                else if (!(listLiks.getModel() instanceof BilderPool)) {
                    ((Client) jComboBox.getSelectedItem()).setAngezeigteListe(0);
                }

                else if (jComboBox.getSelectedItem() instanceof Client && listLiks.getModel() instanceof BilderPool) {
                    System.out.println("Spezielle Bilder");
                    ((Client) jComboBox.getSelectedItem()).setAngezeigteListe(2);

                }
                listRechts.setModel((AbstractListModel) jComboBox.getSelectedItem());
                //System.out.println("post: "+jComboBox.getSelectedItem());
            }
        });
    }
}
