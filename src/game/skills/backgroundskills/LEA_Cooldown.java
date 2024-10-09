package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LEA_Cooldown extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    public static final int CD_VALUE = 1;

    public LEA_Cooldown(Entity e) {
        super(e);
        this.name="lea_agitate";
        this.translation="Agitate";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.ALL_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.RESTOCK);
    }
    @Override
    public Skill getCast() {
        LEA_Cooldown cast = new LEA_Cooldown(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Reduces cooldown for all allies for " + CD_VALUE;
    }
    @Override
    protected void individualResolve(Entity target) {
        List<Skill> skillsWithCD = Arrays.stream(target.skills).filter(skill -> skill.getCdCurrent()>0).toList();
        if (skillsWithCD.size()>0) {
            Random rand = new Random();
            Skill rdmSkill = skillsWithCD.get(rand.nextInt(skillsWithCD.size()));
            rdmSkill.setCdCurrent(Math.max(0,rdmSkill.getCdCurrent()-CD_VALUE));
            this.applySkillEffects(target);
        }
    }
}
