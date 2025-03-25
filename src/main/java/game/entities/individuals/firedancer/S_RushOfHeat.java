package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_RushOfHeat extends Skill {

    public S_RushOfHeat(Hero hero) {
        super(hero);
        this.iconPath = "entities/firedancer/icons/rushofheat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.RESTOCK);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.effects = List.of(new Burning(4));
        this.cdMax = 3;
        this.faithGain = true;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public int getAIRating(Hero target) {
        return -1* this.hero.getMissingLifePercentage() / 25;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get 4 burn. +6"+Stat.FAITH.getIconString();
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 6, this.hero);
    }

    @Override
    public String getName() {
        return "Rush of Heat";
    }
}
