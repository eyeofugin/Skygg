package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;

public class TextField extends GUIElement {
    protected String string;
    protected int textSize;
    protected Color fontColor;

    @Override
    public int[] render() {
        fill(getTextLine(string, width, height, textSize, TextAlignment.LEFT, Color.VOID, fontColor));
        return pixels;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
}
