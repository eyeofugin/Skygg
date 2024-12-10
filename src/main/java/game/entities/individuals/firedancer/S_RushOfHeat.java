package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_RushOfHeat extends Skill {

    public S_RushOfHeat(Hero hero) {
        super(hero);
        this.iconPath = "/icons/rushofheat.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.RESTOCK);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Burning(3));
        this.cdMax = 3;
        this.faithGain = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        return -1* this.hero.getMissingLifePercentage() / 25;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get 3 burn. +15"+Stat.FAITH.getIconString();
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 15);
    }

    @Override
    public String getName() {
        return "Rush of Heat";
    }
}
