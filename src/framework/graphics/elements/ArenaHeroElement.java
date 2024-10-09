package framework.graphics.elements;

import framework.graphics.GUIElement;
import game.entities.Hero;

public class ArenaHeroElement extends GUIElement {

    private final Hero hero;
    private int position;

    public ArenaHeroElement(Hero hero) {
        super(32,90);
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
