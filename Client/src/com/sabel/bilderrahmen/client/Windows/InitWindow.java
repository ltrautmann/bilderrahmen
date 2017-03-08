package com.sabel.bilderrahmen.client.Windows;

import com.sabel.bilderrahmen.client.Test;
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
        init();
    }

    private void init() {
        initFrame();
        initComponents();
        System.out.println("########################################################################################################################");
        System.out.println("Raspi Bilderrahmen");
        System.out.println("########################################################################################################################");
        System.out.println();
        System.out.println("When in Display Mode, Press ESC to quit, F5 to force refresh configuration or F1 to edit local configuration");
        System.out.println();
        System.out.println("########################################################################################################################");
        Config.setConfigDefault();

        try {
            if (Config.getConfigReaderWriter().readInitialConfig()) {
                try {
                    ImageTools.resizeAllImages(false);
                    Config.setImageService(new ImageService(ImageTools.getResizedImagePaths()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Test.setMainWindow(new MainWindow());
            } else {
                //MainWindow.setDisplayImages(false);
                Test.setConfigWindow(new ConfigWindow());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setVisible(false);
        this.dispose();
    }

    private void initFrame() {
        setTitle("Initializing...");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
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
    }
}
