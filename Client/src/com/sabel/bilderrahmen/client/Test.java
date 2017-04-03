package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.Logger.Logger;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by you shall not pass on 10.03.2017.
 */
public class Test extends JFrame {
    public static void main(String[] args) {
        new Test();
    }

    public Test() throws HeadlessException {
        Container c = getContentPane();
        JTextArea jTextArea = new JTextArea();
        JProgressBar jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jTextArea.setEditable(false);
        JPanel jPanel = new JPanel(new BorderLayout());
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jPanel.add(jProgressBar, BorderLayout.NORTH);
        jPanel.add(jScrollPane, BorderLayout.CENTER);
        c.add(jPanel);
        setTitle("Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            Logger logger = new Logger(jTextArea, jProgressBar);
            for (int i = 0; i < 20; i++) {
                logger.resetProgressBar(4);
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                logger.append("Text" + i + "\n");
            }
            TimeUnit.MILLISECONDS.sleep(20);
            logger.append("#####################################################################################################################################################################\n");
            TimeUnit.MILLISECONDS.sleep(20);
            /*for (int i = 50; i < 100; i++) {
                logger.resetProgressBar(4);
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                TimeUnit.MILLISECONDS.sleep(50);
                logger.updateProgressBar();
                logger.append("Text" + i + "\n");
            }*/
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(100);
                logger.resetProgressBar(10);
                logger.append("Testtext" + i);
                for (int j = 0; j < 10; j++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    logger.updateProgressBar();
                    logger.append(".");
                }
                logger.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
