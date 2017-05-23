package com.sabel.bilderrahmen.Client.windows;

import com.sabel.bilderrahmen.Client.Main;
import com.sabel.bilderrahmen.Client.utils.config.Config;
import com.sabel.bilderrahmen.Client.utils.image.ImageService;
import com.sabel.bilderrahmen.Client.utils.image.ImageTools;
import com.sabel.bilderrahmen.Client.utils.image.SavedImage;
import com.sabel.bilderrahmen.Client.utils.logger.Logger;
import com.sabel.bilderrahmen.Client.utils.panels.ImagePanel;
import com.sabel.bilderrahmen.Client.utils.usb.USBService;

import javax.swing.*;
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

    public MainWindow() {
        init();
    }

    private void init() {
        initComponents();
        initEvents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        fullScreen(this);
        start();
    }

    private void start() {
        imageUpdateThread = new Thread(() -> {
            Main.registerThread(Thread.currentThread());
            try {
                SavedImage img = null;
                if (Config.getImageService().size() < 1) {
                    Logger.appendln("No images provided to display thread. Exiting.", Logger.LOGTYPE_FATAL);
                    Main.quit();
                } else {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            if (Config.isRandomImageOrder()) {
                                img = Config.getImageService().randomImage();
                                Logger.appendln("Applying random image: \"" + img.getPicture_properties().getName() + "\" for " + img.getDisplayTime() + " Seconds.", Logger.LOGTYPE_INFO);
                                imagePanel.setImage(ImageService.accessImage(img.getResizedPath(), null, null));
                            } else {
                                img = Config.getImageService().next(img);
                                Logger.appendln("Applying next image: \"" + img.getPicture_properties().getName() + "\" for " + img.getDisplayTime() + " Seconds.", Logger.LOGTYPE_INFO);
                                imagePanel.setImage(ImageService.accessImage(img.getResizedPath(), null, null));
                            }
                        } catch (IOException e) {
                            Logger.appendln("Could not read image: " + e.getMessage(), Logger.LOGTYPE_ERROR);
                        } catch (NullPointerException e) {
                            Logger.appendln("Image to be updated was null, skipping image. This is probably due to attempting to read a new image that is currently being scaled.", Logger.LOGTYPE_WARNING);
                        }
                        if (img != null) {
                            TimeUnit.SECONDS.sleep(img.getDisplayTime());
                        } else {
                            TimeUnit.MILLISECONDS.sleep(10);
                        }
                    }
                }
            } catch (InterruptedException e) {
                Logger.logProgramExit("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
            }
            Logger.logProgramExit("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
        });
        configUpdateThread = new Thread(() -> {
            Main.registerThread(Thread.currentThread());
            while (!Thread.currentThread().isInterrupted()) {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                try {
                    Thread.sleep(1000 * Config.getConfigUpdateInterval());
                    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                    Config.readServerConfig();
                    if (Config.isUsbEnabled()) {
                        Logger.appendln("Reading connected storage media.", Logger.LOGTYPE_INFO);
                        USBService.update();
                    } else {
                        Logger.appendln("USB is disabled.", Logger.LOGTYPE_INFO);
                    }
                    ImageTools.resizeAllImages(false);
                    ImageTools.deleteObsoleteImages();
                    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                    Logger.appendln("Configuration update finished.", Logger.LOGTYPE_INFO);
                    Logger.appendln(Config.getImageService().toString(), Logger.LOGTYPE_INFO);
                    if (Config.getImageService().size() < 1) {
                        Logger.appendln("No images provided to display thread. Exiting.", Logger.LOGTYPE_FATAL);
                        Main.quit();
                    }
                } catch (InterruptedException e) {
                    Logger.logProgramExit("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
                }
            }
            Logger.logProgramExit("Thread was interrupted. Exiting.", Logger.LOGTYPE_INFO);
        });
        imageUpdateThread.setName("IMAGEUPDATE");
        configUpdateThread.setName("CONFIGUPDATE");
        imageUpdateThread.start();
        configUpdateThread.start();
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
                    Config.readServerConfig();
                    Logger.appendln("Refreshing Config.", Logger.LOGTYPE_INFO);
                }else if (e.getKeyCode() == KeyEvent.VK_F12) {
                    Main.restart();
                    Logger.appendln("Restarting.", Logger.LOGTYPE_INFO);
                }
            }
        });
    }

    static private boolean fullScreen(final JFrame frame) {

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

            device.setFullScreenWindow(frame);
        } else {
            frame.setPreferredSize(frame.getGraphicsConfiguration().getBounds().getSize());

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
