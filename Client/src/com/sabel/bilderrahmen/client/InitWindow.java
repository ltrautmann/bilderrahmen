package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.Config.Config;
import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageService;
import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageTools;
import com.sabel.bilderrahmen.client.utils.panels.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by you shall not pass on 24.02.2017.
 */
public class InitWindow extends JFrame {
    private JScrollPane jScrollPaneConsoleOutput;
    private MessageConsole messageConsole;
    private JTextPane jTextPaneConsoleOutput;
    private Container c;
    private boolean displayImages;

    public InitWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Initializing...");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {


        initComponents();
        initEvents();
    }

    private void initComponents() {
        c = getContentPane();
        jTextPaneConsoleOutput = new JTextPane();
        jScrollPaneConsoleOutput = new JScrollPane(jTextPaneConsoleOutput);
        messageConsole = new MessageConsole(jTextPaneConsoleOutput);
        messageConsole.redirectOut(null, System.out);
        messageConsole.redirectErr(Color.RED, System.err);
        messageConsole.setMessageLines(1000);
        c.add(jScrollPaneConsoleOutput);
        this.setVisible(true);
        Config.setConfigDefault();

        try {
            if (Config.getConfigReaderWriter().readInitialConfig()) {
                displayImages = true;
            } else {
                displayImages = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (displayImages) {
            try {
                ImageTools.resizeAllImages(false);
                Config.setImageService(new ImageService(ImageTools.getResizedImages()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (displayImages) {
            Window.setDisplayImages(true);
        } else {
            Window.setDisplayImages(false);
        }
        this.setVisible(false);
        new Window();
    }

    private void initEvents() {

    }
}
