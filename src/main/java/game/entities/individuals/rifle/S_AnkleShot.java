package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_AnkleShot extends Skill {

    public S_AnkleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/ankleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 11;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -2);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives target -2 Speed";
    }


    @Override
    public String getName() {
        return "Ankle Shot";
    }
}
