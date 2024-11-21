package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class S_AetherStep extends Skill {

    public S_AetherStep(Hero hero) {
        super(hero);
        this.iconPath = "/icons/aetherstep.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.MOVE);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 0;
        this.manaCost = 10;
        this.actionCost = 0;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
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
        return "No action cost. Move 1";
    }

}
