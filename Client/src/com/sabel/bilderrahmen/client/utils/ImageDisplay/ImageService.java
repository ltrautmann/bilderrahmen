package com.sabel.bilderrahmen.client.utils.ImageDisplay;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by you shall not pass on 21.02.2017.
 */
public class ImageService {
    private List<Image> images;
    private Random random;

    public ImageService() {
        images = new ArrayList<>();
    }

    public ImageService(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public Image getImage(int index) {
        return (index >= 0 && index < images.size()) ? images.get(index) : null;
    }

    public Image removeImage(int index){
        return images.remove(index);
    }

    public Image removeImage(Image image){
        return removeImage(images.indexOf(image));
    }

    public Image next(Image image) {
        int index = images.indexOf(image);
        return (index == images.size() - 1) ? images.get(0) : images.get(index + 1);
    }

    public Image previous(Image image) {
        int index = images.indexOf(image);
        return (index == 0) ? images.get(images.size() - 1) : images.get(index - 1);
    }

    public Image randomImage(){
        if(random == null) random = new Random();
        return images.get(random.nextInt(images.size() - 1));
    }

    public int size(){
        return images.size();
    }
}
