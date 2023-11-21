package org.astemir.core.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorUtils {

    public static final int QUITE_SIMILAR = 10000;
    public static final int LITTLE_SIMILAR = 5000;
    public static final int SIMILAR = 1000;
    public static final int VERY_SIMILAR = 500;
    public static final int ULTRA_SIMILAR = 300;
    public static final int SAME = 100;
    public static final int EXACTLY_SAME = 50;
    public static final int EQUALITY = 20;

    public static String hex(int rgb){
        StringBuffer res = new StringBuffer();
        res.append("#");
        String val = Long.toHexString((long) rgb & 0xFFFFFF);
        for (int i = 0; i < (6 - val.length()); i++)
            res.append("0");
        res.append(val);
        return res.toString();
    }

    public static boolean isTransparent(int rgb) {
        if((rgb>>24) == 0x00 ) {
            return true;
        }
        return false;
    }

    public static float getHue(int color){
        return getHsb(color).get("h");
    }

    public static float getSaturation(int color){
        return getHsb(color).get("s");
    }

    public static float getBrightness(int color){
        return getHsb(color).get("b");
    }

    public static int setHue(int color,float hue){
        return Color.getHSBColor(hue,getSaturation(color),getBrightness(color)).getRGB();
    }

    public static int setBrightness(int color,float brightness){
        return Color.getHSBColor(getHue(color),getSaturation(color),brightness).getRGB();
    }

    public static int setSaturation(int color,float saturation){
        return Color.getHSBColor(getHue(color),saturation,getBrightness(color)).getRGB();
    }

    public static Map<String,Float> getHsb(int color){
        Map<String,Float> map = new HashMap<>();
        Color color1 = new Color(color);
        float[] floats = new float[3];
        floats = Color.RGBtoHSB(color1.getRed(),color1.getGreen(),color1.getBlue(),floats);
        map.put("h",floats[0]);
        map.put("s",floats[1]);
        map.put("b",floats[2]);
        return map;
    }

    private static int colorOffset(int color,int h,int s,int v){
        float h1 = ((float) h)/255f;
        float s1 = ((float) s)/255f;
        float v1 = ((float) v)/255f;
        float hue = ColorUtils.getHue(color)+h1;
        float saturation = ColorUtils.getSaturation(color)+s1;
        float brightness = ColorUtils.getBrightness(color)+v1;
        return Color.HSBtoRGB(hue,saturation,brightness);
    }

    public static boolean isSimilarColor(int color, int colorB, int similarity){
        Color a = new Color(color);
        Color b = new Color(colorB);
        double distance = (a.getRed() - b.getRed())*(a.getRed() - b.getRed()) + (a.getGreen() - b.getGreen())*(a.getGreen() - b.getGreen()) + (a.getBlue() - b.getBlue())*(a.getBlue() - b.getBlue());
        if(distance < similarity){
            return true;
        }else{
            return false;
        }
    }

}
