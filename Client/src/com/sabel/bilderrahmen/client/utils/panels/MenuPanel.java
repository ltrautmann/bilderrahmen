package com.sabel.bilderrahmen.client.utils.panels;

import com.sabel.bilderrahmen.client.test;
import com.sabel.bilderrahmen.client.utils.Config.Config;
import com.sabel.bilderrahmen.client.utils.Config.ConfigReaderWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by you shall not pass on 17.02.2017.
 */
public class MenuPanel extends JPanel {
    private JLabel[] labels;
    private JTextField[] textfields;
    private JPanel panelLabels;
    private JPanel panelTextfields;
    private JPanel panelTop;
    private JPanel panelBottom;
    private JButton buttonOK;
    private ConfigReaderWriter configReaderWriter;

    public MenuPanel(ConfigReaderWriter configReaderWriter) {
        this.configReaderWriter = configReaderWriter;
        initComponents();
        initEvents();
    }

    public MenuPanel(ConfigReaderWriter configReaderWriter, String url, String loginName, String password){
        this(configReaderWriter);
        setServerURL(url);
        setLoginName(loginName);
        setPassword(password);
    }

    private void initComponents(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labels = new JLabel[3];
        textfields = new JTextField[3];
        labels[0] = new JLabel("Server URL  ");
        labels[1] = new JLabel("Login Name  ");
        labels[2] = new JLabel("Password  ");
        textfields[0] = new JTextField(30);
        textfields[1] = new JTextField(30);
        textfields[2] = new JPasswordField(30);
        panelLabels = new JPanel();
        panelTextfields = new JPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelTextfields.setLayout(new BoxLayout(panelTextfields, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i++) {
            labels[i].setHorizontalAlignment(JLabel.RIGHT);
            panelLabels.add(labels[i]);
            panelTextfields.add(textfields[i]);
        }
        panelTop = new JPanel();
        panelTop.add(panelLabels);
        panelTop.add(panelTextfields);
        panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());
        buttonOK = new JButton("Speichern");
        panelBottom.add(buttonOK);
        this.add(panelTop);
        this.add(panelBottom);
    }

    private void initEvents() {
        buttonOK.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyConfigChanges();
            }
        });
        textfields[0].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfields[1].requestFocus();
            }
        });
        textfields[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfields[2].requestFocus();
            }
        });
        textfields[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyConfigChanges();
            }
        });
    }

    private void applyConfigChanges(){
        configReaderWriter.modifyLoginParams(getServerURL(), getLoginName(), getPassword());
        Config.getConfigReaderWriter().modifyLoginParams(null,null,null);
        //test.restart();
        //TODO:restart
    }

    public void setServerURL(String url){
        this.textfields[0].setText(url);
    }

    public void setLoginName(String loginName){
        this.textfields[1].setText(loginName);
    }

    public void setPassword(String password){
        this.textfields[2].setText(password);
    }

    public String getServerURL() {
        return this.textfields[0].getText();
    }

    public String getLoginName() {
        return this.textfields[1].getText();
    }

    public char[] getPassword(){
        return ((JPasswordField) this.textfields[2]).getPassword();
    }
}