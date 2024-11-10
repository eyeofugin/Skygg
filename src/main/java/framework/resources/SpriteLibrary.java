package framework.resources;

import framework.Property;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.BlastingCounter;
import game.skills.changeeffects.effects.Blight;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.DarkSecrets;
import game.skills.changeeffects.effects.Exalted;
import game.skills.changeeffects.effects.Immunity;
import game.skills.changeeffects.effects.RegenBoost;
import game.skills.changeeffects.effects.RegenStop;
import game.skills.changeeffects.effects.RighteousHammerCounter;
import game.skills.changeeffects.effects.Scoped;
import game.skills.changeeffects.effects.SmokeScreen;
import game.skills.changeeffects.effects.Steadfast;
import game.skills.changeeffects.effects.SwiftStrikeCounter;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Blinded;
import game.skills.changeeffects.statusinflictions.Dazed;
import game.skills.changeeffects.statusinflictions.Disenchanted;
import game.skills.changeeffects.statusinflictions.Injured;
import game.skills.changeeffects.statusinflictions.Rooted;
import game.skills.changeeffects.statusinflictions.Taunted;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {
    public static Map<String, int[]> sprites = new HashMap<>();
    public static Map<String, int[]> portraits = new HashMap<>();
    public static Map<String, int[]> skills = new HashMap<>();


    public static void load() {
        sprites.put("scene01", sprite(640,360, 320, 180, "battlescene/scene01.png", 0));
        sprites.put("arrow_down", sprite(32,32,32,32,"icons/gui/arrowsmall.png", 90));
        sprites.put("arrow", sprite(12,12,12,12,"icons/gui/arrow.png", 90));
        sprites.put("arrow_right", sprite(12,12,12,12,"icons/gui/arrow_right.png", 0));
        sprites.put(Burning.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/burning.png", 0));
        sprites.put(Combo.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/combo.png", 0));
        sprites.put(Exalted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/exalted.png", 0));
        sprites.put(Bleeding.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/bleeding.png", 0));
        sprites.put(Blinded.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/blinded.png", 0));
        sprites.put(Dazed.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/dazed.png", 0));
        sprites.put(Disenchanted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/disenchanted.png", 0));
        sprites.put(Injured.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/regenstop.png", 0));
        sprites.put(Rooted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/rooted.png", 0));
        sprites.put(Taunted.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/taunted.png", 0));
        sprites.put(DarkSecrets.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/darksecrets.png", 0));
        sprites.put(Blight.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/blightsmall.png", 0));
        sprites.put(AxeSwingCounter.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/axeswingcounter.png", 0));
        sprites.put(BlastingCounter.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/blastingcounter.png", 0));
        sprites.put(Immunity.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/immunity.png", 0));
        sprites.put(RegenBoost.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/regenboost.png", 0));
        sprites.put(RegenStop.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/regenstop.png", 0));
        sprites.put(RighteousHammerCounter.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/righteoushammercounter.png", 0));
        sprites.put(Scoped.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/scoped.png", 0));
        sprites.put(SmokeScreen.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/smokescreen.png", 0));
        sprites.put(Steadfast.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/steadfast.png", 0));
        sprites.put(SwiftStrikeCounter.class.getName(), sprite(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,Property.EFFECT_ICON_SIZE,
                "icons/effect/swiftstrikecounter.png", 0));
        sprites.put(Heat.class.getName(), sprite(Property.GLOBAL_EFFECT_WIDTH, Property.GLOBAL_EFFECT_HEIGHT,Property.GLOBAL_EFFECT_WIDTH,Property.GLOBAL_EFFECT_HEIGHT,
                "icons/effect/heat.png", 0));

    }

    public static int[] sprite(int targetW, int targetH, int w, int h, String path,int flip) {
        int[] sprite = new int[targetW*targetH];
        try {
            URI uri = SpriteLibrary.class.getClassLoader().getResource(path).toURI();
            BufferedImage sheet = ImageIO.read(new File(uri));
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
            URI uri = SpriteLibrary.class.getClassLoader().getResource(path).toURI();
            BufferedImage sheet = ImageIO.read(new File(uri));
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
