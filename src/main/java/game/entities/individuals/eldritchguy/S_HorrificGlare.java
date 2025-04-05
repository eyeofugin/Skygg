package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_HorrificGlare extends Skill {

    public S_HorrificGlare(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/horrificglare.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5,6};
        this.effects = List.of(new Dazed(1));
        this.manaCost = 3;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ENDURANCE, -1 * target.getStat(Stat.ENDURANCE)/5);
    }

    @Override
    public int getAIRating(Hero target) {
        return 2;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "-20% Endurance, Daze(1).";
    }

    @Override
    public String getName() {
        return "Horrific Glare";
    }
}
