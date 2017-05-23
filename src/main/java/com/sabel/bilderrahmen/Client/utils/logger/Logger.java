package com.sabel.bilderrahmen.Client.utils.logger;

import com.sabel.bilderrahmen.Client.utils.config.Config;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.*;

/**
 * Created by CheaterLL on 05.04.2017.
 */
public class Logger {
    private static FileWriter fw;
    private static String logFile;
    private static ThreadPoolExecutor loggerExecutor;
    private static ThreadPoolExecutor progressBarExecutor;
    private static ThreadPoolExecutor fileWriterExecutor;
    private static long loggerThreadCount;
    private static long progressBarThreadCount;
    private static long fileWriterThreadCount;
    private static JTextArea jTextArea;
    private static JProgressBar jProgressBar;
    private static boolean wasLastCharNewLine = true;
    private static boolean useLogFile = false;
    private static Thread flushFileWriter;
    public static String LOGTYPE_INFO = "INFO";
    public static String LOGTYPE_WARNING = "WARNING";
    public static String LOGTYPE_ERROR = "ERROR";
    public static String LOGTYPE_FATAL = "FATAL";

    public static synchronized void initLogger(JTextArea jTextArea, JProgressBar jProgressBar) {
        Logger.jTextArea = jTextArea;
        Logger.jProgressBar = jProgressBar;
        Logger.loggerExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.DAYS, new LinkedBlockingQueue<>(), r -> new Thread(r, "Logger" + ++loggerThreadCount));
        Logger.progressBarExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.DAYS, new LinkedBlockingQueue<>(), r -> new Thread(r, "ProgressBar" + ++progressBarThreadCount));
        Logger.appendln("Logging activated", LOGTYPE_INFO);
    }

    public static void initLogFile() {
        String timestamp = new Timestamp(System.currentTimeMillis()).toString().replace(':', '-').replace(' ', '_');
        logFile = Config.getLocalLogDir() + "bilderrahmen-clientv2-log_" + timestamp + ".txt";
        fileWriterExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.DAYS, new LinkedBlockingQueue<>(), r -> new Thread(r, "LogfileWriter" + ++fileWriterThreadCount));
        fileWriterExecutor.execute(() -> {
            try {
                fw = new FileWriter(logFile);
                useLogFile = true;
            } catch (IOException e) {
                appendln("", LOGTYPE_ERROR);
            }
        });
        flushFileWriter = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(500);
                    if (!fileWriterExecutor.isShutdown()) {
                        fileWriterExecutor.execute(() -> {
                            try {
                                fw.flush();
                            } catch (IOException e) {
                                useLogFile = false;
                                Logger.appendln("Failed to write to Log File at " + logFile, LOGTYPE_ERROR);
                                useLogFile = true;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    logProgramExit("Thread was interrupted, stopping use of LogFile.", LOGTYPE_INFO);
                    closeFW();
                }
            }
            closeFW();
        });
        flushFileWriter.setName("LogfileWriter");
        flushFileWriter.start();
        Logger.appendln("Using logfile at \"" + logFile + "\".", LOGTYPE_INFO);
    }

    private static synchronized void closeFW() {
        useLogFile = false;
        if (fw != null) {
            try {
                fw.close();
                logProgramExit("Successfully closed logfile.", LOGTYPE_INFO);
            } catch (IOException e) {
                logProgramExit("Could not close Logfile. It will be forcibly closed later, but may be missing several lines.", LOGTYPE_WARNING);
            }
        }
        fileWriterExecutor.shutdownNow();
    }

    public static synchronized void append(CharSequence c, String logtype) {
        String threadName = Thread.currentThread().getName();
        try {
            loggerExecutor.execute(() -> {
                String out = c.toString();
                String prefix = "[" + new Timestamp(System.currentTimeMillis()) + "] " + logtype + " (" + threadName + "): ";

                if (wasLastCharNewLine) {
                    out = prefix + out;
                }
                if (out.charAt(out.length() - 1) == "\n".charAt(0)) {
                    wasLastCharNewLine = true;
                    out = out.substring(0, out.length() - 1).replace("\n", "\n" + prefix) + "\n";
                } else {
                    wasLastCharNewLine = false;
                    out = out.replace("\n", "\n" + prefix);
                }
                if (useLogFile) {
                    String finalOut = out;
                    if (!fileWriterExecutor.isShutdown()) {
                        fileWriterExecutor.execute(() -> {
                            try {
                                fw.append(finalOut);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
                jTextArea.append(out);
                System.out.print(out);
            });
        } catch (RejectedExecutionException e) {
            System.out.println("Something tried to log after logger was shut down, this is the message to log: " + "[" + new Timestamp(System.currentTimeMillis()) + "] " + logtype + " (" + threadName + "): " + c);
        }
    }

    public static synchronized void appendln(CharSequence c, String logtype) {
        append(c + "\n", logtype);
    }

    public static synchronized void resetProgressBar(int maxVal) {
        if (!progressBarExecutor.isShutdown()) {
            progressBarExecutor.execute(() -> {
                jProgressBar.setValue(0);
                if (maxVal > 0) {
                    jProgressBar.setMaximum(maxVal);
                } else {
                    jProgressBar.setMaximum(1);
                }
            });
        }
    }

    public static synchronized void updateProgressBar() {
        if (!progressBarExecutor.isShutdown()) {
            progressBarExecutor.execute(() -> {
                if (jProgressBar.getValue() < jProgressBar.getMaximum()) {
                    jProgressBar.setValue(jProgressBar.getValue() + 1);
                }
            });
        }
    }

    public static synchronized void dispose() throws InterruptedException {
        if (useLogFile) {
            flushFileWriter.interrupt();
        }
        Thread.sleep(50);
        logProgramExit("Logger exiting.", LOGTYPE_INFO);
        loggerExecutor.shutdownNow();
        progressBarExecutor.shutdownNow();
    }

    public static synchronized void logProgramExit(CharSequence c, String logtype) {
        String out = c.toString();
        String prefix = "[" + new Timestamp(System.currentTimeMillis()) + "] " + logtype + " (" + Thread.currentThread().getName() + "): ";
        out = prefix + out;
        if (!wasLastCharNewLine) {
            out = "\n" + out;
        }
        System.out.println(out);
    }
}
