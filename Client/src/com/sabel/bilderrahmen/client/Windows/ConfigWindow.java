package com.sabel.bilderrahmen.client.Windows;

import com.sabel.bilderrahmen.client.Test;
import com.sabel.bilderrahmen.client.utils.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by you shall not pass on 24.02.2017.
 */
public class ConfigWindow extends JFrame {
    private Container c;
    private JPanel menuPanelParent;

    public ConfigWindow() throws HeadlessException {
        init();
    }

    private void initFrame() {
        setTitle("Config-Server");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        initComponents();
        initFrame();
    }

    private void initComponents() {
        c = getContentPane();
        menuPanelParent = new JPanel();
        menuPanelParent.setLayout(new GridBagLayout());
        menuPanelParent.add(new MenuPanel(this));
        c.add(menuPanelParent);
    }

    public void exitAndRestart(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Test.setInitWindow(new InitWindow());
        this.dispose();
    }
}
