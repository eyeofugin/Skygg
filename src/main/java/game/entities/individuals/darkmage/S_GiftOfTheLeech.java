package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.LifeSteal;

import java.util.ArrayList;
import java.util.List;

public class S_GiftOfTheLeech extends Skill {

    public S_GiftOfTheLeech(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/giftoftheleech.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new LifeSteal(1));
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2,3};
    }


    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getName() {
        return "Gift of the leech";
    }
}
