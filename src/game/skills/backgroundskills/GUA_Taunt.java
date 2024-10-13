package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class GUA_Taunt extends Skill {
    private static final int TURNS = 1;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 2;

    public GUA_Taunt(Hero e) {
        super(e);
        this.name="gua_taunt";
        this.translation="Taunt";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Taunted(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        GUA_Taunt cast = new GUA_Taunt(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Target must auto attack ~ in the next turn.";
    }

}
