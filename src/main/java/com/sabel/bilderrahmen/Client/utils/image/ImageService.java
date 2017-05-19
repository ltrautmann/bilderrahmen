package com.sabel.bilderrahmen.Client.utils.image;

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

    public List<SavedImage> getImages() {
        return images;
    }

    public void setImages(List<SavedImage> images) {
        this.images = images;
    }

    public void addImage(SavedImage image) {
        images.add(image);
    }

    public SavedImage getImage(int index) throws IOException {
        return (index >= 0 && index < images.size()) ? images.get(index) : null;
    }

    public int size() {
        return images.size();
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
        if (size() < 1) {
            return null;
        }
        if (image == null) {
            return images.get(0);
        }
        if (!images.contains(image)) {
            return null;
        }
        int index = images.indexOf(image);
        return (index == images.size() - 1) ? images.get(0) : images.get(index + 1);
    }

    public SavedImage previous(SavedImage image) throws IOException {
        if (size() < 1) {
            return null;
        }
        if (image == null) {
            return images.get(0);
        }
        if (!images.contains(image)) {
            return null;
        }
        int index = images.indexOf(image);
        return (index == images.size() - 1) ? images.get(images.size()-1) : images.get(index - 1);
    }

    public SavedImage randomImage() throws IOException {
        if (random == null) random = new Random();
        if (size() < 1) {
            return null;
        }
        return images.get(random.nextInt(images.size()));
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

    public boolean contains(String name) {
        if (name != null && !"".equals(name)) {
            for (SavedImage s : images) {
                if (name.equals(s.getPicture_properties().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int find(String name) {
        if (name != null && !"".equals(name)) {
            for (SavedImage s : images) {
                if (name.equals(s.getPicture_properties().getName())) {
                    return images.indexOf(s);
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImageService");
        for (SavedImage s : images) {
            sb.append("|");
            sb.append(s);
        }
        return sb.toString();
    }
}
