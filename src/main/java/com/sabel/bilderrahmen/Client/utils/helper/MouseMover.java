package com.sabel.bilderrahmen.Client.utils.helper;

import java.awt.*;

/**
 * Created by Robin on 10.05.2017.
 */
public class MouseMover implements Runnable {
    private static Robot WallE;

    static {
        try {
            WallE = new Robot();
            WallE.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    public static void runit() {
        while (!Thread.currentThread().isInterrupted()) {
            WallE.delay(1000);
            int x = MouseInfo.getPointerInfo().getLocation().x ^ 1; // + - 1 Pixel
            int y = MouseInfo.getPointerInfo().getLocation().y ^ 1; // + - 1 Pixel
            WallE.mouseMove(x, y);
        }
    }

    @Override
    public void run() {
        runit();

    }
}

