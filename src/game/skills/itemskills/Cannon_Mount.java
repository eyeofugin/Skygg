package game.skills.itemskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Rooted;

import java.util.List;

public class Cannon_Mount extends Skill {
    public boolean active = false;
    public Cannon_Mount(Hero Hero) {
        super(Hero);
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
        Cannon_Mount cast = new Cannon_Mount(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public int getDamageChanges(Hero caster, Hero target, Skill cast, int result, Stat damageType, boolean simulated) {
        int bonus = 0;
        if (caster == this.Hero && cast.isWeaponSkill()) {
            Logger.logLn(this.Hero.name + ".Cannon_mount.getDamageChanges");
            bonus = result;
        }
        return result + bonus;
    }
    @Override
    protected void individualResolve(Hero target) {
        ((Cannon_Mount)this.ogSkill).active = !active;
    }
    @Override
    public void update() {
        if (this.active) {
            Logger.logLn(this.Hero.name + ".Cannon_mount.update:add rooted");
            this.Hero.addEffect(new Rooted(1),null);
        }
    }
}
