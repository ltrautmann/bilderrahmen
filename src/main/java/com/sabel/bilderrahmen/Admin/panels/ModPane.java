package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.Picture_Properties;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by robin on 05.05.17.
 */
@Deprecated
public class ModPane extends JScrollPane {
    public final static int MOD_CLIENT = 1;
    public final static int MOD_Gruppe = 2;
    private JComboBox jComboBox;
    private JPanel jPanelCombobox;
    private JPanel picPanel;
    private int modus;
    private ComboBoxModel comboBoxModel;

    private JPanel mainPanel;
    private MainWindow mainWindow;

    public ModPane(MainWindow mainWindow, int modus) {//modus 1 Clients modus 2 Gruppen
        this.modus = modus;
        picPanel = new JPanel();
        jComboBox = new JComboBox();

        picPanel.setLayout(new GridLayout());

        jPanelCombobox = new JPanel();
        this.mainWindow = mainWindow;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        fillCombo();
        //jComboBox.setSelectedIndex(0);
        mainPanel.add(jPanelCombobox);
        mainPanel.add(picPanel);

        jPanelCombobox.add(jComboBox);
        setViewportView(mainPanel);
        initEvents();
        showProbs();
    }

    private void initEvents() {
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProbs();
            }
        });
    }

    private void fillCombo() {
        if (modus == 1) {
            comboBoxModel = new DefaultComboBoxModel(ClientPool.getInstance().getClientArrayList().toArray());

        }
        if (modus == 2) {
            comboBoxModel = new DefaultComboBoxModel(GroupPool.getInstance().getGroupArrayList().toArray());
        }
        jComboBox.setModel(comboBoxModel);
        jComboBox.validate();
    }

    public void showProbs() {
        picPanel.removeAll();
        Object selectedItem = jComboBox.getSelectedItem();
        if (selectedItem instanceof Client) {
            for (Picture_Properties picture_properties : ((Client) selectedItem).getPrivatePictures()) {
                picPanel.add(new ModPictureTime(picture_properties));
            }
        }
        if (selectedItem instanceof Group) {
            for (Picture_Properties picture_properties : ((Group) selectedItem).getPictureList()) {
                picPanel.add(new ModPictureTime(picture_properties));
            }
        }
        System.out.println(selectedItem);
        picPanel.setLayout(new GridLayout(1 + picPanel.getComponentCount() / 2, 2));
        mainWindow.repaint();
    }

}
