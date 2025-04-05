package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Exalted;

import java.util.List;

public class S_HolyShield extends Skill {

    public S_HolyShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/holyshield.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.cdMax = 1;
        this.faithCost = 4;
        this.shield = 3;
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }


    @Override
    public String getName() {
        return "Holy Shield";
    }
}
