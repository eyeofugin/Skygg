package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Bounty;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class HUN_Flamethrower extends Skill {
    private static final int BURN_CHANCE = 10;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int POWER = 20;

    public HUN_Flamethrower(Entity e) {
        super(e);
        this.name="hun_flamethrower";
        this.translation="Flamethrower";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.LINE;
        this.damageType = Stat.RANGED;
        this.effects = List.of(new Injured(3, BURN_CHANCE));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.dmg = POWER;
        this.tags = List.of(AiSkillTag.DMG);
    }

    @Override
    public Skill getCast() {
        HUN_Flamethrower cast = new HUN_Flamethrower(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Deals " + POWER + " burning damage to all targets in front for a distance of X where X is 1 plus the amount of bounty stacks ~ has. " + BURN_CHANCE + "% chance to burn.";
    }
    @Override
    public int getDistance() {
        return 1 + this.entity.hasPermanentEffect(Bounty.class);
    }
}
