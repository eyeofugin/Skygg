package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Advantage;

import java.util.List;

public class LEA_Give_orders extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int TURNS = 2;
    private static final int TARGET_DIST = 2;

    public LEA_Give_orders(Hero e) {
        super(e);
        this.name="lea_give_orders";
        this.translation="Give orders";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE_ALLY;
        this.effects = List.of(new Advantage(TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = TARGET_DIST;
        this.tags = List.of(AiSkillTag.BUFF);
    }
    @Override
    public Skill getCast() {
        LEA_Give_orders cast = new LEA_Give_orders(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Target gets advantage for " + TURNS + " turns.";
    }
}
