package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_DoubleShot extends Skill {

    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.primary = true;
        this.countAsHits = 2;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.damageMode = DamageMode.PHYSICAL;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Hits.";
    }
    @Override
    public String getName() {
        return "Double Shot";
    }
}
