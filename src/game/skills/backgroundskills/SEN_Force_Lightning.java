package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Paralysed;

import java.util.List;

public class SEN_Force_Lightning extends Skill {
    private static final int CHANCE = 20;
    private static final int TURNS = 3;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 5;
    private static final int DISTANCE = 2;
    private static final int POWER = 30;

    public SEN_Force_Lightning(Entity e) {
        super(e);
        this.name="sen_battle_meditation";
        this.translation="Battle Meditation";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.LINE;
        this.damageType = Stat.FORCE;
        this.effects = List.of(new Paralysed(TURNS, CHANCE));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.dmg = POWER;
        this.tags = List.of(AiSkillTag.DMG, AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        SEN_Force_Lightning cast = new SEN_Force_Lightning(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Deals " + POWER + " damage to the next "+ DISTANCE + " characters. Chance to paralyse.";
    }
}
