package com.sabel.bilderrahmen.client.utils.Logger;

import com.sabel.bilderrahmen.client.utils.Config.Config;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by you shall not pass on 03.04.2017.
 */
public class Logger {
    private FileWriter fw;
    private String logFileName;
    private Executor executor;
    private JTextArea jTextArea;

    public Logger(JTextArea jTextArea) throws IOException {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '-').replace(' ', '_');
        //logFileName = Config.getLocalConfigDir() + "bilderrahmen-client-log_" + timestamp + ".txt";
        //fw = new FileWriter(logFileName);
        this.jTextArea = jTextArea;
        executor = Executors.newSingleThreadExecutor();

    }

    public synchronized void append(CharSequence c) {
        String threadName = Thread.currentThread().getName();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String out = threadName + "|" + c.toString();
                    //fw.append(out);
                    jTextArea.append(out);
                    System.out.print(out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
