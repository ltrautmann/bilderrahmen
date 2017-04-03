package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.Logger.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by you shall not pass on 10.03.2017.
 */
public class Test extends JFrame{
    public static void main(String[] args) {
        new Test();
    }

    public Test() throws HeadlessException {
        Container c = getContentPane();
        JTextArea jTextArea = new JTextArea();
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)jTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jTextArea.setEditable(false);
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(jTextArea);
        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        c.add(jScrollPane);
        setTitle("Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);
        setVisible(true);
        try {
            Logger logger = new Logger(jTextArea);
            for (int i = 0; i < 51; i++) {
                logger.append("Text" + i + "\n");
                TimeUnit.MILLISECONDS.sleep(20);
            }
            logger.append("#####################################################################################################################################################################\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
