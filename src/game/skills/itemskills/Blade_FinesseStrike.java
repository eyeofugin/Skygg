package game.skills.itemskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.DefensiveStance;
import game.skills.changeeffects.effects.OffensiveStance;
import utils.MyMaths;

import java.util.List;

public class Blade_FinesseStrike extends Skill {
    public Blade_FinesseStrike(Entity entity) {
        super(entity);
        this.name="blade_finessestrike";
        this.translation="Finesse Strike";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.actionCost = 1;
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.cdMax = 3;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.tags = List.of(AiSkillTag.SETUP, AiSkillTag.DMG);
        this.weaponSkill = true;
    }

    @Override
    public Skill getCast() {
        Blade_FinesseStrike cast = new Blade_FinesseStrike(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    protected void individualResolve(Entity target) {
        this.baseDamageChanges();
        int dmg = this.getDamage();
        Stat dt = this.getDamageType();
        int lethality = this.entity.getCastingStat(this, Stat.LETHALITY);
        int critChance = this.entity.getCastingStat(this, Stat.CRIT_CHANCE);
        if (MyMaths.success(critChance)) {
            this.dmg*=2;
            this.entity.arena.critTrigger(target, this);
        }
        if (dmg>0) {
            int doneDamage = target.damage(this.entity, dmg, dt, lethality, this);
            this.entity.arena.dmgTrigger(target,this, doneDamage);
        }
        this.entity.removePermanentEffect(DefensiveStance.class);
        this.entity.addEffect(new OffensiveStance(), null);
        this.applySkillEffects(target);
    }
}
