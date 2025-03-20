package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Blight;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_UnleashEmptiness extends Skill {

    public S_UnleashEmptiness(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/unleashemptiness.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.SELF;
        this.manaCost = 5;
        this.lifeCost = 5;
        this.ultimate = true;
        this.abilityType = AbilityType.ULT;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.removeNegativeEffects();
        this.hero.addToStat(Stat.MAGIC, this.hero.getStat(Stat.ENDURANCE) / 2);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Remove all debuffs. +X Magic where X is 50% of your Endurance.";
    }


    @Override
    public String getName() {
        return "Unleash Emptiness";
    }
}
