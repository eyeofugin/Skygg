package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Timeout;

import java.util.List;

public class HUN_Lure_in extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 4;
    private static final int DISTANCE = 3;
    private static final int PULL_DIST = 2;

    public HUN_Lure_in(Entity e) {
        super(e);
        this.name="hun_lure_in";
        this.translation="Lure in";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Timeout(2));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.PEEL);
    }
    @Override
    public Skill getCast() {
        HUN_Lure_in cast = new HUN_Lure_in(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Pull target up to " + PULL_DIST + " fields towards you. Stun them.";
    }
    @Override
    protected void individualResolve(Entity target) {
        int dir = this.entity.position < target.position ? 1: -1;
        this.entity.arena.move(target, PULL_DIST, dir);
        this.applySkillEffects(target);
    }
}
