package framework.graphics.text;

import framework.resources.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Symbol {
    public final int WIDTH,HEIGHT;
    public int[] pixels;

    public static Symbol zero5x8 = 		new Symbol(50,	0,4,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol one5x8 = 		new Symbol(50,	5,9,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol two5x8 = 		new Symbol(50,	10,14,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol three5x8=		new Symbol(50,	15,19,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol four5x8=		new Symbol(50,    20,24,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol five5x8=		new Symbol(50,	25,29,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol six5x8 = 		new Symbol(50,	30,34,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol seven5x8 = 	new Symbol(50, 	35,39,	0,7,	SpriteSheet.fonts5x8);
    public static Symbol eight5x8 = 	new Symbol(50,  40,44,  0,7,	SpriteSheet.fonts5x8);
    public static Symbol nine5x8 = 		new Symbol(50,  45,49,  0,7,    SpriteSheet.fonts5x8);
    public static Symbol A5x8 = 		new Symbol(50,	0,4,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol B5x8 = 		new Symbol(50,	5,9,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol C5x8 = 		new Symbol(50,	10,14,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol D5x8=			new Symbol(50,	15,19,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol E5x8=			new Symbol(50,  20,24,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol F5x8=			new Symbol(50,	25,29,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol G5x8 = 		new Symbol(50,	30,34,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol H5x8 = 		new Symbol(50, 	35,39,	8,15,	SpriteSheet.fonts5x8);
    public static Symbol I5x8 = 		new Symbol(50,  40,44,  8,15,	SpriteSheet.fonts5x8);
    public static Symbol J5x8 = 		new Symbol(50,  45,49,  8,15,   SpriteSheet.fonts5x8);
    public static Symbol K5x8 = 		new Symbol(50,	0,4,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol L5x8 = 		new Symbol(50,	5,9,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol M5x8 = 		new Symbol(50,	10,14,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol N5x8=			new Symbol(50,	15,19,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol O5x8=			new Symbol(50,  20,24,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol P5x8=			new Symbol(50,	25,29,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol Q5x8 = 		new Symbol(50,	30,34,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol R5x8 = 		new Symbol(50, 	35,39,	16,23,	SpriteSheet.fonts5x8);
    public static Symbol S5x8 = 		new Symbol(50,  40,44,  16,23,	SpriteSheet.fonts5x8);
    public static Symbol T5x8 = 		new Symbol(50,  45,49,  16,23,  SpriteSheet.fonts5x8);
    public static Symbol U5x8 = 		new Symbol(50,	0,4,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol V5x8 = 		new Symbol(50,	5,9,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol W5x8 = 		new Symbol(50,	10,14,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol X5x8=			new Symbol(50,	15,19,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol Y5x8=			new Symbol(50,  20,24,	24,31,	SpriteSheet.fonts5x8);
    public static Symbol Z5x8=			new Symbol(50,	25,29,	24,31,	SpriteSheet.fonts5x8);

    public static Symbol a5x8 = 		new Symbol(50,	0,4,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol b5x8 = 		new Symbol(50,	5,9,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol c5x8 = 		new Symbol(50,	10,14,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol d5x8=			new Symbol(50,	15,19,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol e5x8=			new Symbol(50,  20,24,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol f5x8=			new Symbol(50,	25,29,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol g5x8 = 		new Symbol(50,	30,34,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol h5x8 = 		new Symbol(50, 	35,39,	32,39,	SpriteSheet.fonts5x8);
    public static Symbol i5x8 = 		new Symbol(50,  40,44,  32,39,	SpriteSheet.fonts5x8);
    public static Symbol j5x8 = 		new Symbol(50,  45,49,  32,39,  SpriteSheet.fonts5x8);
    public static Symbol k5x8 = 		new Symbol(50,	0,4,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol l5x8 = 		new Symbol(50,	5,9,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol m5x8 = 		new Symbol(50,	10,14,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol n5x8=			new Symbol(50,	15,19,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol o5x8=			new Symbol(50,  20,24,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol p5x8=			new Symbol(50,	25,29,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol q5x8 = 		new Symbol(50,	30,34,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol r5x8 = 		new Symbol(50, 	35,39,	40,47,	SpriteSheet.fonts5x8);
    public static Symbol s5x8 = 		new Symbol(50,  40,44,  40,47,	SpriteSheet.fonts5x8);
    public static Symbol t5x8 = 		new Symbol(50,  45,49,  40,47,  SpriteSheet.fonts5x8);
    public static Symbol u5x8 = 		new Symbol(50,	0,4,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol v5x8 = 		new Symbol(50,	5,9,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol w5x8 = 		new Symbol(50,	10,14,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol x5x8=			new Symbol(50,	15,19,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol y5x8=			new Symbol(50,  20,24,	48,55,	SpriteSheet.fonts5x8);
    public static Symbol z5x8=			new Symbol(50,	25,29,	48,55,	SpriteSheet.fonts5x8);

    public static Symbol point5x8 =		initPoint5x8();
    public static Symbol slash5x8 = 	initSlash5x8();
    public static Symbol colon5x8 =     initColon5x8();
    public static Symbol moreThan5x8 =  initMoreThan5x8();
    public static Symbol lessThan5x8 =  initLessThan5x8();
    public static Symbol bracketopen5x8 = initBracketOpen5x8();
    public static Symbol bracketclose5x8 =initBracketClose5x8();
    public static Symbol minus5x8 =     initMinus5x8();
    public static Symbol plus5x8 =      initPlus5x8();
    public static Symbol equals5x8 =    initEquals5x8();

    public static Symbol hypermatter =    new Symbol("res/fonts/hypermatter.png",5,8);
    public static Symbol energy = 	new Symbol("res/fonts/energy.png",5,8);
    public static Symbol life = new Symbol("res/fonts/life.png",5,8);
    public static Symbol speed = new Symbol("res/fonts/speed.png",5,8);
    public static Symbol target = new Symbol("res/fonts/target.png",5,8);
    public static Symbol strength = new Symbol("res/fonts/strength.png", 5,8);
    public static Symbol precision = new Symbol("res/fonts/precision.png", 5,8);
    public static Symbol lethality = new Symbol("res/fonts/lethality.png", 5,8);
    public static Symbol reflexes = new Symbol("res/fonts/lethality.png", 5,8);
    public static Symbol action = new Symbol("res/fonts/action5x8.png", 5,8);
    public static Symbol turn = new Symbol("res/fonts/turn.png", 5,8);
    public static Symbol overheat_inactive = new Symbol("res/fonts/overheat_inactive.png", 5,8);

    public static Symbol smallNum1 = new Symbol(30,	0,2,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum2 = new Symbol(30,	3,5,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum3 = new Symbol(30,	6,8,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum4 = new Symbol(30,	9,11,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum5 = new Symbol(30,	12,14,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum6 = new Symbol(30,	15,17,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum7 = new Symbol(30,	18,20,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum8 = new Symbol(30,	21,23,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum9 = new Symbol(30,	24,26,	0,4,	SpriteSheet.smallNum);
    public static Symbol smallNum0 = new Symbol(30,	27,29,	0,4,	SpriteSheet.smallNum);

    public Symbol(int sheetBaseWidth, int xfrom,int xuntil,int yfrom,int yuntil, SpriteSheet sheet) {
        this.WIDTH = (xuntil-xfrom)+1;
        this.HEIGHT = (yuntil-yfrom)+1;
        pixels = new int[this.WIDTH*this.HEIGHT];
        cutOutSymbol(sheetBaseWidth,sheet.getSprite(sheet.WIDTH, sheet.HEIGHT),xfrom,xuntil,yfrom,yuntil);
    }
    public Symbol(int[] pixels, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.pixels = pixels;
    }
    public Symbol(String path, int width, int height) {
        this.WIDTH=width;
        this.HEIGHT=height;
        pixels=new int[this.WIDTH*this.HEIGHT];
        getSymbol(path);
    }
    private void getSymbol(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int width = image.getWidth();
            int height = image.getHeight();
            image.getRGB(0, 0, width, height, pixels, 0, width);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cutOutSymbol(int sheetBaseWidth, int[] sheet, int xfrom,int xuntil,int yfrom,int yuntil) {
        int index = 0;
        for(int i = yfrom; i <= yuntil;i++) {
            for(int j = xfrom; j <= xuntil;j++) {
                int color = sheet[j + i * sheetBaseWidth];
                pixels[index] = color;
                index++;
            }
        }
    }

    private static Symbol initPoint5x8() {
        int[] pixels = new int[]{0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                -1,0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels, 5,8);
    }
    private static Symbol initSlash5x8() {
        int[] pixels = new int[]{	0, 0, 0, 0,-1,
                0, 0, 0, 0,-1,
                0, 0, 0,-1, 0,
                0, 0,-1, 0, 0,
                0,-1, 0, 0, 0,
                -1, 0, 0, 0, 0,
                -1, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initBracketOpen5x8() {
        int[] pixels = new int[]{	0, 0, 0, 0,-1,
                0, 0, 0,-1, 0,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                0, 0, 0,-1, 0,
                0, 0, 0, 0,-1,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initBracketClose5x8() {
        int[] pixels = new int[]{  -1, 0, 0, 0, 0,
                0,-1, 0, 0, 0,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                0,-1, 0, 0, 0,
                -1, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initMinus5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
               -1,-1,-1,-1,-1,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initPlus5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                -1,-1,-1,-1,-1,
                0, 0,-1, 0, 0,
                0, 0,-1, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initEquals5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                -1,-1,-1,-1,-1,
                0, 0, 0, 0, 0,
                -1,-1,-1,-1,-1,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initColon5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0,-1, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0,-1, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,5,8);
    }
    private static Symbol initMoreThan5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0,-1, 0, 0, 0,
                0, 0,-1, 0, 0,
                0, 0, 0,-1, 0,
                0, 0,-1, 0, 0,
                0,-1, 0, 0, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,8,7);
    }
    private static Symbol initLessThan5x8() {
        int[] pixels = new int[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0,-1, 0,
                0, 0,-1, 0, 0,
                0,-1, 0, 0, 0,
                0, 0,-1, 0, 0,
                0, 0, 0,-1, 0,
                0, 0, 0, 0, 0};
        return new Symbol(pixels,8,7);
    }
}
