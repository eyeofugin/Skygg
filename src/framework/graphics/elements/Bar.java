package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Entity;

public class Bar extends GUIElement {

    protected Entity entity;
    protected String resource;
    protected Color color;

    @Override
    public int[] render() {
        background(Color.VOID);
        int[] barPixels = getBar(this.width, this.height, this.entity.getResourcePercentage(resource), color, Color.BLACK);
        int[] barStringPixels = getTextLine(this.entity.getResourceString(resource), this.width, this.height, 1, Color.WHITE);
        fill(barPixels);
        fill(barStringPixels);
        return pixels;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
