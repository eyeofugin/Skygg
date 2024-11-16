package framework.graphics.elements;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import game.entities.Hero;
import game.skills.Effect;

import java.util.Arrays;
import java.util.List;

public class EffectField extends GUIElement {
    private final Hero hero;
    private int pointer = 0;
    private int relativePointer = 0;
    private final int AMOUNT_VISIBLE_EFFECTS = 5;
    private final int EFFECT_SIZE = 10;
    private final int BUTTON_SIZE = 8;
    private final int LEFT_BUTTON_X = 0;
    private final int RIGHT_BUTTON_X = EFFECT_SIZE * AMOUNT_VISIBLE_EFFECTS;
    private final int EFFECT_Y = 2;
    private final int EFFECTS_X_FROM = LEFT_BUTTON_X + BUTTON_SIZE + 2;

    private GUIElement leftArrow;
    private GUIElement rightArrow;
    private GUIElement[] effectIcons;
    //EffectInfo effectInfo;
    private Engine engine;

    public EffectField(Hero hero, Engine engine) {
        this.hero = hero;
        this.effectIcons = new GUIElement[0];
        this.setSize(100, 100);
        this.engine = engine;
        this.setArrows(0);
    }
    @Override
    public void update(int frame) {
        if (this.active) {
            if (!this.hero.getEffects().isEmpty()) {
                if (engine.keyB._leftPressed) {
                    recalculateEffectList(-1);
                }

                if (engine.keyB._rightPressed) {
                    recalculateEffectList(1);
                }
            }
            updateChildren(frame);
        }
    }

    private void recalculateEffectList(int dir) {
        this.children.remove(leftArrow);
        this.children.remove(rightArrow);

        List<Effect> effects = this.hero.getEffects();
        setNextPointers(effects.size(), dir);

        setArrows(effects.size());

        setEffects();
    }

    private void setEffects() {
        List<Effect> effects = this.hero.getEffects();
        this.effectIcons = new GUIElement[Math.min(5, effects.size())];
        int relativeFrom = this.relativePointer - this.pointer;
        int xFrom = EFFECTS_X_FROM;
        for (int i = 0; i < this.effectIcons.length; i++) {
            int query = i + relativeFrom;
            boolean active = query == this.relativePointer;
            Effect effect = this.hero.getEffects().get(query);
            int[] pixels = new int[EFFECT_SIZE*EFFECT_SIZE];
            Arrays.fill(pixels, Color.VOID.VALUE);
            if (active) {
                GUIElement.addBorder(EFFECT_SIZE,EFFECT_SIZE, pixels, Color.WHITE);
            }
            GUIElement.staticFillSize(1, 1, Property.EFFECT_ICON_SIZE, 8, EFFECT_SIZE, pixels,  SpriteLibrary.getSprite(effect.getClass().getName()));

            GUIElement effectElement = new GUIElement();
            effectElement.setSize(9,9);
            effectElement.setPosition(xFrom, EFFECT_Y);
            effectElement.setPixels(pixels);
            this.effectIcons[i] = effectElement;
            xFrom += EFFECT_SIZE;
        }
    }

    private void setArrows(int listSize) {
        String leftIconName = this.relativePointer > pointer ? "scrollArrowActive_l" : "scrollArrowInactive_l";
        int[] leftArrowPixels = SpriteLibrary.getSprite(leftIconName);
        this.leftArrow = new GUIElement();
        this.leftArrow.setSize(8,8);
        this.leftArrow.setPixels(leftArrowPixels);
        this.leftArrow.setPosition(LEFT_BUTTON_X,EFFECT_Y);
        this.children.add(leftArrow);

        boolean rightActive = Math.max(0, listSize - 1) - this.relativePointer > (AMOUNT_VISIBLE_EFFECTS - 1) - pointer;
        String rightIconName = rightActive ? "scrollArrowActive_r" : "scrollArrowInactive_r";
        int[] rightArrowPixels = SpriteLibrary.getSprite(rightIconName);
        this.rightArrow = new GUIElement();
        this.rightArrow.setSize(8,8);
        this.rightArrow.setPixels(rightArrowPixels);
        this.rightArrow.setPosition(RIGHT_BUTTON_X, EFFECT_Y);
        this.children.add(rightArrow);
    }

    private void setNextPointers(int listSize, int dir) {
        int newPointer = this.pointer + dir;
        if (listSize <= this.AMOUNT_VISIBLE_EFFECTS) {
            this.pointer = Math.min(Math.max(0, newPointer), this.AMOUNT_VISIBLE_EFFECTS -1);
            this.relativePointer = this.pointer;
        } else {
            if (newPointer < 0) {
                if (relativePointer > 0) {
                    relativePointer--;
                }
            } else if (newPointer >= AMOUNT_VISIBLE_EFFECTS) {
                if (relativePointer < listSize-1) {
                    relativePointer++;
                }
            }else {
                this.pointer = newPointer;
                this.relativePointer += dir;
            }
        }
    }

    @Override
    public int[] render() {
        renderChildren();
        renderEffects();
        return this.pixels;
    }

    private void renderEffects() {
        for (GUIElement effect : this.effectIcons) {
            fillWithGraphicsSize(effect.getX(), effect.getY(), effect.getWidth(), effect.getHeight(), effect.render(), false);
        }
    }

}
