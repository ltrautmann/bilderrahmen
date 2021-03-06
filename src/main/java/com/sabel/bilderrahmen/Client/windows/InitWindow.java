package com.sabel.bilderrahmen.Client.windows;

import com.sabel.bilderrahmen.Client.Main;
import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.image.ImageTools;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.usb.USBService;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.HttpURLConnection;

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
        Thread init = new Thread(() -> {
            Logger.initLogger(jTextArea, jProgressBar);
            Config.init();
            Logger.initLogFile();
            Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
            Logger.appendln("Raspi Bilderrahmen", Logger.LOGTYPE_INFO);
            Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
            Logger.appendln("", Logger.LOGTYPE_INFO);
            Logger.appendln("When in Display Mode, Press ESC to quit or F5 to force refresh configuration", Logger.LOGTYPE_INFO);
            Logger.appendln("", Logger.LOGTYPE_INFO);
            Logger.appendln("########################################################################################################################", Logger.LOGTYPE_INFO);
            int serverResponseCode = Config.testServerConnection();
            if (serverResponseCode == HttpURLConnection.HTTP_OK) {
                Logger.appendln("Server Connection OK (Response Code " + serverResponseCode + ")", Logger.LOGTYPE_INFO);
                if (Config.readServerConfig()) {
                    if (Config.isUsbEnabled()) {
                        Logger.appendln("Reading connected storage media.", Logger.LOGTYPE_INFO);
                        USBService.update();
                    } else {
                        Logger.appendln("USB is disabled.", Logger.LOGTYPE_INFO);
                    }
                    ImageTools.resizeAllImages(false);
                    Logger.appendln("Starting Display.", Logger.LOGTYPE_INFO);
                    Main.setMainWindow(new MainWindow());
                }
            } else {
                Logger.appendln("Could not connect to server \"" + Config.getServer() + "\"! HTTP ERROR " + serverResponseCode, Logger.LOGTYPE_ERROR);
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
