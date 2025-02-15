package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.containers.HUD;
import framework.graphics.text.Color;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.objects.equipments.skills.S_GraftedExoskeleton;

import java.util.ArrayList;
import java.util.List;

public class Draft extends GUIElement {

    public final Engine engine;
    private final HUD hud;

    private Hero[] draftChoices = new Hero[6];
    public Hero[] draftResult = new Hero[3];
    private int draftPointer = 0;
    public int round = 0;
    public boolean finished = false;

    public Draft(Engine engine) {
        super(Engine.X, Engine.Y);
        this.engine = engine;
        this.hud = new HUD(engine);
        this.hud.setDraft(this);
        this.round = 1;

        this.initDraft();
    }

    private void initDraft() {

        List<Hero> drafted = new ArrayList<>();
        Hero hero1 = DraftBuilder.getRandomBackLiner(drafted);
        draftChoices[0] = hero1;
        drafted.add(hero1);

        Hero hero2 = DraftBuilder.getRandomMiddleLiner(drafted);
        draftChoices[1] = hero2;
        drafted.add(hero2);

        Hero hero3 = DraftBuilder.getRandomFrontLiner(drafted);
        draftChoices[2] = hero3;
        drafted.add(hero3);

        for (int i = 0; i < 3; i++) {
            Hero flex = DraftBuilder.getRandomFlexPick(drafted);
            draftChoices[3+i] = flex;
            drafted.add(flex);
        }
        activateHero();
    }

    @Override
    public void update(int frame) {
        if (active) {
            updateKeys();
        }
        updateDraft(frame);
        this.hud.update(frame);
    }
    private void updateKeys() {
        if (engine.keyB._leftPressed) {
            if (draftPointer != 0) {
                draftPointer--;
                this.activateHero();
            }
        }
        if (engine.keyB._rightPressed) {
            if (draftPointer != draftChoices.length-1) {
                draftPointer++;
                this.activateHero();
            }
        }
        if (engine.keyB._enterPressed) {
            this.choose();
        }
        if (engine.keyB._backPressed) {
            this.remove();
        }
        if (engine.keyB._contextPressed) {
            this.activateHUD();
        }
    }
    private void updateDraft(int frame) {
        for (Hero hero : this.draftChoices) {
            hero.update(frame);
        }
    }

    private void choose() {
        boolean hasEmptySlot = false;
        for (Hero hero : this.draftResult) {
            if (hero == null) {
                hasEmptySlot = true;
                break;
            }
        }
        if (!hasEmptySlot) {
            System.out.println("Full Draft");
            this.finished = true;
            return;
        }
        int nextEmptyResult = 99;
        for ( int i = 0; i < this.draftResult.length; i++ ) {
            if (this.draftResult[i] == null) {
                nextEmptyResult = i;
                break;
            }
        }
        Hero choice = this.draftChoices[this.draftPointer];
        choice.draft(nextEmptyResult+1);
        this.draftResult[nextEmptyResult] = choice;
    }

    private void remove() {
        Hero choice = this.draftChoices[this.draftPointer];
        int draftChoice = choice.getDraftChoice();
        if (draftChoice != 0) {
            this.draftResult[draftChoice-1] = null;
            choice.removeFromDraft();
        }
    }

    private void activateHUD() {
        this.active = false;
        this.hud.activateTeamDraftOV();
    }

    private void activateHero() {
        this.hud.setActiveHero(this.draftChoices[this.draftPointer]);
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderDraft();
        renderHUD();
        renderChildren();
        return this.pixels;
    }
    private void renderDraft() {
        int x = 20;
        int y = 20;
        for (int i = 0; i < draftChoices.length; i++) {
            Hero hero = this.draftChoices[i];
            fillWithGraphicsSize(x, y, hero.getWidth(), hero.getHeight(), hero.render(), this.draftPointer == i);
            x += hero.getWidth()+10;
        }
    }
    private void renderHUD() {
        fillWithGraphicsSize(0,0,640,360,hud.render(),null);
    }
}
