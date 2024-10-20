package framework.graphics;

import framework.Logger;
import framework.Property;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.graphics.text.TextEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIElement {
    public String name;
    protected int width, height;
    protected Color background = Color.VOID;
    protected int[] pixels;
    protected int x, y; //location in parent
    protected TextEditor editor = new TextEditor(TextEditor.baseConf);
    protected List<GUIElement> children = new ArrayList<>();

    protected boolean simpleBorder;
    protected Color borderColor = Color.WHITE;
    protected int borderThickness;

    protected boolean active;

    public GUIElement() {
        this.pixels= new int[0];
    }
    public GUIElement(String name) {
        this.pixels= new int[0];
        this.name = name;
    }

    public GUIElement(int width, int height, Color background, int[] pixels, int x, int y) {
        this.width = width;
        this.height = height;
        this.background = background;
        this.pixels = pixels;
        this.x = x;
        this.y = y;
    }
    public GUIElement(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.pixels = new int[width*height];
    }

    public GUIElement(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width*height];
    }

    public void update(int frame) {

    };
    public int[] render() {
        renderChildren();
        return this.pixels;
    }

    protected void updateChildren(int frame) {
        for (GUIElement child : children) {
            if (child != null) {
                child.update(frame);
            }
        }
    }
    protected void renderChildren() {
        for (GUIElement child : children) {
            if (child != null) {
                int[] childP = child.render();
                fillWithGraphicsSize(child.getX(), child.getY(), child.getWidth(), child.getHeight(), childP, false);
            }
        }
    }
    protected void deactivateAllChildren() {
        for (GUIElement child : children) {
            if (child != null) {
                child.setActive(false);
            }
        }
    }
    protected int[] getTextBlock(String text, int maxWidth, int fontSize, TextAlignment alignment, Color background, Color font) {
        String[] rows = splitRows(text, maxWidth, fontSize);
        int[] result = new int[0];
        for (String s : rows) {
            int[] rowPixels = getTextLine(s, maxWidth, 10 * fontSize, fontSize, alignment, background, font);
            int[] newPixels = new int[result.length + rowPixels.length];
            System.arraycopy(result, 0, newPixels, 0, result.length);
            System.arraycopy(rowPixels, 0, newPixels, result.length, rowPixels.length);
            result = newPixels;
        }
        return result;
    }

    private String[] splitRows(String text, int width, int fontSize) {
        int stringWidth = getStringWidth(text, fontSize);
        boolean hasLineBreaks = text.contains("[br]");
        if (stringWidth > width || hasLineBreaks) {
            return splitAt(width, text, fontSize);
        } else {
            return new String[]{text};
        }
    }

    private int getStringWidth(String s, int fontSize) {
        int chars = s.length();
        if (s.contains("[")) {
            int charcounter = 0;
            char[] stringChars = new char[s.length()];
            s.getChars(0, s.length() - 1, stringChars, 0);
            boolean inBrackets = false;
            for (char c : stringChars) {
                if (c == '[') {
                    inBrackets = true;
                    charcounter++;
                } else if (c == ']') {
                    inBrackets = false;
                } else if (inBrackets) {
                    charcounter++;
                }
            }
            chars -= charcounter;
        }
        return chars * ((Property.BASE_CHAR_DISTANCE + getCharWidth()) * fontSize);
    }

    private String[] splitAt(int split, String s, int fontSize) {
        List<String> result = new ArrayList<>();
        String[] splitWhole = s.split(" ");
        int loopcheck = 0;
        while (hasEntries(splitWhole) && loopcheck<10) {
            result.add(getNextRow(splitWhole, split, fontSize));
            loopcheck++;
        }
        return getArray(result);
    }

    private boolean hasEntries(String[] split) {
        for (String s : split) {
            if (!s.equals(" ")) {
                return true;
            }
        }
        return false;
    }

    private String getNextRow(String[] splitted, int split, int fontSize) {
        String newS = "";
        int index = getFirstFilled(splitted);
        while (getStringWidth(newS, fontSize) + getStringWidth(splitted[index], fontSize) + 5 < split) {
            if (splitted[index].equals("[br]")) {
                splitted[index] = " ";
                return newS + " ";
            }
            newS += splitted[index] + " ";
            splitted[index] = " ";
            index++;
            if (index == splitted.length) {
                return newS;
            }
        }
        return newS;
    }

    private int getFirstFilled(String[] split) {
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals(" ")) {
                return i;
            }
        }
        return 0;
    }

    private String[] getArray(List<String> list) {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static int getCharWidth() {
        return new TextEditor(TextEditor.baseConf).charWidth;
    }
    protected int[] getTextLine(String text, int textWidth, int textHeight, int fontSize, Color fontColor) {
        return editor.getTextLine(text, textWidth, textHeight, fontSize, TextAlignment.CENTER, Color.VOID, fontColor);
    }
    protected int[] getTextLine(String text, int textWidth, int textHeight, int fontSize, TextAlignment alignment, Color background, Color fontColor) {
        return editor.getTextLine(text, textWidth, textHeight, fontSize, alignment, background, fontColor);
    }

    protected void writeLine(String text, int xFrom, int xUntil, int yFrom, int yUntil) {
        writeLine(text, xFrom, xUntil, yFrom, yUntil, 0, TextAlignment.CENTER, Color.BLACK, Color.WHITE);
    }

    protected void writeLine(String text, int xFrom, int xUntil, int yFrom, int yUntil, int fontSize, TextAlignment alignment, Color backGround, Color font) {

        int[] result = editor.getTextLine(text, (xUntil - xFrom + 1), (yUntil - yFrom + 1), fontSize, alignment, backGround, font);
        int index = 0;
        for (int y = yFrom; y <= yUntil; y++) {
            for (int x = xFrom; x <= xUntil; x++) {
                if (result[index] != -12450784) {
                    pixels[x + y * this.width] = result[index];
                } else if (!backGround.equals(Color.VOID)) {
                    pixels[x + y * this.width] = backGround.VALUE;
                }
                index++;
            }
        }
    }

    protected void horizontalLine(int xfrom, int xuntil, int height) {
        for (int i = xfrom; i <= xuntil; i++) {
            pixels[i + height * this.width] = -1;
        }
    }
    protected void fill(int[] graphics) {
        if (this.pixels.length != graphics.length) {
            System.out.println("ERROR: Not the same length");
            return;
        }
        for (int i = 0; i < this.width*this.height; i++) {
            int color =  graphics[i];
            if (color != Color.VOID.VALUE) {
                this.pixels[i] = color;
            }
        }
    }
    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, Color borderColor) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, borderColor != null, borderColor, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered, Color borderColor) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, bordered, borderColor, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered) {
        fillWithGraphicsSize(xf, yf, w, h, graphics, bordered, Color.WHITE, Color.VOID);
    }

    protected void fillWithGraphicsSize(int xf, int yf, int w, int h, int[] graphics, boolean bordered, Color borderColor, Color backgroundColor) {
        int xu = xf + w - 1;
        int yu = yf + h - 1;
        fillWithGraphics(xf, xu, yf, yu, graphics, bordered, borderColor, backgroundColor);
    }

    protected void fillWithGraphics(int xfrom, int xuntil, int yfrom, int yuntil, int[] graphics, boolean bordered, Color borderColor, Color backgroundColor) {

        if (graphics == null) {
            return;
        }
        int index = 0;
        for (int i = yfrom; i <= yuntil; i++) {
            for (int j = xfrom; j <= xuntil; j++) {

                if (index >= graphics.length) {
                    continue;
                }
                if (j + i * this.width >= pixels.length) {
                    continue;
                }
                if (graphics[index] != Color.VOID.VALUE) {
                    int color = graphics[index];
                    int arrayindex = j + i * this.width;
                    if (arrayindex < pixels.length && arrayindex >= 0)
                        pixels[arrayindex] = color;
                } else if (!backgroundColor.equals(Color.VOID)) {
                    pixels[j + i * this.width] = backgroundColor.VALUE;
                }
                index++;
            }
        }
        if (bordered) {
            try {
                for (int i = xfrom; i <= xuntil; i++) {
                    pixels[i + (yfrom) * this.width] = borderColor.VALUE;
                    pixels[i + (yuntil) * this.width] = borderColor.VALUE;
                }
                for (int i = yfrom; i <= yuntil; i++) {
                    pixels[(xfrom) + i * this.width] = borderColor.VALUE;
                    pixels[(xuntil) + i * this.width] = borderColor.VALUE;
                }
            } catch (IndexOutOfBoundsException e) {
                Logger.logLn("[ERROR]: Writing border failed");
            }

        }
    }

    public void background(Color color) {
        Arrays.fill(this.pixels, color.VALUE);
    }
    protected int[] getBar(int width, int height, double percentage, Color color, Color color2) {
        int[] resultPixels = new int[width * height];
        int filledSize = (int) (width * percentage);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                resultPixels[x + y * width] = color2.VALUE;
            }
        }
        for (int x = 0; x < filledSize; x++) {
            for (int y = 0; y < height; y++) {
                resultPixels[x + y * width] = color.VALUE;
            }
        }
        for (int i = 0; i < width; i++) {
            resultPixels[i] = Color.WHITE.VALUE;
            resultPixels[i + (height - 1) * width] = Color.WHITE.VALUE;
        }
        for (int i = 0; i < height; i++) {
            resultPixels[i * width] = Color.WHITE.VALUE;
            resultPixels[width - 1 + i * width] = Color.WHITE.VALUE;
        }
        return resultPixels;
    }
    protected void writeBar(int xFrom, int xUntil, int yFrom, int yUntil, double percentage, Color color, Color color2) {

        int filledSize = (int) ((xUntil - xFrom) * percentage);

        for (int x = xFrom; x < (xUntil); x++) {
            for (int y = yFrom; y < yUntil; y++) {
                pixels[x + y * width] = color2.VALUE;
            }
        }

        for (int x = xFrom; x < (xFrom + filledSize); x++) {
            for (int y = yFrom; y < yUntil; y++) {
                pixels[x + y * width] = color.VALUE;
            }
        }

        for (int i = xFrom; i < xUntil + 1; i++) {
            pixels[i + yFrom * this.width] = -1;
            pixels[i + yUntil * this.width] = -1;
        }
        for (int i = yFrom; i < yUntil + 1; i++) {
            pixels[(xFrom) + i * this.width] = -1;
            pixels[(xUntil) + i * this.width] = -1;
        }
    }
    protected void clear() {
        this.pixels = new int[this.pixels.length];
    }

    public int[] getPixels() {
        return this.pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isSimpleBorder() {
        return simpleBorder;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorderThickness() {
        return borderThickness;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];
    }


    public void setBackground(Color background) {
        this.background = background;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEditor(TextEditor editor) {
        this.editor = editor;
    }

    public void setChildren(List<GUIElement> children) {
        this.children = children;
    }

    public void setBorder(Color borderColor, int borderThickness) {
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        this.simpleBorder = true;
    }
    public void removeBorder() {
        this.simpleBorder = false;
    }
}
