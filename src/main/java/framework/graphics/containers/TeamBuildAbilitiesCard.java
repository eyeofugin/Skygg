package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamBuildAbilitiesCard extends GUIElement {
    public static final int _WIDTH = 400;
    public static final int _HEIGHT = 250;

    private final Engine engine;

    private Hero hero;
    private int abilityPointer = 0;
    private List<Skill> availableSkills = new ArrayList<>();
    private List<Skill> chosenSkills = new ArrayList<>();
    private int primaryChosen = 0;
    private int tacticalChosen = 0;
    private int ultimateChosen = 0;

    public TeamBuildAbilitiesCard(Engine e) {
        super(_WIDTH, _HEIGHT);
        this.engine = e;
        this.x = 220;
        this.y = 10;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.availableSkills.addAll(Arrays.stream(this.hero.getPrimary()).toList());
        this.availableSkills.addAll(Arrays.stream(this.hero.getTactical()).toList());
        this.availableSkills.add(this.hero.getUlt());
    }
    public void finish() {
        this.chosenSkills.add(new S_Skip(this.hero));
        this.hero.setSkills(this.chosenSkills.toArray(new Skill[0]));
    }
    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (this.abilityPointer > 0) {
                    this.abilityPointer--;
                }
            }
            if (engine.keyB._downPressed) {
                if (this.abilityPointer < this.availableSkills.size()-1) {
                    this.abilityPointer++;
                }
            }
            if (engine.keyB._enterPressed) {
                chooseAbility();
            }
            if (engine.keyB._backPressed) {
                removeAbility();
            }
        }
    }

    private void chooseAbility() {
        Skill skill = this.availableSkills.get(this.abilityPointer);
        if (this.chosenSkills.contains(skill)) {
            this.chosenSkills.remove(skill);
            switch (skill.abilityType) {
                case PRIMARY -> this.primaryChosen--;
                case TACTICAL -> this.tacticalChosen--;
                case ULT -> this.ultimateChosen--;
            }
            return;
        }
        switch (skill.abilityType) {
            case PRIMARY -> {
                if (this.primaryChosen == 0) {
                    this.primaryChosen++;
                    this.chosenSkills.add(skill);
                }
            }
            case TACTICAL -> {
                if (this.tacticalChosen < 2) {
                    this.tacticalChosen++;
                    this.chosenSkills.add(skill);
                }
            }
            case ULT -> {
                if (this.ultimateChosen == 0) {
                    this.ultimateChosen++;
                    this.chosenSkills.add(skill);
                }
            }
        }
    }

    private void removeAbility() {
        Skill skill = this.availableSkills.get(this.abilityPointer);
        if (this.chosenSkills.contains(skill)) {
            this.chosenSkills.remove(skill);
            switch (skill.abilityType) {
                case PRIMARY -> this.primaryChosen--;
                case TACTICAL -> this.tacticalChosen--;
                case ULT -> this.ultimateChosen--;
            }
        }
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderAvailableSkills();
        renderSkillInfo();
        return this.pixels;
    }
    private void renderAvailableSkills() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.availableSkills.size(); i++) {
            Skill skill = this.availableSkills.get(i);
            Color borderColor = getBorderColorFor(skill, i);
            SkillElement element = new SkillElement(skill);
            fillWithGraphicsSize(x, y, SkillElement._WIDTH, SkillElement._HEIGHT, element.render(), true, borderColor);
            y += SkillElement._HEIGHT + 2;
        }
    }

    private void renderSkillInfo() {
        SkillInfo info = new SkillInfo(this.availableSkills.get(this.abilityPointer));
        info.setPosition(130,10);
        fillWithGraphicsSize(130, 10, info.getWidth(), info.getHeight(), info.render(), false);
    }

    private Color getBorderColorFor(Skill skill, int i) {
        if (i == this.abilityPointer) {
            return Color.WHITE;
        }
        if (this.chosenSkills.contains(skill)) {
            return Color.GREEN;
        }
        if (isFullFor(skill)) {
            return Color.RED;
        }
        return Color.VOID;
    }

    private boolean isFullFor(Skill skill) {
        switch (skill.abilityType) {
            case PRIMARY -> {
                if (this.primaryChosen > 0) {
                    return true;
                }
            }
            case TACTICAL -> {
                if (this.tacticalChosen > 1) {
                    return true;
                }
            }
            case ULT -> {
                if (this.ultimateChosen > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
