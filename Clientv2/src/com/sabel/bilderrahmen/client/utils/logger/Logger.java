package com.sabel.bilderrahmen.client.utils.logger;

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

    public Logger(JTextArea jTextArea, JProgressBar jProgressBar) {
        this.jTextArea = jTextArea;
        this.jProgressBar = jProgressBar;
        this.loggerExecutor = Executors.newSingleThreadExecutor();
        this.progressBarExecutor = Executors.newSingleThreadExecutor();
    }

    public void initLogFile() throws IOException {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '-').replace(' ', '_');
        logFile = Config.getLocalConfigDir() + "bilderrahmen-client-log_" + timestamp + ".txt";
        this.fileWriterExecutor = Executors.newSingleThreadExecutor();
        fileWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    fw = new FileWriter(logFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(500);
                        fileWriterExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fw.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (Thread.interrupted()) {
                    if (fw != null) {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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
                if (out.charAt(out.length() - 1) == "\n".charAt(0)) {
                    wasLastCharNewLine = true;
                    out = out.substring(0, out.length() - 1).replace("\n", "\n" + threadName + "|") + "\n";
                } else {
                    out = out.replace("\n", "\n" + threadName + "|");
                }
                if (useLogFile) {
                    String finalOut = out;
                    fileWriterExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                fw.append(finalOut);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                jTextArea.append(out);
                System.out.println(out);
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
        try {
            loggerExecutor.shutdownNow();
            fileWriterExecutor.shutdownNow();
            fw.close();
            progressBarExecutor.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
