package com.sabel.bilderrahmen.client.windows;

import com.sabel.bilderrahmen.client.Main;
import com.sabel.bilderrahmen.client.utils.config.Config;
import com.sabel.bilderrahmen.client.utils.logger.Logger;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class InitWindow extends JFrame {
    private Container c;
    private JProgressBar jProgressBar;
    private JScrollPane jScrollPane;
    private JTextArea jTextArea;

    public InitWindow() throws HeadlessException {
        init();
    }

    private void init() {
        initComponents();
        initEvents();
        setSize(960, 540);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Initializing");
        setVisible(true);
        Thread init = new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.initLogger(jTextArea, jProgressBar);
                Config.setConfigDefault();
                Logger.initLogFile();
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
                Logger.appendln("Raspi Bilderrahmen", Logger.LOGTYPE_INFO);
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
                Logger.appendln("", Logger.LOGTYPE_INFO);
                Logger.appendln("When in Display Mode, Press ESC to quit, F5 to force refresh configuration or F1 to edit local configuration", Logger.LOGTYPE_INFO);
                Logger.appendln("", Logger.LOGTYPE_INFO);
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
            }
        });
        init.setName("INIT");
        init.start();
    }

    private void initComponents() {
        c = getContentPane();
        jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);
        jTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane = new JScrollPane(jTextArea);
        c.add(jProgressBar, BorderLayout.NORTH);
        c.add(jScrollPane, BorderLayout.CENTER);
    }

    private void initEvents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.quit();
            }
        });
    }
}
