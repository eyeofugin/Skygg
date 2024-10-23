package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.Bar;
import framework.graphics.elements.TextField;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.Stat;

public class ActiveCharCard extends GUIElement {

    public Engine engine;
    public Arena arena;
    private Hero activeHero;
    private GUIElement portrait;
    private TextField name;
    private Bar healthBar;
    private Bar resourceBar;

    public ActiveCharCard(Engine e, Arena arena) {
        super(Property.ACTIVE_CHAR_OV_WIDTH, Property.ACTIVE_CHAR_OV_HEIGHT);
        this.x = Property.ACTIVE_CHAR_OV_X;
        this.y = Property.ACTIVE_CHAR_OV_Y;
        this.engine = e;
        this.arena = arena;
    }

    @Override
    public void update(int frame) {
        if (this.active) {

        }
    }

    public void setActiveHero(Hero e) {
        if (!e.isEnemy()) {
            this.activeHero = e;
            this.name = new TextField();
            this.name.setSize(124, 11);
            this.name.setPosition(2, 2);
            this.name.setString(this.activeHero.getName());
            this.name.setTextSize(1);
            this.name.setFontColor(Color.WHITE);

            this.portrait = new GUIElement();
            this.portrait.setSize(32, 32);
            this.portrait.setPosition(2, 14);
            this.portrait.setPixels(SpriteLibrary.sprites.get(activeHero.getPortraitName()));

            this.healthBar = new Bar();
            this.healthBar.setSize(90, 15);
            this.healthBar.setPosition(36, 14);
            this.healthBar.setHero(this.activeHero);
            this.healthBar.setResource(Stat.LIFE);
            this.healthBar.setColor(Color.GREEN);

            if (this.activeHero.getSecondaryResource() != null) {
                this.resourceBar = new Bar();
                this.resourceBar.setSize(90, 15);
                this.resourceBar.setPosition(36, 31);
                this.resourceBar.setHero(this.activeHero);
                this.resourceBar.setResource(this.activeHero.getSecondaryResource());
                this.resourceBar.setColor(Hero.getResourceColor(this.activeHero.getSecondaryResource()));
            }

            this.children.add(name);
            this.children.add(portrait);
            this.children.add(healthBar);
            this.children.add(resourceBar);
        }
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderChildren();
        return this.pixels;
    }
}
