package game.skills.itemskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Rooted;

import java.util.List;

public class Cannon_Mount extends Skill {
    public boolean active = false;
    public Cannon_Mount(Entity entity) {
        super(entity);
        this.name="cannon_mount";
        this.translation="Mount";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.actionCost = 1;
        this.targetType = TargetType.SELF;
        this.tags = List.of(AiSkillTag.SETUP);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Cannon_Mount cast = new Cannon_Mount(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill cast, int result, Stat damageType, boolean simulated) {
        int bonus = 0;
        if (caster == this.entity && cast.isWeaponSkill()) {
            Logger.logLn(this.entity.name + ".Cannon_mount.getDamageChanges");
            bonus = result;
        }
        return result + bonus;
    }
    @Override
    protected void individualResolve(Entity target) {
        ((Cannon_Mount)this.ogSkill).active = !active;
    }
    @Override
    public void update() {
        if (this.active) {
            Logger.logLn(this.entity.name + ".Cannon_mount.update:add rooted");
            this.entity.addEffect(new Rooted(1),null);
        }
    }
}
