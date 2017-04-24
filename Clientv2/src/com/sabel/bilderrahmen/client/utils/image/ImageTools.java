package com.sabel.bilderrahmen.client.utils.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CheaterLL on 18.04.2017.
 */
public class ImageTools {
    private static List<String> supportedExtensions;
    private static String renamedFilePrefix = "resized-";

    static {
        supportedExtensions = new ArrayList<>();
        supportedExtensions.add("png");
        supportedExtensions.add("jpg");
        supportedExtensions.add("jpeg");
        supportedExtensions.add("bmp");
    }

    public static synchronized BufferedImage resize(BufferedImage image, boolean forceSingleStep) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        //adjust the target height or width to conserve the aspect ratio of the original image
        double aspectRatio = (double)image.getWidth() / (double)image.getHeight();
        if (aspectRatio <= ((double) width / (double) height)) {
            width = (int) (height * aspectRatio);
        } else {
            height = (int) (width / aspectRatio);
        }
        boolean multiStepDownscaling = (image.getWidth() > width && image.getHeight() > height) && !forceSingleStep;
        System.out.print(((multiStepDownscaling)?"  Downscaling":"  Upscaling") + " image of size " + image.getWidth() + "x" + image.getHeight() + " to " + width + "x" + height + " at aspect ratio of " + aspectRatio + ".");
        if (true) {
            return getScaledInstance(image, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, multiStepDownscaling);
        } else {
            BufferedImage tmp = new BufferedImage(width, height, ((image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB));
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(image, 0, 0, width, height, null);
            g2.dispose();
            g2 = null;
            image = tmp;
            return image;
        }
    }

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance, in pixels
     * @param targetHeight the desired height of the scaled instance,  in pixels
     * @param hint one of the rendering hints that corresponds to
     *                      {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *                      {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *                      {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *                      {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *                      scaling technique that provides higher quality than the usual
     *                      one-step technique (only useful in downscaling cases, where
     *                      {@code targetWidth} or {@code targetHeight} is
     *                      smaller than the original dimensions, and generally only when
     *                      the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    private static synchronized BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        int cycles = 0;
        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }
        System.out.print(".");
        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }
            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }
            System.out.print(".");
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            g2 = null;
            System.gc();
            ret = tmp;
            cycles++;
        } while (w != targetWidth || h != targetHeight);
        System.out.println(".");
        System.out.println("  Done in " + cycles + ((cycles!=1)?" cycles.":" cycle."));
        return ret;
    }

    public static List<String> getSupportedExtensions() {
        return supportedExtensions;
    }

    public static String getRenamedFilePrefix() {
        return renamedFilePrefix;
    }

    public static void setRenamedFilePrefix(String renamedFilePrefix) {
        ImageTools.renamedFilePrefix = renamedFilePrefix;
    }
}
