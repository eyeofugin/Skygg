package framework.graphics.containers;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.text.Color;
import framework.states.Arena;
import game.entities.Entity;
import game.skills.Skill;

public class ActiveAbilitiesCard extends GUIElement {

    public Engine engine;
    public Arena arena;
    private Entity activeEntity;
    public int abilityPointer = 0;

    GUIElement[] skillIcons = new GUIElement[5];
    SkillInfo skillInfo;

    public ActiveAbilitiesCard(Engine e, Arena arena) {
        super(Property.ACTIVE_ABILITY_WIDTH, Property.ACTIVE_ABILITY_HEIGHT);
        this.x = Property.ACTIVE_ABILITY_X;
        this.y = Property.ACTIVE_ABILITY_Y;
        this.engine = e;
        this.arena = arena;
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._upPressed) {
                if (abilityPointer != 0) {
                    abilityPointer--;
                    this.activateIcon();
                }
            }
            if (engine.keyB._downPressed) {
                if (abilityPointer != 4) {
                    abilityPointer++;
                    this.activateIcon();
                }
            }
            if (engine.keyB._enterPressed) {
                act();
            }
        }
    }
    private void act() {
        if (this.arena.activeEntity == this.activeEntity) {
            Skill s = getSkill();
            if (s != null) {
                if (this.activeEntity.canPerform(s)) {
                    this.active = false;
                    this.arena.chooseTargets(s);
                } else {
                    Logger.logLn("cannot perform");
                }
            }
        }
    }
    public void setActiveEntity(Entity e) {
        if (!e.enemy) {
            this.activeEntity = e;
            this.abilityPointer = 0;
            createSkillIcons();
            activateIcon();
        }
    }
    private void createSkillIcons() {
        int y = 2;
        for (int i = 0; i < this.activeEntity.skills.length; i++) {
            Skill s = this.activeEntity.skills[i];
            GUIElement skillIcon = new GUIElement();
            skillIcon.setSize(16, 16);
            skillIcon.setPixels(s!=null ? s.iconPixels : new int[16*16]);
            skillIcon.setPosition(2, y);
            this.skillIcons[i] = skillIcon;
            y+=18;
        }
    }
    private void activateIcon() {
        System.out.println("Activate Icon");
        for (GUIElement skillIcon : this.skillIcons) {
            skillIcon.removeBorder();
        }
        this.skillIcons[this.abilityPointer].setBorder(Color.WHITE, 1);
        setSkillInfo();
    }
    private void setSkillInfo() {
        Skill s = getSkill();
        if (s != null) {
            this.skillInfo = new SkillInfo(s);
            this.skillInfo.setPosition(24,2);
        }
    }

    @Override
    public int[] render() {
        background(Color.RED);
        for (GUIElement icon : this.skillIcons) {
            fillWithGraphicsSize(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight(), icon.render(), icon.isSimpleBorder());
        }
        fillWithGraphicsSize(this.skillInfo.getX(), this.skillInfo.getY(), this.skillInfo.getWidth(), this.skillInfo.getHeight(),
                this.skillInfo.render(), this.skillInfo.isSimpleBorder());
        return this.pixels;
    }

    private Skill getSkill() {
        if (this.activeEntity != null && this.activeEntity.skills.length > this.abilityPointer) {
            Skill s = this.activeEntity.skills[this.abilityPointer];
            if (s != null) {
                return s._cast;
            }
        }
        return null;
    }
}
