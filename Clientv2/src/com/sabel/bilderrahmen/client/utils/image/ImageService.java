package com.sabel.bilderrahmen.client.utils.image;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class ImageService {
    private List<SavedImage> images;
    private Random random;

    public ImageService() {
        images = new ArrayList<>();
    }

    public ImageService(List<SavedImage> imagePaths) {
        this.images = imagePaths;
    }

    public void addImage(SavedImage image) {
        images.add(image);
    }

    public SavedImage getImage(int index) throws IOException {
        return (index >= 0 && index < images.size()) ? images.get(index) : null;
    }

    public int indexOfImage(SavedImage i) {
        return images.indexOf(i);
    }

    public void removeImage(int index) {
        images.remove(index);
    }

    public void removeImage(SavedImage image) {
        removeImage(images.indexOf(image));
    }

    public SavedImage next(SavedImage image) throws IOException {
        int index = images.indexOf(image);
        if (index < 0) {
            index = 0;
        }
        return (index == images.size() - 1) ? images.get(0) : images.get(index + 1);
    }

    public SavedImage previous(SavedImage image) throws IOException {
        int index = images.indexOf(image);
        if (index < 0) {
            index = 0;
        }
        return (index == 0) ? images.get(images.size() - 1) : images.get(index - 1);
    }

    public SavedImage randomImage() throws IOException {
        if (random == null) random = new Random();
        return images.get(random.nextInt(images.size() - 1));
    }

    public static synchronized Image accessImage(String path, RenderedImage image, String imageFormat) throws IOException {
        if (path != null) {
            if (image == null) {
                return ImageIO.read(new File(path));
            } else {
                ImageIO.write(image, imageFormat, new File(path));
            }
        }
        return null;
    }
}
