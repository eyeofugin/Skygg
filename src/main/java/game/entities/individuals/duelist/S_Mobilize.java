package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Mobilize extends Skill {

    public S_Mobilize(Hero hero) {
        super(hero);
        this.iconPath = "/icons/mobilize.png";
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
        this.cdMax = 3;
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.addToStat(Stat.POWER, 1);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1. Combo: +1 Power";
    }

    @Override
    public String getName() {
        return "Mobilize";
    }
}
