package com.sabel.bilderrahmen.client.utils.image;

import com.sabel.bilderrahmen.client.utils.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class ImageService {
    private List<String> images;
    private Random random;

    public ImageService() {
        images = new ArrayList<>();
    }

    public ImageService(List<String> imagePaths) {
        this.images = imagePaths;
    }

    public void addImage(String imagePath) {
        images.add(imagePath);
    }

    public Image getImage(int index) {
        return (index >= 0 && index < images.size()) ? readImage(images.get(index)) : null;
    }

    public int indexOfImage(Image i) {
        return images.indexOf(i);
    }

    public void removeImage(int index) {
        images.remove(index);
    }

    public void removeImage(Image image) {
        removeImage(images.indexOf(image));
    }

    public Image next(Image image) {
        int index = images.indexOf(image);
        return readImage((index == images.size() - 1) ? images.get(0) : images.get(index + 1));
    }

    public Image previous(Image image) {
        int index = images.indexOf(image);
        return readImage((index == 0) ? images.get(images.size() - 1) : images.get(index - 1));
    }

    public Image randomImage() {
        if (random == null) random = new Random();
        return readImage(images.get(random.nextInt(images.size() - 1)));
    }

    public int size() {
        return images.size();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    private Image readImage(String path) {
        if (path != null) {
            try {
                return ImageIO.read(new File(path));
            } catch (IOException e) {
                Logger.appendln("Image \"" + path + "\" could not be loaded.", Logger.LOGTYPE_INFO);
                return null;
            }
        }
        return null;
    }
}
