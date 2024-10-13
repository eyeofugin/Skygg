package game.skills.itemskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.DefensiveStance;
import game.skills.changeeffects.effects.OffensiveStance;
import utils.MyMaths;

import java.util.List;

public class Blade_Duelist extends Skill {

    public enum Mode{
        OFF,DEF;
    }
    public Mode mode;

    public Blade_Duelist(Hero Hero) {
        super(Hero);
        this.name="blade_duelist";
        this.translation="Duelist";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.actionCost = 1;
        this.targetType = TargetType.SELF;
        this.weaponSkill = true;
        this.tags = List.of(AiSkillTag.SETUP);
    }
    @Override
    public Skill getCast() {
        Blade_Duelist cast = new Blade_Duelist(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    protected void individualResolve(Hero target) {
        applyChange(target);
        this.applySkillEffects(target);
    }
    public static void applyChange(Hero target) {
        if (target.hasPermanentEffect(OffensiveStance.class)>0) {
            target.removePermanentEffect(OffensiveStance.class);
            target.addEffect(new DefensiveStance(), null);
        } else if (target.hasPermanentEffect(DefensiveStance.class)>0){
            target.removePermanentEffect(DefensiveStance.class);
            target.addEffect(new OffensiveStance(), null);
        }
    }
    @Override
    public int getDamageChanges(Hero caster, Hero target, Skill cast, int result, Stat damageType, boolean simulated) {
        if (caster == this.Hero && cast.isWeaponSkill() && this.mode == Mode.OFF) {
            Logger.logLn(this.Hero.name + ".Blade_duelist.getDamageChanges:++");
            return (int)(result * 1.3);
        }
        if (target == this.Hero && this.mode == Mode.DEF) {
            Logger.logLn(this.Hero.name + ".Blade_duelist.getDamageChanges:--");
            return (int)(result*0.7);
        }
        return result;
    }
}
