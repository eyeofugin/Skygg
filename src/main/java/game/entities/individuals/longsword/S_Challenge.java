package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Threatening;
import game.skills.changeeffects.statusinflictions.Taunted;

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
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 3;
        this.casterEffects = List.of(new Threatening(1));
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        this.abilityType = AbilityType.TACTICAL;
    }
    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1. Threaten";
    }

    @Override
    public String getName() {
        return "Challenge";
    }
}
