package com.sabel.bilderrahmen.client.utils.panels;

import com.sabel.bilderrahmen.client.Windows.ConfigWindow;
import com.sabel.bilderrahmen.client.utils.Config.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by you shall not pass on 17.02.2017.
 */
public class MenuPanel extends JPanel {
    private ConfigWindow parentFrame;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JPanel panelLabels;
    private JPanel panelTextfields;
    private JPanel panelTop;
    private JPanel panelBottom;
    private JButton buttonOK;

    public MenuPanel(ConfigWindow parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
        initEvents();
        setServerURL(Config.getServer());
        setDeviceName(Config.getDeviceID());
        setLocalConfigPath(Config.getLocalConfigDir());
        setLocalImagePath(Config.getLocalImageDir());
        setLocalResizedImagePath(Config.getLocalResizedImageDir());
    }

    public MenuPanel(ConfigWindow parentFrame, String url, String loginName, String password){
        this.parentFrame = parentFrame;
        initComponents();
        initEvents();
        setServerURL(url);
        setLoginName(loginName);
        setPassword(password);
        setDeviceName(Config.getDeviceID());
        setLocalConfigPath(Config.getLocalConfigDir());
        setLocalImagePath(Config.getLocalImageDir());
        setLocalResizedImagePath(Config.getLocalResizedImageDir());
    }

    private void initComponents(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        labels = new JLabel[7];
        textfields = new JTextField[7];
        labels[0] = new JLabel("Server URL");
        labels[1] = new JLabel("Login Name");
        labels[2] = new JLabel("Password");
        labels[3] = new JLabel("Device Name");
        labels[4] = new JLabel("Local Config Path");
        labels[5] = new JLabel("Local Image Path");
        labels[6] = new JLabel("Local Resized Image Path");
        textfields[0] = new JTextField(30);
        textfields[1] = new JTextField(30);
        textfields[2] = new JPasswordField(30);
        textfields[3] = new JTextField(30);
        textfields[4] = new JTextField(30);
        textfields[5] = new JTextField(30);
        textfields[6] = new JTextField(30);
        panelLabels = new JPanel();
        panelTextfields = new JPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelTextfields.setLayout(new BoxLayout(panelTextfields, BoxLayout.Y_AXIS));
        for (int i = 0; i < labels.length; i++) {
            labels[i].setHorizontalAlignment(JLabel.RIGHT);
            panelLabels.add(labels[i]);
            panelTextfields.add(textfields[i]);
            System.out.println(i + "|" + labels[i].getText());
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
                textfields[3].requestFocus();
            }
        });
        textfields[3].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfields[4].requestFocus();
            }
        });
        textfields[4].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfields[5].requestFocus();
            }
        });
        textfields[5].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textfields[6].requestFocus();
            }
        });
        textfields[6].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyConfigChanges();
            }
        });
    }

    private void applyConfigChanges(){
        if (getServerURL() != null && !getServerURL().equals("") &&
                getLoginName() != null && !getLoginName().equals("") &&
                getPassword() != null && getPassword().toString() != null && !getPassword().toString().equals("") &&
                getDeviceName() != null && !getDeviceName().equals("") &&
                getLocalConfigPath() != null && !getLocalConfigPath().equals("") &&
                getLocalImagePath() != null && !getLocalImagePath().equals("") &&
                getLocalResizedImagePath() != null && !getLocalResizedImagePath().equals("")) {

            Config.getConfigReaderWriter().modifyLoginParams(getServerURL(), getLoginName(), getPassword());
            Config.setLocalConfigDir(getLocalConfigPath());
            Config.setLocalImageDir(getLocalImagePath());
            Config.setLocalResizedImageDir(getLocalResizedImagePath());
            Config.setDeviceID(getDeviceName());
            //Test.restart();
            //TODO:restart
            parentFrame.exitAndRestart();
        } else {

        }

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

    public void setDeviceName(String deviceName) {
        this.textfields[3].setText(deviceName);
    }

    public void setLocalConfigPath(String path) {
        this.textfields[4].setText(path);
    }

    public void setLocalImagePath(String path) {
        this.textfields[5].setText(path);
    }

    public void setLocalResizedImagePath(String path) {
        this.textfields[6].setText(path);
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

    public String getDeviceName(){
        return this.textfields[3].getText();
    }

    public String getLocalConfigPath(){
        return this.textfields[3].getText();
    }

    public String getLocalImagePath(){
        return this.textfields[3].getText();
    }

    public String getLocalResizedImagePath(){
        return this.textfields[3].getText();
    }
}