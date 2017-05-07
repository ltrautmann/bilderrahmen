package com.sabel.bilderrahmen.client;

import com.sabel.bilderrahmen.client.utils.web.FileDownloader;

import java.io.IOException;

/**
 * Created by CheaterLL on 07.05.2017.
 */
public class Test {
    public static void main(String[] args) {
        printHelp();
    }

    private static void printHelp() {
        System.out.print("NAME\n\t\tbilderrahmen\n\nSYNOPSIS\n\t\tbilderrahmen [-h|--help|/h|/help|/?]\n\t\tbilderrahmen [-s|--server|/s|/server \"server url\"] [-u|--user|/u|/user \"server login name\"] [-p|--password|/p|/password \"server login password\"] [-n|--device-name|/n|/device-name \"device name\"] [-d|--directory|/d|/directory \"working directory\"]\n\nDESCRIPTION\n\tI4A Bilderrahmen-HTML Project\n\n\tOPTIONS\n\t\t-h|--help|/h|/help|/?\n\t\t\t\tDisplays this help page\n\n\t\t-s|--server|/s|/server \"server url\"\n\t\t\t\tThe server to pull configuration and images from\n\t\t\n\t\t-u|--user|/u|/user \"server login name\"\n\t\t\t\tThe login name for the server\n\t\t\n\t\t-p|--password|/p|/password \"server login password\"\n\t\t\t\tThe login password for the server\n\t\t\n\t\t-n|--device-name|/n|/device-name \"device name\"\n\t\t\t\tThe name of this device\n\n\t\t-d|--directory|/d|/directory \"working directory\"\n\t\t\t\tThe directory to use for storing configuration files, images and logs\n\t\t\nBUGS\n\t\tReport bugs to info@cheaterll.de\n\nAUTHOR\n\t\tRobin Schippan\n\t\tLukas Trautmann\n");
    }
}
