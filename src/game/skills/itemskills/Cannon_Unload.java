package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;

import java.util.List;

public class Cannon_Unload extends Skill {
    public Cannon_Unload(Hero Hero) {
        super(Hero);
        this.name="cannon_unload";
        this.translation="Unload";
        this.description= "Fires all remaining heat into the first enemy";
        this.animationR="aaR";
        this.animationL="aaL";
        this.targetType = TargetType.FIRST_ENEMY;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.1),
                new Multiplier(Stat.STRENGTH,0.2));
        this.accuracy = 100;
        this.actionCost = 1;
        this.distance = 3;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }

    @Override
    public Skill getCast() {
        Cannon_Unload cast = new Cannon_Unload(this.Hero);
        cast.copyFrom(this);
        int overheat = this.Hero.getPrimary().getMaxOverheat()-this.Hero.getPrimary().getCurrentOverheat();
        cast.countAsHits = overheat;
        cast.overheatCost = overheat;
        return cast;
    }

//    @Override
//    public int getOverheatCost() {
//        return this.Hero.getPrimary().getMaxOverheat()-this.Hero.getPrimary().getCurrentOverheat();
//    }
//    @Override
//    public int getCountsAsHits() {
//        return this.Hero.getPrimary().getMaxOverheat()-this.Hero.getPrimary().getCurrentOverheat();
//    }
}
