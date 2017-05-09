package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by robin on 19.04.17.
 */
public class AllocatePane extends JPanel {
    private JPanel jPanelMiddle;
    private JPanel jPanelLeft;
    private JPanel jPanelRight;
    private JPanel jPanelCombobox;
    private JPanel jPanelAdd;
    private JPanel jPanelRem;
    private JScrollPane jScrollPaneLeft;
    private JScrollPane jScrollPaneRight;
    private ArrayList targets;
    private JList items;
    private JList itmePool;
    private JComboBox jComboBox;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPane2;
    private JButton jBadd;
    private JButton jBrem;
    private int kindeOfPane; // 1= ignore Pictures; 2= Bild >> Client; 3= Bild >> Gruppe; 4 = Gruppe >> Client

    public AllocatePane(ArrayList itmePool, ArrayList targets) {
        this.targets = targets;
        this.itmePool = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return
                        (itmePool.toArray()).length;
            }

            @Override
            public Object getElementAt(int index) {
                return (itmePool.toArray())[index];
            }
        });
        if (targets.get(0) instanceof Client && itmePool.get(0) instanceof Picture_Properties) {
            kindeOfPane = 2;
        }
        if (targets.get(0) instanceof Client && itmePool.get(0) instanceof Group) {
            kindeOfPane = 4;
        }
        if (targets.get(0) instanceof Group) {
            kindeOfPane = 3;
        }

        initComponents();
        buildWindow();
        initEvents();

    }

    public AllocatePane(ArrayList<Client> targets) {
        this.targets = targets;
        itmePool = null;
        kindeOfPane = 1;
        initComponents();
        buildWindow();
        initEvents();

    }

    private void initEvents() {
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = jComboBox.getSelectedItem();
                if (o instanceof Client) {
                    if (kindeOfPane == 1) {
                        showTarget();
                    }
                    if (kindeOfPane == 2) {
                        showTarget();

                    }
                    if (kindeOfPane == 4) {
                        showClientGroups();
                    }
                }
                if (o instanceof Group) {
                    showGroupPictures();
                }
            }

        });
        jBadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (kindeOfPane) {
                    case 1: {
                        ((Client) jComboBox.getSelectedItem()).addIgnoredPicture(itmePool.getSelectedValuesList());
                        showTarget();
                        break;
                    }
                    case 2: {
                        ((Client) jComboBox.getSelectedItem()).addPrivatePicture(itmePool.getSelectedValuesList());
                        showTarget();
                        break;
                    }
                    case 3: {
                        ((Group) jComboBox.getSelectedItem()).addPicture(itmePool.getSelectedValuesList());
                        showGroupPictures();
                        break;
                    }
                    case 4: {
                        ((Client) jComboBox.getSelectedItem()).addGroup(itmePool.getSelectedValuesList());
                        showClientGroups();
                        break;
                    }

                }

            }
        });
        jBrem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (kindeOfPane) {
                    case 1: {
                        ((Client) jComboBox.getSelectedItem()).removeIgnoredPicture(items.getSelectedValuesList());
                        showTarget();
                        break;
                    }
                    case 2: {
                        ((Client) jComboBox.getSelectedItem()).removePrivatePictureByName(items.getSelectedValuesList());
                        showTarget();
                        break;
                    }
                    case 3: {
                        ((Group) jComboBox.getSelectedItem()).removePicture(items.getSelectedValuesList());
                        showGroupPictures();
                        break;
                    }
                    case 4: {
                        ((Client) jComboBox.getSelectedItem()).removeGroup(items.getSelectedValuesList());
                        showClientGroups();

                        break;
                    }
                }
            }
        });

        refreshPane();
    }

    public void refreshPane() {
        switch (kindeOfPane) {
            case 1:
            case 2: {
                Object o = jComboBox.getSelectedItem();

                if (o instanceof Client) {
                    showTarget();
                }
                break;
            }
            case 3: {
                showGroupPictures();
                break;
            }
            case 4: {
                showClientGroups();
            }
        }
    }

    private void showGroupPictures() {
        items = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return ((Group) jComboBox.getSelectedItem()).getPictureList().size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((Group) jComboBox.getSelectedItem()).getPictureList().get(index);
            }
        });
        jScrollPaneRight.setViewportView(items);
    }

    private void buildWindow() {
        add(jSplitPane1);
        jPanelLeft.setLayout(new BorderLayout());
        jPanelRight.setLayout(new BorderLayout());
        jPanelLeft.add(jScrollPaneLeft);
        jPanelRight.add(jScrollPaneRight);
        jPanelRight.setPreferredSize(new Dimension(200, 300));
        jPanelLeft.setPreferredSize(new Dimension(200, 300));
        jSplitPane1.setLeftComponent(jPanelLeft);
        jSplitPane1.setRightComponent(jSplitPane2);
        jSplitPane2.setLeftComponent(jPanelMiddle);
        jSplitPane2.setRightComponent(jPanelRight);
        jComboBox = new JComboBox(targets.toArray());
        jPanelCombobox.add(jComboBox);
        jPanelAdd.add(jBadd);
        jPanelRem.add(jBrem);
        jPanelMiddle.add(jPanelCombobox);
        jPanelMiddle.add(jPanelAdd);
        jPanelMiddle.add(jPanelRem);
        if (itmePool == null) {
            showTarget();
        } else {
            jScrollPaneLeft.setViewportView(itmePool);
            itmePool.setVisibleRowCount(11);
            if (kindeOfPane == 4) {
                showClientGroups();
            }
        }
    }

    private void showClientGroups() {
        items =
                new JList(new AbstractListModel() {
                    @Override
                    public int getSize() {
                        Object selectedItem = jComboBox.getSelectedItem();
                        if (selectedItem instanceof Client) {
                            return ((Client) selectedItem).getGroups().size();

                        }
                        System.err.println("Client erwartet aber " + selectedItem + " erhalten");
                        return 0;
                    }

                    @Override
                    public Object getElementAt(int index) {
                        Object selectedItem = jComboBox.getSelectedItem();
                        if (selectedItem instanceof Client) {
                            return ((Client) selectedItem).getGroups().get(index);
                        }
                        return null;
                    }
                });
        jScrollPaneRight.setViewportView(items);
    }

    private void initComponents() {

        jPanelAdd = new JPanel();
        jPanelRem = new JPanel();
        jPanelCombobox = new JPanel();
        jPanelMiddle = new JPanel();
        jPanelRight = new JPanel();
        jPanelLeft = new JPanel();
        jSplitPane1 = new JSplitPane();
        jSplitPane2 = new JSplitPane();
        jBadd = new JButton(">>");
        jBrem = new JButton("<<");
        jBadd.setSize(60, 20);
        jBrem.setSize(60, 20);
        jPanelMiddle.setLayout(new GridLayout(3, 1));
        jScrollPaneRight = new JScrollPane();
        jScrollPaneLeft = new JScrollPane();
    }

    private void showTarget() {
        if (kindeOfPane == 1) {
            jScrollPaneLeft.setViewportView(collectAllPictures());
            jScrollPaneRight.setViewportView(ignoredPicturesofTarget());
        }
        if (kindeOfPane == 2) {
            jScrollPaneRight.setViewportView(privatePicturesofTarget());
        }
    }

    private JList ignoredPicturesofTarget() {

        items = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return ((Client) jComboBox.getSelectedItem()).getIgnoredPictures().size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((Client) jComboBox.getSelectedItem()).getIgnoredPictures().get(index);
            }
        });
        return items;
    }

    private JList privatePicturesofTarget() {
        items = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return ((Client) jComboBox.getSelectedItem()).getPrivatePictures().size();
            }

            @Override
            public Object getElementAt(int index) {
                return ((Client) jComboBox.getSelectedItem()).getPrivatePictures().get(index);
            }
        });
        return items;
    }

    private JList collectAllPictures() {
        ArrayList<Picture_Properties> allPictures = new ArrayList<>();
        ArrayList<Group> groupes = GroupPool.getInstance().getGroupByName(((Client) jComboBox.getSelectedItem()).getGroups());
        for (Group g : groupes) {
            for (Picture_Properties p : g.getPictureList()) {
                if (!allPictures.contains(p)) {
                    allPictures.add(p);
                }
            }
        }

        itmePool = new JList(new AbstractListModel() {
            @Override
            public int getSize() {
                return allPictures.size();
            }

            @Override
            public Object getElementAt(int index) {
                return allPictures.get(index);
            }
        });
        return itmePool;
    }
}
