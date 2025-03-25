package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Recharge extends Skill {

    public S_Recharge(Hero hero) {
        super(hero);
        this.iconPath = "entities/thewizard/icons/recharge.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.RESTOCK);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.manaCost = 0;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getResourcePercentage(Stat.CURRENT_MANA) < 25) {
            return 4;
        }
        if (this.hero.getResourcePercentage(Stat.CURRENT_MANA) < 50) {
            return 2;
        }
        return 0;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 40% Magic as mana";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int manaRegain = this.hero.getStat(Stat.MAGIC) * 4 / 10;
        this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, manaRegain, this.hero);
    }

    @Override
    public String getName() {
        return "Recharge";
    }
}
