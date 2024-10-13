package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.Disadvantage;
import game.skills.changeeffects.statusinflictions.Disarmed;

import java.util.List;

public class Blade_DisarmingSwipe extends Skill {
    public Blade_DisarmingSwipe(Hero Hero) {
        super(Hero);
        this.name="blade_disarmingswipe";
        this.translation="Disarming Strike";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.STRENGTH,0.6));
        this.effects = List.of(new Disarmed(1, 30));
        this.targetType = TargetType.LINE;
        this.tags = List.of(AiSkillTag.CC, AiSkillTag.DMG);
        this.cdMax=3;
        this.actionCost = 1;
        this.distance = 2;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Blade_DisarmingSwipe cast = new Blade_DisarmingSwipe(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
}
