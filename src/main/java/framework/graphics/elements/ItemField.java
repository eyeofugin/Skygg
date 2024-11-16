package framework.graphics.elements;

import framework.graphics.GUIElement;
import game.entities.Hero;

public class ItemField extends GUIElement {
    private Hero hero;
    private int secondaryPointer = 0;
    private int secondaryPointerMax = 0;
    public ItemField(Hero hero) {
        this.hero = hero;
    }
}
