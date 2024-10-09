package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Paralysed;

import java.util.List;

public class TEC_Shock_Gauntlet extends Skill {

    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 1;
    private static final int TURNS = 1;
    private static final int POWER = 20;

    public TEC_Shock_Gauntlet(Entity e) {
        super(e);
        this.name="tec_shock_gauntlet";
        this.translation="Shock Gauntlet";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Paralysed(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.dmg = POWER;
        this.tags = List.of(AiSkillTag.DMG, AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        TEC_Shock_Gauntlet cast = new TEC_Shock_Gauntlet(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Deals " + POWER + " damage to target and paralyses them for " + TURNS + " turns.";

    }
}
