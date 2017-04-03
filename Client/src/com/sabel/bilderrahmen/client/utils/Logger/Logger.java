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
    private JProgressBar jProgressBar;
    private boolean wasLastCharNewLine;
    private boolean useLogFile = true;

    public Logger(JTextArea jTextArea, JProgressBar jProgressBar) {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '-').replace(' ', '_');
        logFileName = Config.getLocalConfigDir() + "bilderrahmen-client-log_" + timestamp + ".txt";
        this.jTextArea = jTextArea;
        this.jProgressBar = jProgressBar;
        executor = Executors.newSingleThreadExecutor();
        wasLastCharNewLine = true;
        try {
            fw = new FileWriter(logFileName);
        }catch (Exception e){
            useLogFile = false;
        }
        if (useLogFile) {
            appendln("Logger started, but could not write to logfile at \"" + useLogFile + "\"");
        } else {
            appendln("Logger started.");
            //flush filewriter in regular intervals
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.interrupted()) {
                        try {
                            Thread.sleep(500);
                            fileWriter(null); //flush FileWriter thread-safe with synchronized method
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private synchronized void fileWriter(String output) throws IOException {
        if (output == null) {
            fw.flush();
        } else {
            fw.append(output);
        }
    }

    public synchronized void append(CharSequence c) {
        String threadName = Thread.currentThread().getName();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String out = c.toString();
                    if (wasLastCharNewLine) {
                        out = threadName + "|" + out;
                    }
                    wasLastCharNewLine = false;
                    if (out.charAt(out.length() - 1) == "\n".charAt(0)) {
                        wasLastCharNewLine = true;
                        out = out.substring(0, out.length() - 1).replace("\n", "\n" + threadName + "|") + "\n";
                    } else {
                        out = out.replace("\n", "\n" + threadName + "|");
                    }
                    if (useLogFile) {
                        fileWriter(out); //append to FileWriter thread-safe with synchronized method
                    }
                    jTextArea.append(out);
                    System.out.print(out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void appendln(CharSequence c) {
        if (!wasLastCharNewLine) {
            c = "\n" + c;
        }
        append(c + "\n");
    }

    public synchronized void resetProgressBar(int maxVal) {
        jProgressBar.setValue(0);
        jProgressBar.setMaximum(maxVal);
    }

    public synchronized void updateProgressBar() {
        jProgressBar.setValue(jProgressBar.getValue() + 1);
    }
}
