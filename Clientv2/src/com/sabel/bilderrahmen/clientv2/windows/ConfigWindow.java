package com.sabel.bilderrahmen.clientv2.windows;

import com.sabel.bilderrahmen.clientv2.Main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class ConfigWindow extends JFrame {
    private Container c;
    private JPanel menuPanelParent;

    public ConfigWindow() throws HeadlessException {
        init();
    }

    private void init() {
        initComponents();
        initFrame();
    }

    private void initComponents() {
        c = getContentPane();
        menuPanelParent = new JPanel();
        menuPanelParent.setLayout(new GridBagLayout());
        //TODO:MenuPanel
        c.add(menuPanelParent);
    }

    private void initFrame() {

    }

    public void exitForRestart(){
        JOptionPane.showMessageDialog(ConfigWindow.this, "Configuration Changes saved. Program will now exit.", "Configuration Changes Applied", JOptionPane.INFORMATION_MESSAGE);
        Main.restart();
    }
}
