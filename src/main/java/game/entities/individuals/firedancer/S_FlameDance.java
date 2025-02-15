package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_FlameDance extends Skill {

    public S_FlameDance(Hero hero) {
        super(hero);
        this.iconPath = "/icons/flamedance.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.MOVE, SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.faithCost = 2;
        this.actionCost = 0;
    }



    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        this.hero.addEffect(new Burning(2), this.hero);
    }

    @Override
    public String getName() {
        return "Flame Dance";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Free Action. Move 1. Get 2 burn stack.";
    }

}
