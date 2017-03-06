package utils.panels;

import utils.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class P_ClientSelect extends JPanel {
    private JComboBox jComboBox;
    private ArrayList<Client> clientArrayList;
    private JLabel jLabel;
    private JPanel boxPanel;


    public P_ClientSelect(ArrayList<Client> clientArrayList) {

        this.clientArrayList = clientArrayList;
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
        String[] inhalt = new String[clientArrayList.size()];
        for (int i = 0; i < clientArrayList.size(); i++) {
            inhalt[i] = clientArrayList.get(i).getName();
            //System.out.println(clientArrayList.get(i).getNumber());
        }
       // jComboBox = new JComboBox(inhalt);
        jComboBox = new JComboBox(clientArrayList.toArray());
        jLabel = new JLabel("Select Client:");
        add(boxPanel);
        boxPanel.add(jLabel);
        boxPanel.add(jComboBox);


    }
}
