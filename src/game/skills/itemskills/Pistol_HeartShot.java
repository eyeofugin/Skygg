package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class Pistol_HeartShot extends Skill {
    public Pistol_HeartShot(Hero Hero) {
        super(Hero);
        this.name="pistol_heartshot";
        this.translation="Heart Shot";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.8));
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.DMG);
        this.actionCost = 1;
        this.distance = 3;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Pistol_HeartShot cast = new Pistol_HeartShot(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
}
