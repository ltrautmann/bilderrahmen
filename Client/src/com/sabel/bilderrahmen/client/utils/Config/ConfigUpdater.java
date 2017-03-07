package com.sabel.bilderrahmen.client.utils.Config;

import com.sabel.bilderrahmen.client.utils.ImageDisplay.ImageTools;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by you shall not pass on 07.03.2017.
 */
public class ConfigUpdater implements Runnable {
    private String threadName;
    private Thread t;

    public ConfigUpdater(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                Thread.sleep(Config.getConfigUpdateInterval() * 1000);
                System.out.println(threadName + ": Updating Configuration (" + new Timestamp(System.currentTimeMillis()) + ")");
                //TODO: Config updaten
                ImageTools.resizeAllImages(false);
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                Config.getImageService().setImages(ImageTools.getResizedImagePaths()); //Set max thread priority while updating the ImageService List
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + ": Thread was interrupted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        if (t==null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
