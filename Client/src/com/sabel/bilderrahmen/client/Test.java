package com.sabel.bilderrahmen.client;

import java.io.File;

/**
 * Created by you shall not pass on 10.03.2017.
 */
public class Test {
    public static void main(String[] args) {
        File f = new File("D:\\testfile.txt");
        f.delete();
        System.out.println(f.getPath());
    }
}
