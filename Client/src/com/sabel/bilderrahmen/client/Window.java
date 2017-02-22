package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.ConfigReaderWriter;
import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageService;
import com.sabel.bilderrahmen.client.utils.ImageTools;
import com.sabel.bilderrahmen.client.utils.panels.ImagePanel;
import com.sabel.bilderrahmen.client.utils.panels.MenuPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by you shall not pass on 10.02.2017.
 */
public class Window extends JFrame {
    private ConfigReaderWriter configReaderWriter;
    private List<Image> images;
    private Container c;
    private JPanel menuPanelParent;
    private ImagePanel imagePanel;
    private ImageService imageService;



    public Window(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        fullScreen(this, false);
        imageService = new ImageService();
        testimages();
    }

    private void testimages() {
        test.testimages();
        try {
            for (String s : test.testimages) {
                imageService.addImage(ImageTools.resizeImage(ImageIO.read(new File(s))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        configReaderWriter = new ConfigReaderWriter();
        initComponents();
        initEvents();
        try {
            if (configReaderWriter.readInitialConfig()) {
                c.add(imagePanel);
            } else {
                c.add(menuPanelParent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents(){
        c = getContentPane();
        menuPanelParent = new JPanel();
        menuPanelParent.setLayout(new GridBagLayout());
        menuPanelParent.add(new MenuPanel(configReaderWriter));
        imagePanel = new ImagePanel();
    }

    private void initEvents(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    //TODO:Refresh config
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
