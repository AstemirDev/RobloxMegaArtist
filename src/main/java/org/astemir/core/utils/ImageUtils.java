package org.astemir.core.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {


    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public static BufferedImage readImage(String path, ImageScaling scaling){
        try {
            Image image = ImageIO.read(new File(path));
            return toBufferedImage(image.getScaledInstance(32,32, scaling.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public enum ImageScaling{
        DEFAULT(1),FAST(2),SMOOTH(4);

        private int id;

        ImageScaling(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
