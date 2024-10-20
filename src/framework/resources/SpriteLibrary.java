package framework.resources;

import framework.Property;
import game.skills.changeeffects.statusinflictions.Disenchanted;
import game.skills.changeeffects.statusinflictions.Injured;
import game.skills.changeeffects.statusinflictions.Rooted;
import game.skills.changeeffects.statusinflictions.Taunted;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {
    public static Map<String, int[]> sprites = new HashMap<>();
    public static Map<String, int[]> portraits = new HashMap<>();
    public static Map<String, int[]> skills = new HashMap<>();


    public static void load() {
        sprites.put("scene01", sprite(640,360, 320, 180, "res/battlescene/scene01.png", 0));
        sprites.put("arrow_down", sprite(32,32,32,32,"res/icons/gui/arrowsmall.png", 90));
        sprites.put("arrow", sprite(12,12,12,12,"res/icons/gui/arrow.png", 0));
        sprites.put(Injured.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_FILE_SIZE,Property.EFFECT_ICON_FILE_SIZE,"res/icons/effect/burning.png",0));
        sprites.put(Disenchanted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_FILE_SIZE,Property.EFFECT_ICON_FILE_SIZE,"res/icons/effect/paralysed.png",0));
        sprites.put(Rooted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_FILE_SIZE,Property.EFFECT_ICON_FILE_SIZE,"res/icons/effect/rooted.png",0));
        sprites.put(Taunted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_FILE_SIZE,Property.EFFECT_ICON_FILE_SIZE,"res/icons/effect/steadfast.png",0));

    }

    public static int[] sprite(int targetW, int targetH, int w, int h, String path,int flip) {
        int[] sprite = new int[targetW*targetH];
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            int[] pixels = new int[w*h];
            if (flip==180){
                sheet = SpriteUtils.flipH(sheet);
            }
            if (flip==90) {
                sheet = SpriteUtils.flip90(sheet);
            }
            sheet.getRGB(0, 0, w, h, pixels, 0, w);
            return convert(pixels, targetW, targetH, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprite;
    }
    public static int[][] setupSprites(int amntFrames, int w, int h, String path, boolean flip) {
        int[][] sprite = new int[amntFrames][w*h];
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            for (int i = 0; i < amntFrames; i++) {
                int[] pixels = new int[w*h];
                BufferedImage img = sheet.getSubimage(i*w, 0,w,h);
                if (flip){
                    img = SpriteUtils.flipH(img);
                }
                img.getRGB(0, 0, w, h, pixels, 0, w);
                sprite[i] = pixels;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprite;
    }
    private static int[] convert(int[] source,int targetW, int targetH, int w) {
        int[] p = new int[targetH*targetW];
        int mult = targetW / w;

        for(int y = 0; y < targetH; y++) {
            for(int x = 0; x < targetW; x++) {
                p[x+y*targetW] = source[(x/mult)+(y/mult)*w];
            }
        }
        return p;
    }
}
