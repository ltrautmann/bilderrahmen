package com.sabel.bilderrahmen.client.utils.Windows;

import com.sabel.bilderrahmen.client.utils.Config.Config;
import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageService;
import com.sabel.bilderrahmen.client.utils.panels.ImagePanel;
import com.sabel.bilderrahmen.client.utils.panels.MenuPanel;

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
    private static boolean displayImages;
    ImageService imageService;



    public MainWindow(){
        init();
    }

    private void testimages() {
        System.out.println("run testimages");
        imageService = Config.getImageService();
        Random r = new Random();

        try {
            Image curImg = imageService.getImage(0);
            while (true) {
                //TimeUnit.SECONDS.sleep(1);
                TimeUnit.MILLISECONDS.sleep(r.nextInt(1900)+100);
                curImg = imageService.next(curImg);
                //imagePanel.setImage(curImg);
                imagePanel.setImage(imageService.randomImage());
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
        if (displayImages) {
            testimages();
        }
    }

    private void initComponents(){
        System.out.println("init components");
        if (displayImages) {
            imagePanel = new ImagePanel();
            c.add(imagePanel);
        } else {
        }
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
                }
            }
        });
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

    public static boolean isDisplayImages() {
        return displayImages;
    }

    public static void setDisplayImages(boolean displayImages) {
        MainWindow.displayImages = displayImages;
    }
}
