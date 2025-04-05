package game.entities.individuals.paladin;

import game.entities.Hero;
import game.skills.*;

import java.util.List;
import java.util.Optional;

public class S_QuickPrayer extends Skill {

    public S_QuickPrayer(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/quickprayer.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 4));
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
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
