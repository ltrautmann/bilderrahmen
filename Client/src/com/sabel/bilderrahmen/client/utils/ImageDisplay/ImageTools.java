package com.sabel.bilderrahmen.client.utils.ImageDisplay;

import com.sabel.bilderrahmen.client.utils.Config.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by you shall not pass on 17.02.2017.
 */
public class ImageTools {
    private static List<String> supportedExtensions;

    static {
        supportedExtensions = new ArrayList<>();
        supportedExtensions.add("png");
        supportedExtensions.add("jpg");
        supportedExtensions.add("jpeg");
        supportedExtensions.add("bmp");
    }

    public static void resizeAllImages(boolean forceResize) throws IOException {
        int imageCount = 0;
        int resizedCount = 0;
        int ignoredFiles = 0;
        int ignoredDirectories = 0;
        System.out.println("Searching for images in \"" + Config.getLocalImageDir() + "\"");
        File imageDir = new File(Config.getLocalImageDir());
        File[] images = imageDir.listFiles();
        System.out.println("Found " + images.length + " files and directories");
        int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        System.out.println("Detected screen resolution of " + width + "x" + height);
        String lastResizedScreenresPath = Config.getLocalResizedImageDir() + "last-resized-screenres.txt";
        if (new File(lastResizedScreenresPath).exists()) {
            BufferedReader br = new BufferedReader(new FileReader(lastResizedScreenresPath));
            String s = br.readLine();
            br.close();
            if (!(width + "x" + height).equals(s)) {
                forceResize = true;
                System.out.println("Screen resolution at last resize was " + s + ", forcing resize of all images.");
                BufferedWriter bw = new BufferedWriter(new FileWriter(lastResizedScreenresPath));
                bw.write(width + "x" + height);
                bw.flush();
                bw.close();
            } else {
                System.out.println("Screen resolution did not change since last resize, not forcing resize on already resized images.");
            }
        } else {
            forceResize = true;
            System.out.println("Did not find resolution of previous resize, forcing resize of all images.");
            BufferedWriter bw = new BufferedWriter(new FileWriter(lastResizedScreenresPath));
            bw.write(width + "x" + height);
            bw.flush();
            bw.close();
        }
        System.out.println("Resizing Images:");
        for (File f : images) {
            String filename = f.getName();
            String resizedName = "resized-" + filename;
            String resizedPath = Config.getLocalResizedImageDir() + resizedName;
            System.out.println("Source: \"" + filename + "\": ");
            if ((!(new File(resizedPath).exists()) || forceResize)) {
                if (new File(f.getPath()).isFile()) {
                    String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    if (supportedExtensions.contains(fileExtension)) {
                        ImageIO.write(resizeImage(ImageIO.read(f)), fileExtension, new File(resizedPath));
                        imageCount++;
                        resizedCount++;
                    } else {
                        System.out.println("  File extension \"" + fileExtension + "\" is not a supported image type and was ignored.");
                        ignoredFiles++;
                    }
                }else {
                    System.out.println("  \"" + filename + "\" is a directory and was ignored.");
                    ignoredDirectories++;
                }
            } else {
                System.out.println("  Resized image \"" + resizedName + "\" already exists.");
                imageCount++;
            }
        }
        System.out.println(resizedCount + " of " + imageCount + " images had to be resized.");
        System.out.println("Ignored " + ignoredDirectories + ((ignoredDirectories == 1) ? " directory and " : " directories and ") + ignoredFiles + ((ignoredFiles == 1) ? " incompatible file." : " incompatible files."));
    }

    public static java.util.List<Image> getResizedImages() throws IOException {
        List<Image> ret = new ArrayList<>();
        File[] images = new File(Config.getLocalResizedImageDir()).listFiles();
        for (File f : images) {
            ret.add(ImageIO.read(f));
        }
        return ret;
    }


    public static BufferedImage resizeImage(BufferedImage image) {
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
        boolean multiStepDownscaling = (image.getWidth() > width && image.getHeight() > height);
        System.out.print(((multiStepDownscaling)?"  Downscaling":"  Upscaling") + " image of size " + image.getWidth() + "x" + image.getHeight() + " to " + width + "x" + height + " at aspect ratio of " + aspectRatio + ".");
        return getScaledInstance(image, width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR, multiStepDownscaling);
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
    private static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
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
}
