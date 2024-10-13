package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.Stat;

public class Bar extends GUIElement {

    protected Hero hero;
    protected Stat resource;
    protected Color color;

    @Override
    public int[] render() {
        background(Color.VOID);
        int[] barPixels = getBar(this.width, this.height, this.hero.getResourcePercentage(resource), color, Color.BLACK);
        int[] barStringPixels = getTextLine(this.hero.getResourceString(resource), this.width, this.height, 1, Color.WHITE);
        fill(barPixels);
        fill(barStringPixels);
        return pixels;
    }

    public Bar setResource(Stat resource) {
        this.resource = resource;
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Bar setHero(Hero hero) {
        this.hero = hero;
        return this;
    }
}
