package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Paralysed;

import java.util.List;

public class HUN_Spring_the_trap extends Skill {
    private static final int TURNS = 0;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int DISTANCE = 2;

    public HUN_Spring_the_trap(Entity e) {
        super(e);
        this.name="hun_spring_the_trap";
        this.translation="Spring the trap";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Paralysed(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        HUN_Spring_the_trap cast = new HUN_Spring_the_trap(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Target is paralysed.";
    }
}
