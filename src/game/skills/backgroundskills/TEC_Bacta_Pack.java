package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class TEC_Bacta_Pack extends Skill {

    private static final int POWER = 20;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 1;

    public TEC_Bacta_Pack(Hero e) {
        super(e);
        this.name="tec_bacta_pack";
        this.translation="Bacta Pack";
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
        TEC_Bacta_Pack cast = new TEC_Bacta_Pack(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Heals target for " + POWER ;
    }

    @Override
    protected void individualResolve(Hero target) {
        int heal = this.getHeal();
        target.heal(this.Hero, heal, this);
        this.applySkillEffects(target);
    }
}
