package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

public class S_HealingGrenade extends Skill {

    public S_HealingGrenade(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/healinggrenade.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ALLY;
        this.cdMax = 3;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public int getAIRating(Hero target) {
        return 1 + target.getMissingLifePercentage() / 50;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int missingLife = target.getStat(Stat.LIFE) - target.getStat(Stat.CURRENT_LIFE);
        target.heal(this.hero, missingLife / 3, this, false);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heals all allies for 1/3 missing life";
    }


    @Override
    public String getName() {
        return "Healing Grenade";
    }
}
