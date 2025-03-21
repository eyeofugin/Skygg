package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_AllOut extends Skill {

    public S_AllOut(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/allout.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.cdMax = 5;
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.ENDURANCE, -3);
        this.hero.addToStat(Stat.POWER, 5);
        this.hero.addToStat(Stat.SPEED, this.hero.getStat(Stat.SPEED)/5);
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 5;
        rating -= this.hero.getMissingLifePercentage() / 20;
        return rating;
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "5"+Stat.POWER.getIconString()+", +20%"+Stat.SPEED.getIconString()+", -3"+Stat.ENDURANCE.getIconString();
    }

    @Override
    public String getName() {
        return "All Out";
    }
}
