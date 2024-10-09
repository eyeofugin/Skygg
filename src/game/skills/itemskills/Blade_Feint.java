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

public class Blade_Feint extends Skill {
    public Blade_Feint(Entity entity) {
        super(entity);
        this.name="blade_feint";
        this.translation="Feint";
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
        Blade_Feint cast = new Blade_Feint(this.entity);
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
        this.entity.removePermanentEffect(OffensiveStance.class);
        this.entity.addEffect(new DefensiveStance(), null);
        this.applySkillEffects(target);
    }
}
