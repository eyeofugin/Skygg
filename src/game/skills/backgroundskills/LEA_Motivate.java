package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class LEA_Motivate extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;

    public LEA_Motivate(Hero e) {
        super(e);
        this.name="lea_motivate";
        this.translation="Motivate";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = 2;
        this.tags = List.of(AiSkillTag.HEAL);
    }
    @Override
    public Skill getCast() {
        LEA_Motivate cast = new LEA_Motivate(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Removes all debuffs of target.";
    }
}
