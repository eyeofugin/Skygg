package game.skills.itemskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.DefensiveStance;
import game.skills.changeeffects.effects.OffensiveStance;
import utils.MyMaths;

import java.util.List;

public class Blade_FinesseStrike extends Skill {
    public Blade_FinesseStrike(Hero Hero) {
        super(Hero);
        this.name="blade_finessestrike";
        this.translation="Finesse Strike";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.actionCost = 1;
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.cdMax = 3;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.tags = List.of(AiSkillTag.SETUP, AiSkillTag.DMG);
        this.weaponSkill = true;
    }

    @Override
    public Skill getCast() {
        Blade_FinesseStrike cast = new Blade_FinesseStrike(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    protected void individualResolve(Hero target) {
        this.baseDamageChanges();
        int dmg = this.getDamage();
        Stat dt = this.getDamageType();
        int lethality = this.Hero.getCastingStat(this, Stat.LETHALITY);
        int critChance = this.Hero.getCastingStat(this, Stat.CRIT_CHANCE);
        if (MyMaths.success(critChance)) {
            this.dmg*=2;
            this.Hero.arena.critTrigger(target, this);
        }
        if (dmg>0) {
            int doneDamage = target.damage(this.Hero, dmg, dt, lethality, this);
            this.Hero.arena.dmgTrigger(target,this, doneDamage);
        }
        this.Hero.removePermanentEffect(DefensiveStance.class);
        this.Hero.addEffect(new OffensiveStance(), null);
        this.applySkillEffects(target);
    }
}
