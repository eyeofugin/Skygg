package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.Bar;
import framework.graphics.elements.EffectField;
import framework.graphics.elements.ItemField;
import framework.graphics.elements.StatField;
import framework.graphics.elements.TextField;
import framework.graphics.text.Color;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.Stat;

public class ActiveCharCard extends GUIElement {

    public Engine engine;
    public Arena arena;
    private Hero activeHero;
    private TextField name;
    private TextField health;
    private TextField resource;
    private StatField stats;
    private EffectField effects;
    private ItemField items;

    private GUIElement[] modeIcons;

//    private Bar healthBar;
//    private Bar resourceBar;

    private int modePointer = 0;
    public ActiveCharCard(Engine e, Arena arena) {
        super(Property.ACTIVE_CHAR_OV_WIDTH, Property.ACTIVE_CHAR_OV_HEIGHT);
        this.x = Property.ACTIVE_CHAR_OV_X;
        this.y = Property.HUD_BOXES_Y;
        this.engine = e;
        this.arena = arena;
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (modePointer != 0) {
                    modePointer--;
                    activateMode();
                }
            }

            if (engine.keyB._downPressed) {
                if (modePointer != 2) {
                    modePointer++;
                    activateMode();
                }
            }
            updateChildren(frame);
        }
    }

    private void activateMode() {
        deactivateModes();
        buildIcons();
        switch (modePointer) {
            case 0 -> activateStats();
            case 1 -> activateEffects();
            case 2 -> activateItems();
        }
    }

    private void activateStats() {
        this.stats = new StatField(this.activeHero);
        this.stats.setPosition(16, 30);
        this.stats.setActive(true);
        this.children.add(this.stats);
    }
    private void activateEffects() {
        this.effects = new EffectField(this.activeHero, engine);
        this.effects.setPosition(16,30);
        this.effects.setActive(true);
        this.children.add(this.effects);
    }

    private void activateItems() {
        this.items = new ItemField(this.activeHero, engine);
        this.items.setPosition(16,30);
        this.items.setActive(true);
        this.children.add(this.items);
    }

    private void buildIcons() {
        this.modeIcons = new GUIElement[3];
        int y = 30;
        addIcon("stats", y, 0);
        y+=16;
        addIcon("effects", y, 1);
        y+=16;
        addIcon("items", y, 2);
    }

    private void addIcon(String spriteName, int yPosition, int mode) {
        GUIElement icon = new GUIElement();
        icon.setSize(16,16);
        icon.setPixels(new int[16*16]);
        icon.setPosition(0, yPosition);
        int[] pixels = SpriteLibrary.getSprite(spriteName);
        GUIElement.setBackGround(pixels, modePointer == mode ? Color.BLACK : Color.DARKGREY);
        icon.setPixels(pixels);
        modeIcons[mode] = icon;
    }

    private void deactivateModes() {
        this.children.remove(items);
        this.children.remove(effects);
        this.children.remove(stats);
    }

    public void setActiveHero(Hero e) {

        this.modePointer = 0;
        this.children.remove(name);
        this.children.remove(health);

        this.activeHero = e;
        name = new TextField();
        name.setSize(124, 11);
        name.setPosition(2, 2);
        name.setString(this.activeHero.getName());
        name.setTextSize(1);
        name.setFontColor(Color.WHITE);

        health = new TextField();
        health.setSize(80, 11);
        health.setPosition(2,14);
        health.setTextSize(1);
        health.setString(Stat.LIFE.getIconString() + " " + this.activeHero.getResourceString(Stat.LIFE));
        health.setFontColor(Color.DARKGREEN);

//        this.portrait = new GUIElement();
//        this.portrait.setSize(32, 32);
//        this.portrait.setPosition(2, 14);
//        this.portrait.setPixels(SpriteLibrary.sprites.get(activeHero.getPortraitName()));

//        this.healthBar = new Bar();
//        this.healthBar.setSize(90, 15);
//        this.healthBar.setPosition(36, 14);
//        this.healthBar.setHero(this.activeHero);
//        this.healthBar.setResource(Stat.LIFE);
//        this.healthBar.setColor(Color.MEDIUMGREEN);

//        if (this.activeHero.getSecondaryResource() != null) {
//            this.resourceBar = new Bar();
//            this.resourceBar.setSize(90, 15);
//            this.resourceBar.setPosition(36, 31);
//            this.resourceBar.setHero(this.activeHero);
//            this.resourceBar.setResource(this.activeHero.getSecondaryResource());
//            this.resourceBar.setColor(Hero.getResourceColor(this.activeHero.getSecondaryResource()));
//            this.children.add(resourceBar);
//        } else {
//            this.children.remove(resourceBar);
//        }

        this.children.add(name);
        this.children.add(health);

        this.activateMode();
//        this.children.add(portrait);
//        this.children.add(healthBar);
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        for (GUIElement child: modeIcons) {
            int[] childP = child.render();
            fillWithGraphicsSize(child.getX(), child.getY(), child.getWidth(), child.getHeight(), childP, false);
        }
        renderChildren();
        if (this.active) {
            addBorder(this.width, this.height, this.pixels, Color.WHITE);
        }
        return this.pixels;
    }
}
