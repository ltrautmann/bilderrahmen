package com.sabel.bilderrahmen.client.utils.panels;

import javax.swing.*;

/**
 * Created by you shall not pass on 17.02.2017.
 */
public class MenuPanel extends JPanel {
    private JPanel[] panels;
    private JLabel[] labels;
    private JTextField[] textfields;

    public MenuPanel() {
        panels = new JPanel[3];
        labels = new JLabel[3];
        textfields = new JTextField[3];
        panels[0] = new JPanel();
        panels[1] = new JPanel();
        panels[2] = new JPanel();
        labels[0] = new JLabel("Server URL:");
        labels[1] = new JLabel("Login Name");
        labels[2] = new JLabel("Password");
        textfields[0] = new JTextField(30);
        textfields[1] = new JTextField(30);
        textfields[2] = new JPasswordField(30);

    }
}
