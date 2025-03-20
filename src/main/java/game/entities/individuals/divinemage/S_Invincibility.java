package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Immunity;
import game.skills.changeeffects.effects.Invincible;

import java.util.List;

public class S_Invincibility extends Skill {

    public S_Invincibility(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/invincibility.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.faithCost = 5;
        this.effects = List.of(new Invincible(2));
        this.distance = 2;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        if (target.getCurrentLifePercentage() < 25) {
            return 4;
        }
        if (target.getCurrentLifePercentage() < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gains invincible for 2 turns";
    }


    @Override
    public String getName() {
        return "Invincibility";
    }
}
