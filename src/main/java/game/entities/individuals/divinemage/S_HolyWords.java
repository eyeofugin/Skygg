package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/holywords.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.heal = 2;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.faithCost = 2;
        this.distance = 2;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
