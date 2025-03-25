package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class S_Taunt extends Skill {

    public S_Taunt(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/taunt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5};
        this.cdMax = 5;
        this.effects = List.of(new Taunted(2));
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        return 5;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Taunts target";
    }

    @Override
    public String getName() {
        return "Taunt";
    }
}
