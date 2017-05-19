package com.sabel.bilderrahmen.Admin.services;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.swing.JOptionPane;
import java.io.*;

/**
 * Created by robin on 07.03.17.
 */
public class FtpService {
    private static FtpService instance;
    private static String loginName;
    private static String loginSerever;
    private static String loginPassword;
    private FTPClient ftpClient;

    private FtpService() {
        ftpClient = new FTPClient();
        login();

    }

    public static FtpService getInstance(String name, String password, String server) {

        if (instance != null) {
            JOptionPane.showConfirmDialog(null, "Bereits verbunden!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return getInstance();

        }

        loginName = name;
        loginPassword = password;
        loginSerever = server;
        return getInstance();

    }

    public static FtpService getInstance() {
        if (instance == null)
            instance = new FtpService();
        return instance;
    }

    private void login() {
        try {
            ftpClient.connect(loginSerever);//"ftp.strato.de");
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.err.println("FTP server refused connection.");
            }
            ftpClient.login(loginName, loginPassword);//"bilderrahmen@bilderrahmen.cheaterll.de", "Kennwort0");
            if (ftpClient.isConnected())
                System.out.println("FTP Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(String folder, File file) {
        try {
            if (!ftpClient.isConnected()) {
                login();
            }
            ftpClient.changeWorkingDirectory(folder);
            FileInputStream fi = new FileInputStream(file);
            ftpClient.setFileTransferMode(FTP.BLOCK_TRANSFER_MODE);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(file.getName().replace(' ', '_'), fi);
            fi.close();
            //file.delete();
            // ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile(String path) {
        if (!ftpClient.isConnected()) {
            login();
        }
        File file = new File("tmp");
        try {

            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(file));
            ftpClient.retrieveFile(path, outputStream1);
            outputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public FTPFile[] getFolder(String folder) {
        if (!ftpClient.isConnected()) {
            login();
        }
        FTPFile[] ftpFile = null;
        try {
            ftpClient.enterLocalPassiveMode();

            ftpFile = ftpClient.listFiles(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpFile;

    }

    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                System.out.println("FTP disconnected");
            } catch (IOException ioe) {
                // swallow exception
                System.err.println("Cannot disconnect from the host");
            }
        }
    }

    public boolean delFile(String pfad) {
        if (!ftpClient.isConnected()) {
            login();
        }
        try {
            return ftpClient.deleteFile(pfad);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
