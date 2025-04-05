package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_Challenge extends Skill {

    public S_Challenge(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/challenge.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
        this.cdMax = 3;
        this.casterEffects = List.of(new Threatening(1));
        this.tags = List.of(SkillTag.TACTICAL);
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
    }
    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move. Threaten";
    }

    @Override
    public String getName() {
        return "Challenge";
    }
}
