package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class SEN_Battle_Meditation extends Skill {
    private static final int TURNS = 3;
    private static final int ACTION_COST = 2;
    private static final int CD_TOTAL = 5;

    public SEN_Battle_Meditation(Hero e) {
        super(e);
        this.name="sen_battle_meditation";
        this.translation="Battle Meditation";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.effects = List.of(Effect.newStatChange(2,Stat.SPEED),
                Effect.newStatChange(2,Stat.VITALITY),
                Effect.newStatChange(2,Stat.REFLEXES),
                Effect.newStatChange(2,Stat.HARMONY),
                Effect.newStatChange(2,Stat.ACCURACY));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.SETUP);
    }
    @Override
    public Skill getCast() {
        SEN_Battle_Meditation cast = new SEN_Battle_Meditation(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Boosts all non-offensive stats." ;
    }

    @Override
    public int[] setupTargetMatrix() {
        return new int[]{this.Hero.position};
    }
    @Override
    protected void individualResolve(Hero target) {
        this.applySkillEffects(target);
    }
}
