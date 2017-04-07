package com.sabel.bilderrahmen.client.windows;

import com.sabel.bilderrahmen.client.Main;
import com.sabel.bilderrahmen.client.utils.logger.Logger;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class InitWindow extends JFrame {
    private Container c;
    private JScrollPane jScrollPane;
    private JProgressBar jProgressBar;
    private JTextArea jTextArea;

    public Logger getLogger() {
        return logger;
    }

    private Logger logger;

    public InitWindow() throws HeadlessException {
        init();
    }

    private void init() {
        initFrame();
        logger = new Logger(jTextArea, jProgressBar);
        logger.initLogFile();
        logger.appendln(Logger.LINE_SEPARATOR);
        logger.appendln("Raspi Bilderrahmen");
        logger.appendln(Logger.LINE_SEPARATOR);
        logger.appendln(Logger.EMPTY);
        logger.appendln("When in Display Mode, press ESC to quit, F5 to force refresh configuration or F1 to edit local configuration");
        logger.appendln(Logger.EMPTY);
        logger.appendln(Logger.LINE_SEPARATOR);
    }

    private void initFrame() {
        setTitle("Initializing...");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        initComponents();
        initEvents();
        setVisible(true);
    }

    private void initComponents() {
        c = getContentPane();
        jTextArea = new JTextArea();
        jScrollPane = new JScrollPane(jTextArea);
        jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        ((DefaultCaret) jTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        c.add(jProgressBar, BorderLayout.NORTH);
        c.add(jScrollPane, BorderLayout.CENTER);

    }

    private void initEvents() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("...");
                if (Main.confirmQuit(InitWindow.this)) {
                    Main.quit();
                }
            }
        });
    }
}
