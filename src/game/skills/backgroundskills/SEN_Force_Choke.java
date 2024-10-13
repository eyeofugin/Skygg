package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Rooted;

import java.util.List;

public class SEN_Force_Choke extends Skill {
    private static final int ROOT_TURNS = 1;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 2;
    private static final int PUSH_DIST = 1;
    private static final int POWER = 10;

    public SEN_Force_Choke(Hero e) {
        super(e);
        this.name="sen_force_choke";
        this.translation="Force Choke";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.damageType = Stat.FORCE;
        this.effects = List.of(new Rooted(ROOT_TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.dmg = POWER;
        this.tags = List.of(AiSkillTag.CC, AiSkillTag.DMG);
    }
    @Override
    public Skill getCast() {
        SEN_Force_Choke cast = new SEN_Force_Choke(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Deals "+ POWER + " damage and roots target for " + ROOT_TURNS + " turns.";
    }
}
