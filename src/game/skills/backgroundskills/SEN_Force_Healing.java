package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class SEN_Force_Healing extends Skill {

    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 5;
    private static final int DISTANCE = 2;
    private static final int POWER = 20;

    public SEN_Force_Healing(Entity e) {
        super(e);
        this.name="sen_force_healing";
        this.translation="Force Healing";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.heal = POWER;
        this.tags = List.of(AiSkillTag.HEAL);
    }
    @Override
    public Skill getCast() {
        SEN_Force_Healing cast = new SEN_Force_Healing(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Cleanses target and heals for " + POWER + "." ;
    }

    @Override
    protected void individualResolve(Entity target) {
        int heal = this.getHeal();
        target.heal(this.entity, heal, this);
        target.cleanse();
        this.applySkillEffects(target);
    }
}
