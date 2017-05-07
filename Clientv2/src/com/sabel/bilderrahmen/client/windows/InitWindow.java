package com.sabel.bilderrahmen.client.windows;

import com.sabel.bilderrahmen.client.Main;
import com.sabel.bilderrahmen.client.utils.config.Config;
import com.sabel.bilderrahmen.client.utils.image.ImageTools;
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
import java.io.IOException;

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
        setTitle("Initializing");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
        Thread init = new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.initLogger(jTextArea, jProgressBar);
                Config.init();
                Logger.initLogFile();
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
                Logger.appendln("Raspi Bilderrahmen", Logger.LOGTYPE_INFO);
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
                Logger.appendln("", Logger.LOGTYPE_INFO);
                Logger.appendln("When in Display Mode, Press ESC to quit, F5 to force refresh configuration or F1 to edit local configuration", Logger.LOGTYPE_INFO);
                Logger.appendln("", Logger.LOGTYPE_INFO);
                Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
                if (Config.testServerConnection()) {

                } else {
                    Logger.appendln("Could not connect to server \"" + Config.getServer() + "\"!", Logger.LOGTYPE_ERROR);
                }
                ImageTools.resizeAllImages(false);
            }
        });
        init.setName("INITIALIZING");
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
