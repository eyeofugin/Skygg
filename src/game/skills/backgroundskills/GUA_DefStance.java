package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class GUA_DefStance extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;

    public GUA_DefStance(Hero e) {
        super(e);
        this.name="gua_def_stance";
        this.translation="Defensive Stance";

        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.effects = List.of(Effect.newStatChange(1, Stat.VITALITY),Effect.newStatChange(1, Stat.REFLEXES));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.BUFF);
    }
    @Override
    public Skill getCast() {
        GUA_DefStance cast = new GUA_DefStance(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Boosts Vitality and Reflexes";
    }
}
