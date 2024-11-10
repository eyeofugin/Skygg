package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_FlameDance extends Skill {

    public S_FlameDance(Hero hero) {
        super(hero);
        this.iconPath = "/icons/flamedance.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.MOVE, SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.faithCost = 5;
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
    public String getName() {
        return "Flame Dance";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "No action cost; Move 1; +1"+ Stat.FINESSE.getIconString();
    }

}
