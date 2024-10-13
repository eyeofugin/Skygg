package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class TEC_Smoke_Bomb extends Skill {

    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 2;
    private static final int TURNS = 2;

    public TEC_Smoke_Bomb(Hero e) {
        super(e);
        this.name="tec_smoke_bomb";
        this.translation="Smoke Bomb";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(Effect.newStatChange(-2, Stat.ACCURACY));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        TEC_Smoke_Bomb cast = new TEC_Smoke_Bomb(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Blinds target for " + TURNS + " turns.";
    }
}
