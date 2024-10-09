package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Paralysed;

import java.util.List;

public class SEN_Force_Stun extends Skill {
    private static final int PARA_TURNS = 1;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 2;
    private static final int PUSH_DIST = 1;
    private static final int POWER = 10;

    public SEN_Force_Stun(Entity e) {
        super(e);
        this.name="sen_force_stun";
        this.translation="Force Stun";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.damageType = Stat.FORCE;
        this.effects = List.of(new Paralysed(PARA_TURNS));
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.dmg = POWER;
        this.tags = List.of(AiSkillTag.DMG, AiSkillTag.CC);
    }
    @Override
    public Skill getCast() {
        SEN_Force_Stun cast = new SEN_Force_Stun(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Deals "+ POWER + " damage and paralyses target. Then pushes target up to " + PUSH_DIST + " fields away.";
    }
    @Override
    protected void individualResolve(Entity target) {
        int dmg = this.getDamage();
        int lethality = this.entity.getCastingStat(this, Stat.LETHALITY);
        if (dmg > 0) {
            int doneDamage = target.damage(this.entity, dmg, this.damageType, lethality, this);
            this.entity.arena.dmgTrigger(target,this, doneDamage);
        }
        int dir = this.entity.position < target.position ? -1: 1;
        this.entity.arena.move(target, PUSH_DIST, dir);
        this.applySkillEffects(target);
    }
}
