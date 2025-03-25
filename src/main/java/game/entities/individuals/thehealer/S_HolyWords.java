package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/holywords.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.manaCost = 3;
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.2),
                new Multiplier(Stat.MANA, 0.3));
        this.abilityType = AbilityType.TACTICAL;
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Strong heal.";
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
