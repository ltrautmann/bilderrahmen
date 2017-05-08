package com.sabel.bilderrahmen.client.windows;

import com.sabel.bilderrahmen.client.Main;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class MainWindow extends JFrame {
    private Container c;
    private Thread updaterThread;

    public MainWindow() {
        init();
    }

    private void init() {
        c = getContentPane();
        initComponents();
        initEvents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        fullScreen(this, false);
        testimages();
    }

    private void testimages() {

    }

    private void initComponents() {

    }

    private void initEvents() {
        System.out.println("init events");
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
