package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.Disadvantage;

import java.util.List;

public class Blade_SarlaccSweep extends Skill {
    public Blade_SarlaccSweep(Hero Hero) {
        super(Hero);
        this.name="blade_sarlaccsweep";
        this.translation="Sarlacc Sweep";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.STRENGTH,0.4),
                new Multiplier(Stat.REFLEXES,0.3));
        this.effects = List.of(new Disadvantage());
        this.targetType = TargetType.LINE;
        this.tags = List.of(AiSkillTag.CC, AiSkillTag.DMG);
        this.cdMax=2;
        this.actionCost = 1;
        this.distance = 2;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Blade_SarlaccSweep cast = new Blade_SarlaccSweep(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
}
