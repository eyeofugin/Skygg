package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Paralysed;

import java.util.List;

public class LEA_Confusing_Tactics extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int TURNS = 2;

    public LEA_Confusing_Tactics(Entity e) {
        super(e);
        this.name="lea_confusing_tactics";
        this.translation="Confusing tactics";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.TWO_RDM;
        this.effects = List.of(new Paralysed(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        LEA_Confusing_Tactics cast = new LEA_Confusing_Tactics(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Confuse two random enemies for " + TURNS + " turns.";
    }
}
