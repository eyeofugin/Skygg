package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SEN_Mind_Trick extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 2;
    private static final int PUSH_DIST = 1;
    public static final int TURNS = 2;

    public SEN_Mind_Trick(Entity e) {
        super(e);
        this.name="sen_mind_trick";
        this.translation="Mind Trick";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        SEN_Mind_Trick cast = new SEN_Mind_Trick(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "A random ability of the target is blocked for " + TURNS + " turns.";
    }
    @Override
    protected void individualResolve(Entity target) {
        Random rand = new Random();
        List<Skill> nonPassiveSkills = Arrays.stream(target.skills).filter(s->!s.isPassive()).toList();
        if (nonPassiveSkills.size()>0) {
            Skill randSkill = nonPassiveSkills.get(rand.nextInt(nonPassiveSkills.size()));
            randSkill.setBlockedTurns(TURNS);
            this.applySkillEffects(target);
        }

    }
}
