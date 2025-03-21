package game.entities.individuals.sniper;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Blinded;

import java.util.List;

public class S_BlindingShot extends Skill {

    public S_BlindingShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/blindingshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.cdMax = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.dmg = 8;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.effects = List.of(new Blinded(2));
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Blinds";
    }

    @Override
    public String getName() {
        return "Blinding Shot";
    }
}
