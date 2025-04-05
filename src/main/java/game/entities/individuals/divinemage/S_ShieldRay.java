package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_ShieldRay extends Skill {

    public S_ShieldRay(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/shieldray.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1};
        this.possibleTargetPositions = new int[]{2,3};
        this.shieldMultipliers = List.of(new Multiplier(Stat.CURRENT_FAITH, 0.5));
        this.faithCost = 3;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Shield Ray";
    }
}
