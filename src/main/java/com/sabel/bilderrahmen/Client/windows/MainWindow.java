package com.sabel.bilderrahmen.Client.windows;

import com.sabel.bilderrahmen.Client.Main;
import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.image.ImageService;
import com.sabel.bilderrahmen.Client.utils.image.SavedImage;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.panels.ImagePanel;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class MainWindow extends JFrame {
    private Container c;
    private Thread imageUpdateThread;
    private Thread configUpdateThread;
    private ImagePanel imagePanel;
    private ImageService imageService;

    public MainWindow() {
        init();
    }

    private void init() {
        initComponents();
        initEvents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        fullScreen(this, false);
        start();
    }

    private void start() {
        imageService = Config.getImageService();
        imageUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.registerThread(Thread.currentThread());
                try {
                    SavedImage img = null;
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            if (Config.isRandomImageOrder()) {
                                img = imageService.randomImage();
                                imagePanel.setImage(ImageService.accessImage(img.getPath(), null, null));
                            } else {
                                img = imageService.next(img);
                                imagePanel.setImage(ImageService.accessImage(img.getPath(), null, null));
                            }
                        } catch (IOException e) {
                            Logger.appendln("Could not read image: " + e.getMessage(), Logger.LOGTYPE_ERROR);
                        }
                        TimeUnit.MILLISECONDS.sleep(img.getDisplayTime());
                    }
                } catch (InterruptedException e) {
                    Logger.appendln("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
                }
                Logger.appendln("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
            }
        });
        configUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.registerThread(Thread.currentThread());
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    try {
                        Thread.sleep(1000 * Config.getConfigUpdateInterval());
                        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                        Config.readServerConfig();
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    } catch (InterruptedException e) {
                        Logger.appendln("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
                    }
                }
                Logger.appendln("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
            }
        });
        imageUpdateThread.setName("IMAGEUPDATE");
        configUpdateThread.setName("CONFIGUPDATE");

    }

    private void initComponents() {
        c = getContentPane();
        imagePanel = new ImagePanel();
        c.add(imagePanel);
    }

    private void initEvents() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Main.quit();
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    //TODO:Refresh config
                    System.out.println("refreshing config...");
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    System.out.println("Manual Configuration Edit requested...");
                    Main.setConfigWindow(new ConfigWindow());
                    //TODO: Properly dispose Main Window
                    Main.setMainWindow(null);
                }
            }
        });
    }

    static private boolean fullScreen(final JFrame frame, boolean doPack) {

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        boolean result = device.isFullScreenSupported();

        if (result) {
            frame.setUndecorated(true);
            frame.setResizable(true);

            frame.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent arg0) {
                    frame.setAlwaysOnTop(true);
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    frame.setAlwaysOnTop(false);
                }
            });

            if (doPack)
                frame.pack();

            device.setFullScreenWindow(frame);
        } else {
            frame.setPreferredSize(frame.getGraphicsConfiguration().getBounds().getSize());

            if (doPack)
                frame.pack();

            frame.setResizable(true);

            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            boolean successful = frame.getExtendedState() == Frame.MAXIMIZED_BOTH;

            frame.setVisible(true);

            if (!successful)
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        return result;
    }
}
