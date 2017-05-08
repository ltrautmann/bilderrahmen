package com.sabel.bilderrahmen.clientv2.utils.image;

import com.sabel.bilderrahmen.clientv2.utils.config.Config;
import com.sabel.bilderrahmen.clientv2.utils.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public static synchronized void resizeAllImages(boolean forceResize) {
        int imageCount = 0;
        int resizedCount = 0;
        int ignoredFiles = 0;
        int ignoredDirectories = 0;
        int outOfMemoryImages = 0;
        int count = 0;
        int screenwidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenheight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        String lastResolutionPath = Config.getLocalResizedDir() + "last-resized-resolution.txt";
        Logger.appendln("Searching for images in \"" + Config.getLocalImageDir() + "\"", Logger.LOGTYPE_INFO);
        File imageDir = new File(Config.getLocalImageDir());
        File[] images = imageDir.listFiles();
        Logger.appendln("Found " + images.length + " files and directories", Logger.LOGTYPE_INFO);
        Logger.appendln("Detected screen resolution of " + screenwidth + "x" + screenheight + ".", Logger.LOGTYPE_INFO);
        forceResize = compareLastResolution(screenwidth, screenheight) || forceResize;
        Logger.appendln("Resizing images:", Logger.LOGTYPE_INFO);
        Logger.resetProgressBar(images.length + 1);
        for (File f : images) {
            String filename = f.getName();
            String resizedName = renamedFilePrefix + filename;
            String resizedPath = Config.getLocalResizedDir() + resizedName;
            Logger.updateProgressBar();
            count++;
            Logger.appendln("Source: \"" + filename + "\" (#" + count + " of " + images.length + ")", Logger.LOGTYPE_INFO);
            if (forceResize || !(new File(resizedPath).exists())) {
                if (new File(f.getPath()).isFile()) {
                    String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                    if (supportedExtensions.contains(fileExtension)) {
                        int tries = 0;
                        int maxTries = 2;
                        boolean multistepResize = true;
                        imageCount++;
                        while (tries < maxTries && !Thread.interrupted()) {
                            try {
                                ImageIO.write(resize(ImageIO.read(f), !multistepResize), fileExtension, new File(resizedPath));
                                ImageService.accessImage(resizedPath, resize((BufferedImage) ImageService.accessImage(f.getPath(), null, null), !multistepResize), fileExtension);
                                resizedCount++;
                                tries += maxTries;
                            } catch (OutOfMemoryError e) {
                                if (multistepResize) {
                                    Logger.appendln("\n\tNot enough memory available for high-quality multi-step scaling. Attempting single-step scaling.", Logger.LOGTYPE_WARNING);
                                    tries++;
                                    multistepResize = false;
                                } else {
                                    Logger.appendln("\n\tNot enough memory to resize current image. Aborting resize.", Logger.LOGTYPE_ERROR);
                                    tries++;
                                    File f2 = new File(resizedPath);
                                    f2.delete();
                                    tries += maxTries;
                                    outOfMemoryImages++;
                                }
                            } catch (IOException e) {
                                if (++tries == maxTries) {
                                    Logger.appendln("\tMax tries exceeded, aborting resize.", Logger.LOGTYPE_ERROR);
                                } else {
                                    int retryMilliseconds = 1000;
                                    Logger.appendln("\tCould not access current image file. This may be because the display thread is currently accessing it. Retrying in " + retryMilliseconds + " Milliseconds. " + (maxTries - tries) + " of " + maxTries + " left.", Logger.LOGTYPE_INFO);
                                    try {
                                        Thread.sleep(retryMilliseconds);
                                    } catch (InterruptedException e1) {
                                        Logger.appendln("Interrupted. Aborting resize.", Logger.LOGTYPE_INFO);
                                        try {
                                            setLastResolution(lastResolutionPath, "interrupted");
                                        } catch (IOException e2) {
                                            Logger.appendln("Could not save current resize as interrupted, this may result in improperly scaled or missing images.", Logger.LOGTYPE_ERROR);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    } else {
                        Logger.appendln("\tFile Extension \"" + fileExtension + "\" is not a supported image type and was ignored.", Logger.LOGTYPE_INFO);
                        ignoredFiles++;
                    }
                } else {
                    Logger.appendln("\t\"" + filename + "\" is a directory and was ignored.", Logger.LOGTYPE_INFO);
                    ignoredDirectories++;
                }
            } else {
                Logger.appendln("\tResized image \"" + resizedName + "\" already exists", Logger.LOGTYPE_INFO);
                imageCount++;
            }
            if (Thread.interrupted()) {
                Logger.appendln("Interrupted. Aborting resize.", Logger.LOGTYPE_INFO);
                try {
                    setLastResolution(lastResolutionPath, "interrupted");
                } catch (IOException e) {
                    Logger.appendln("Could not save current resize as interrupted, this may result in improperly scaled or missing images.", Logger.LOGTYPE_ERROR);
                }
                return;
            }
        }
        Logger.updateProgressBar();
        Logger.appendln(resizedCount + " of " + imageCount + " images were resized.", Logger.LOGTYPE_INFO);
        if (outOfMemoryImages > 0) {
            Logger.appendln("Not enough memory to resize " + outOfMemoryImages + ((outOfMemoryImages == 1) ? " image." : " images."), Logger.LOGTYPE_WARNING);
        }
        Logger.appendln("Ignored " + ignoredDirectories + ((ignoredDirectories == 1) ? " directory and " : " directories and ") + ignoredFiles + ((ignoredFiles == 1) ? " incompatible file." : " incompatible files."), Logger.LOGTYPE_INFO);
    }

    private static boolean compareLastResolution(int screenwidth, int screenheight) {
        String lastResolutionPath = Config.getLocalResizedDir() + "last-resized-resolution.txt";
        String newResolution = screenwidth + "x" + screenheight;
        if (new File(lastResolutionPath).exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(lastResolutionPath));
                String oldResolution = br.readLine();
                br.close();
                if (newResolution.equals(oldResolution)) {
                    Logger.appendln("Screen resolution has not changed since last resize. Resizing only new images.", Logger.LOGTYPE_INFO);
                    return false;
                } else {
                    Logger.appendln("Screen resolution has changed since last resize, forcing resize on all images.", Logger.LOGTYPE_INFO);
                    setLastResolution(lastResolutionPath, newResolution);
                    return true;
                }
            } catch (FileNotFoundException e) {
                Logger.appendln("This should not have happened. You are a wizard. You actually deleted a file in the exact split second between checking if it's there and accessing it.", Logger.LOGTYPE_ERROR);
                return true;
            } catch (IOException e) {
                Logger.appendln("Could not access the file storing the resolution. Forcing resize.", Logger.LOGTYPE_WARNING);
                return true;
            }
        } else {
            try {
                Logger.appendln("Could not find resolution of a previous resize, forcing resize of all images", Logger.LOGTYPE_INFO);
                setLastResolution(lastResolutionPath, newResolution);
            } catch (IOException e) {
                Logger.appendln("Current resolution could not be saved. This will force another resize of all images on the next update cycle.", Logger.LOGTYPE_WARNING);
            } finally {
                return true;
            }
        }
    }

    private static void setLastResolution(String lastResolutionPath, String newResolution) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(lastResolutionPath));
        bw.write(newResolution);
        bw.flush();
        bw.close();
    }

    public static List<String> getResizedImagePaths() {
        List<String> ret = new ArrayList<>();
        File[] images = new File(Config.getLocalResizedDir()).listFiles();
        for (File f : images) {
            if (supportedExtensions.contains(f.getName().substring(f.getName().lastIndexOf(".") + 1))) {
                if (new File(f.getPath().replace(Config.getLocalResizedDir() + renamedFilePrefix, Config.getLocalImageDir())).exists()) {
                    ret.add(f.getPath());
                }
            }
        }
        return ret;
    }

    public static void deleteObsoleteImages() {
        Logger.appendln("Deleting obsolete images.", Logger.LOGTYPE_INFO);
        File[] images = new File(Config.getLocalResizedDir()).listFiles();
        int count = 0;
        int failed = 0;
        for (File f : images) {
            if (supportedExtensions.contains(f.getName().substring(f.getName().lastIndexOf(".") + 1))) {
                if (!new File(f.getPath().replace(Config.getLocalResizedDir() + renamedFilePrefix, Config.getLocalImageDir())).exists()) {
                    if (f.delete()) {
                        Logger.appendln("Deleted obsolete image \"" + f.getPath() + "\"", Logger.LOGTYPE_INFO);
                        count++;
                    } else {
                        Logger.appendln("Could not delete obsolete image \"" + f.getPath() + "\"", Logger.LOGTYPE_WARNING);
                        failed++;
                    }
                }
            }
        }
        Logger.appendln("Deleted " + count + " obsolete " + ((count == 1) ? "image." : "images."), Logger.LOGTYPE_INFO);
        if (failed > 0) {
            Logger.appendln("Failed to delete " + failed + " obsolete " + ((failed == 1) ? "image." : "images."), Logger.LOGTYPE_ERROR);
        }
    }

    private static synchronized BufferedImage resize(BufferedImage image, boolean forceSingleStep) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        //adjust the target height or width to conserve the aspect ratio of the original image
        double aspectRatio = (double) image.getWidth() / (double) image.getHeight();
        if (aspectRatio <= ((double) width / (double) height)) {
            width = (int) (height * aspectRatio);
        } else {
            height = (int) (width / aspectRatio);
        }
        if (image.getHeight() == height && image.getWidth() == width) {
            Logger.appendln("\tImage happens to already be at screen resolution " + width + "x" + height + " and did not have to be resized.", Logger.LOGTYPE_INFO);
            return image;
        }
        boolean multiStepDownscaling = (image.getWidth() > width && image.getHeight() > height) && !forceSingleStep;
        Logger.append(((multiStepDownscaling) ? "\tDownscaling" : "\tUpscaling") + " image of size " + image.getWidth() + "x" + image.getHeight() + " to " + width + "x" + height + " at aspect ratio of " + aspectRatio + ".", Logger.LOGTYPE_INFO);
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
     *
     * @param img           the original image to be scaled
     * @param targetWidth   the desired width of the scaled instance, in pixels
     * @param targetHeight  the desired height of the scaled instance,  in pixels
     * @param hint          one of the rendering hints that corresponds to
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
        Logger.append(".", Logger.LOGTYPE_INFO);
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
            Logger.append(".", Logger.LOGTYPE_INFO);
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            g2 = null;
            System.gc();
            ret = tmp;
            cycles++;
        } while (w != targetWidth || h != targetHeight && !Thread.interrupted());
        Logger.appendln(".", Logger.LOGTYPE_INFO);
        if (Thread.interrupted()) {
            Logger.appendln("\tThread was interrupted while resizing image. Aborting.", Logger.LOGTYPE_INFO);
            return img;
        } else {
            Logger.appendln("\tDone in " + cycles + ((cycles != 1) ? " cycles." : " cycle."), Logger.LOGTYPE_INFO);
        }
        return ret;
    }

    public static List<String> getSupportedExtensions() {
        return supportedExtensions;
    }
}
