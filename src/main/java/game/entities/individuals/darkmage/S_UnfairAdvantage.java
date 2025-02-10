package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.LifeSteal;

import java.util.List;

public class S_UnfairAdvantage extends Skill {

    public S_UnfairAdvantage(Hero hero) {
        super(hero);
        this.iconPath = "/icons/unfairadvantage.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 2;
        this.manaCost = 3;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magicAdd = target.getStat(Stat.MAGIC) / 10;
        int speedAdd = target.getStat(Stat.SPEED) / 10;
        target.addToStat(Stat.MAGIC, magicAdd);
        target.addToStat(Stat.SPEED, speedAdd);
        target.addEffect(new Combo(), this.hero);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "+10% Magic. +10% Speed. + Combo";
    }

    @Override
    public String getName() {
        return "Unfair Advantage";
    }
}
