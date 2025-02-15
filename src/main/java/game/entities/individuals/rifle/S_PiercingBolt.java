package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.BlastingCounter;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_PiercingBolt extends Skill {

    public S_PiercingBolt(Hero hero) {
        super(hero);
        this.iconPath = "/icons/piercingbolt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 10;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Bleeding(1), this.hero);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Bleeds";
    }


    @Override
    public String getName() {
        return "Piercing Arrow";
    }
}
