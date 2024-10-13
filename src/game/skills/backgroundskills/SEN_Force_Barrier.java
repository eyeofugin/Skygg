package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Resilient;

import java.util.List;

public class SEN_Force_Barrier extends Skill {

    private static final int TURNS = 3;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 5;

    public SEN_Force_Barrier(Hero e) {
        super(e);
        this.name="sen_force_barrier";
        this.translation="Force Barrier";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE_ALLY;
        this.effects = List.of(new Resilient(TURNS));
        this.actionCost = ACTION_COST;
        this.distance = 2;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.BUFF);
    }
    @Override
    public Skill getCast() {
        SEN_Force_Barrier cast = new SEN_Force_Barrier(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Gain resilient for " + TURNS + " turns." ;
    }
}
