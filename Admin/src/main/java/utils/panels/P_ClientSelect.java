package utils.panels;

import utils.Clients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

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

    public void setComboboxActionlistener(ActionListener comboboxActionlistener) {
        jComboBox.addActionListener(comboboxActionlistener);
    }

    public Object getSelectedItem() {
        return jComboBox.getSelectedItem();
    }



    private void initComponents() {
        setLayout(new BorderLayout());
        boxPanel = new JPanel();
        //boxPanel.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        String[] inhalt = new String[clientsArrayList.size()];
        for (int i = 0; i < clientsArrayList.size(); i++) {
            inhalt[i] = clientsArrayList.get(i).getName();
            System.out.println(clientsArrayList.get(i).getNumber());
        }
       // jComboBox = new JComboBox(inhalt);
        jComboBox = new JComboBox(clientsArrayList.toArray());
        jLabel = new JLabel("Select Client:");
        add(boxPanel);
        boxPanel.add(jLabel);
        boxPanel.add(jComboBox);


    }
}
