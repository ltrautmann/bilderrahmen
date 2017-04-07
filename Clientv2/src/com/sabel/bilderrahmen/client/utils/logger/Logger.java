package com.sabel.bilderrahmen.client.utils.logger;

import com.sabel.bilderrahmen.client.Main;
import com.sabel.bilderrahmen.client.utils.config.Config;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Logger {
    private FileWriter fw;
    private String logFile;
    private ExecutorService loggerExecutor;
    private ExecutorService progressBarExecutor;
    private ExecutorService fileWriterExecutor;
    private JTextArea jTextArea;
    private JProgressBar jProgressBar;
    private boolean wasLastCharNewLine = true;
    private boolean useLogFile = false;
    public static final String NEWLINE = "\n";
    public static final String LINE_SEPARATOR = "###############################################################################################################";
    public static final String EMPTY = "";

    public Logger(JTextArea jTextArea, JProgressBar jProgressBar) {
        this.jTextArea = jTextArea;
        this.jProgressBar = jProgressBar;
        this.loggerExecutor = Executors.newSingleThreadExecutor();
        this.progressBarExecutor = Executors.newSingleThreadExecutor();
    }

    public void initLogFile() {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '-').replace(' ', '_');
        logFile = Config.getLocalConfigDir() + "bilderrahmen-client-log_" + timestamp + ".txt";
        this.fileWriterExecutor = Executors.newSingleThreadExecutor();
        fileWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    fw = new FileWriter(logFile);
                    useLogFile = true;
                } catch (IOException e) {
                    appendln("Logger could not initialize FileWriter for Log File. Continuing without Log File.");
                    useLogFile = false;
                }
            }
        });
        Thread flushFileWriter = new Thread(new Runnable() {
            @Override
            public void run() {
                Main.threadList.add(Thread.currentThread());
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(500);
                        fileWriterExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fw.flush();
                                } catch (IOException e) {
                                    appendln("WARNING: Could not flush to Log File.");
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        dispose();
                        System.out.println("Logger finished exiting");
                    }
                }
                dispose();
            }
        });
        flushFileWriter.setName("Flush-FileWriter");
        flushFileWriter.start();
    }

    public synchronized void append(CharSequence c) {
        String threadName = Thread.currentThread().getName();
        loggerExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String out = c.toString();
                if (wasLastCharNewLine) {
                    out = threadName + "|" + out;
                }
                wasLastCharNewLine = false;
                if (out.charAt(out.length() - 1) == NEWLINE.charAt(0)) {
                    wasLastCharNewLine = true;
                    out = out.substring(0, out.length() - 1).replace(NEWLINE, NEWLINE + threadName + "|") + NEWLINE;
                } else {
                    out = out.replace(NEWLINE, NEWLINE + threadName + "|");
                }
                if (useLogFile) {
                    String finalOut = out;
                    fileWriterExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                fw.append(finalOut);
                            } catch (IOException e) {
                                String s = "WARNING: Could not write to Log File!\n";
                                if (!wasLastCharNewLine) {
                                    s = NEWLINE + s;
                                }
                                jTextArea.append(s);
                                System.out.println(s);
                                jTextArea.append(finalOut);
                                System.out.println(finalOut);
                            }
                        }
                    });
                }
                jTextArea.append(out);
                System.out.print(out);
            }
        });
    }

    public synchronized void appendln(CharSequence c) {
        if (!wasLastCharNewLine) {
            c = NEWLINE + c;
        }
        append(c + NEWLINE);
    }

    public synchronized void resetProgressBar(int maxVal) {
        progressBarExecutor.execute(new Runnable() {
            @Override
            public void run() {
                jProgressBar.setValue(0);
                if (maxVal > 0) {
                    jProgressBar.setMaximum(maxVal);
                } else {
                    jProgressBar.setMaximum(1);
                }
            }
        });
    }

    public synchronized void updateProgressBar() {
        progressBarExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (jProgressBar.getValue() < jProgressBar.getMaximum()) {
                    jProgressBar.setValue(jProgressBar.getValue() + 1);
                }
            }
        });
    }

    public synchronized void dispose() {
        System.out.println("Logger is exiting.");
        try {
            loggerExecutor.shutdownNow();
            fileWriterExecutor.shutdownNow();
            progressBarExecutor.shutdownNow();
            fw.close();
        } catch (IOException e) {
            System.out.println("FileWriter for Log File could not be correctly closed. This doesn't matter as it will soon be dead anyway.");
        }
    }
}
