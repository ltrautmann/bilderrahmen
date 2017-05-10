package com.sabel.bilderrahmen.Client.utils.helper;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

/**
 * Created by Robin on 10.05.2017.
 */
public class MouseMover implements Runnable {
    private static Robot hal;

    static {
        try {
            Robot hal = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void runit()

    {
        while (!Thread.currentThread().isInterrupted()) {
            hal.delay(1000);
            int x = MouseInfo.getPointerInfo().getLocation().x ^ 10; // + - 1 Pixel
            int y = MouseInfo.getPointerInfo().getLocation().y ^ 10; // + - 1 Pixel
            hal.mouseMove(x, y);
        }
    }

    @Override
    public void run() {
        runit();

    }
}

