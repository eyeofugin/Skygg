package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;

import java.util.List;

public class Blade_XChop extends Skill {
    public Blade_XChop(Hero Hero) {
        super(Hero);
        this.name="blade_xchop";
        this.translation="X Chop";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.STRENGTH,0.4));
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.DMG);
        this.cdMax=2;
        this.actionCost = 1;
        this.distance = 2;
        this.accuracy = -1;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Blade_XChop cast = new Blade_XChop(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
}
