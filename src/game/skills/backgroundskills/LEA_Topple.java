package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Disadvantage;

import java.util.List;

public class LEA_Topple extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int TURNS = 2;

    public LEA_Topple(Hero e) {
        super(e);
        this.name="lea_topple";
        this.translation="Topple";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.FIRST_TWO_ENEMIES;
        this.effects = List.of(new Disadvantage(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        LEA_Topple cast = new LEA_Topple(this.Hero);
        cast.copyFrom(this);
        return cast;
    }

    @Override
    public String getDescriptionFor(Hero e) {
        return "The two closest enemies get disadvantage for " + TURNS + " turns.";
    }

    @Override
    public int[] setupTargetMatrix() {
        if (this.Hero.enemy) {
            return new int[]{2,3};
        } else {
            return new int[]{4,5};
        }
    }
}
