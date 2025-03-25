package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class S_AetherStep extends Skill {

    public S_AetherStep(Hero hero) {
        super(hero);
        this.iconPath = "entities/thewizard/icons/aetherstep.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.MOVE);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2};
        this.manaCost = 5;
        this.actionCost = 0;
        this.abilityType = AbilityType.TACTICAL;
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
    public String getName() {
        return "Aether Step";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Free Action. Move 1";
    }

}
