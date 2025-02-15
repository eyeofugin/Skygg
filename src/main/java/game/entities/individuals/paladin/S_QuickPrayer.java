package game.entities.individuals.paladin;

import game.entities.Hero;
import game.skills.Resource;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;
import java.util.Optional;

public class S_QuickPrayer extends Skill {

    public S_QuickPrayer(Hero hero) {
        super(hero);
        this.iconPath = "/icons/quickprayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 4));
        this.primary = true;
        this.faithGain = true;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 4 Faith.";
    }
    @Override
    public String getName() {
        return "Quick Prayer";
    }
}
