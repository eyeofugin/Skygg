package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.*;

import java.util.List;
import java.util.Optional;

public class S_Praise extends Skill {

    public S_Praise(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/praise.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.primary = true;
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 1));
        this.faithGain = true;
        this.abilityType = AbilityType.PRIMARY;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        List<Hero> over50 = this.hero.team.getHeroesAsList().stream().filter(e->e.getCurrentLifePercentage() > 50).toList();
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2*over50.size(), this.hero);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 1 Faith. Gain 2 Faith for each ally over 50% life";
    }
    @Override
    public String getName() {
        return "Praise";
    }
}
