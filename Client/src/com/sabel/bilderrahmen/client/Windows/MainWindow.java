package com.sabel.bilderrahmen.client.Windows;

import com.sabel.bilderrahmen.client.Test;
import com.sabel.bilderrahmen.client.utils.Config.Config;
import com.sabel.bilderrahmen.client.utils.Config.ConfigUpdater;
import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageService;
import com.sabel.bilderrahmen.client.utils.panels.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class MainWindow extends JFrame {
    private Container c;
    private ImagePanel imagePanel;
    private ImageService imageService;
    private ConfigUpdater updater;
    private Thread updaterThread;
    private boolean isTerminated;

    public MainWindow(){
        init();
    }

    private void testimages() {
        System.out.println("run testimages");
        imageService = Config.getImageService();
        Random r = new Random();
        //TODO: Neuen Thread erstellen für regelmäßiges Config überprüfen
        updater = new ConfigUpdater("Raspi Bilderrahmen Update-Service");
        updaterThread = updater.start();
        isTerminated = false;
        try {
            Image curImg = imageService.getImage(0);
            while (!isTerminated) {
                //TimeUnit.SECONDS.sleep(1);
                TimeUnit.MILLISECONDS.sleep(r.nextInt(1900)+100);
                curImg = imageService.next(curImg);
                //imagePanel.setImage(curImg);
                try {
                    imagePanel.setImage(imageService.randomImage());
                } catch (NullPointerException e) {
                    System.out.println("Tried to update with nonexistent image. Skipping image update for this cycle.");
                    System.out.println("This is probably caused by the list of images being updated currently.");
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        c = getContentPane();
        initComponents();
        initEvents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        fullScreen(this, false);
        testimages();
    }

    private void initComponents(){
        System.out.println("init components");
        imagePanel = new ImagePanel();
        c.add(imagePanel);
    }

    private void initEvents(){
        System.out.println("init events");
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    //TODO:Refresh config
                    System.out.println("refreshing config...");
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    System.out.println("Manual Configuration Edit requested...");
                    Test.setConfigWindow(new ConfigWindow());
                    MainWindow.this.close();
                }
            }
        });
    }

    private void close(){
        isTerminated = true;
        MainWindow.this.setVisible(false);
        updaterThread.interrupt();
        MainWindow.this.dispose();
        Test.setMainWindow(null);
        System.out.println("Image Display Window shut down");

    }

    static public boolean fullScreen(final JFrame frame, boolean doPack) {

        GraphicsDevice device = frame.getGraphicsConfiguration().getDevice();
        boolean result = device.isFullScreenSupported();

        if (result) {
            frame.setUndecorated(true);
            frame.setResizable(true);
/*
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
*/
            if (doPack)
                frame.pack();

            device.setFullScreenWindow(frame);
        }
        else {
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
