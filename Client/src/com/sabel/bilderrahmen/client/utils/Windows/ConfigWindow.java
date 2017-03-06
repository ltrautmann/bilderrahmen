package com.sabel.bilderrahmen.client.utils.Windows;

import com.sabel.bilderrahmen.client.utils.Config.Config;
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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        initFrame();
        initComponents();
    }

    private void initComponents() {
        c = getContentPane();
        menuPanelParent = new JPanel();
        menuPanelParent.setLayout(new GridBagLayout());
        menuPanelParent.add(new MenuPanel(Config.getConfigReaderWriter()));
        c.add(menuPanelParent);
    }
}
