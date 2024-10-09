package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class LEA_Ammo extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    public static final int OVERHEAT_VALUE = 1;

    public LEA_Ammo(Entity e) {
        super(e);
        this.name="lea_ammo";
        this.translation="Restock";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.ALL_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.RESTOCK);
    }
    @Override
    public Skill getCast() {
        LEA_Ammo cast = new LEA_Ammo(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Reduces overheat for all allies for " + OVERHEAT_VALUE;
    }

    @Override
    protected void individualResolve(Entity target) {
        target.getPrimary().changeOverheat(-1*OVERHEAT_VALUE);
        this.applySkillEffects(target);
    }
}
