package utils.panels;

import utils.Clients;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class P_ClientSelect extends JPanel {
    private JComboBox jComboBox;
    private ArrayList<Clients> clientsArrayList;
    private JLabel jLabel;
    private JPanel boxPanel;


    public P_ClientSelect(ArrayList<Clients> clientsArrayList) {

        this.clientsArrayList = clientsArrayList;
        initComponents();

    }

    private void initComponents() {
        setLayout(new BorderLayout());
        boxPanel = new JPanel();
        //boxPanel.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        String[] inhalt = new String[clientsArrayList.size()];
        for (int i = 0; i < clientsArrayList.size(); i++) {
            inhalt[i] = clientsArrayList.get(i).getName();
        }
        jComboBox = new JComboBox(inhalt);
        jLabel = new JLabel("Select Client:");
        add(boxPanel);
        boxPanel.add(jLabel);
        boxPanel.add(jComboBox);
    }
}
