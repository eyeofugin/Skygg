package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_BellowingRoar extends Skill {

    public S_BellowingRoar(Hero hero) {
        super(hero);
        this.iconPath = "entities/dragonbreather/icons/bellowingroar.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.effects = List.of(new Threatening(2));
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.75));
        this.manaCost = 12;
    }
    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 3;
        }
        return 0;
    }

    @Override
    public String getName() {
        return "Bellowing Roar";
    }
}
