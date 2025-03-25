package game.entities.individuals.longsword;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_Stab extends Skill {

    public S_Stab(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/stab.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2,3};
        this.possibleTargetPositions = new int[]{4};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmg = 4;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.effects = List.of(new Bleeding(1));
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;

    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Bleeds.";
    }

    @Override
    public String getName() {
        return "Stab";
    }
}
