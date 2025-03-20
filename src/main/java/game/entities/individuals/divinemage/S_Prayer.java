package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class S_Prayer extends Skill {

    public S_Prayer(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/prayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 3));
        this.primary = true;
        this.faithGain = true;
        this.abilityType = AbilityType.PRIMARY;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Optional<Hero> heroUnder50 = this.hero.team.getHeroesAsList().stream().filter(e->e.getCurrentLifePercentage() < 50).findAny();
        if (heroUnder50.isPresent()) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 3 Faith. +3 if an ally is under 50% life";
    }
    @Override
    public String getName() {
        return "Prayer";
    }
}
