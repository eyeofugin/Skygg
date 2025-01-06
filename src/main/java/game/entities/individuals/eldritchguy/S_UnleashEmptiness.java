package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Blight;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_UnleashEmptiness extends Skill {

    public S_UnleashEmptiness(Hero hero) {
        super(hero);
        this.iconPath = "/icons/unleashemptiness.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.SELF;
        this.manaCost = 5;
        this.lifeCost = 5;
        this.ultimate = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.removeNegativeEffects();
        this.hero.addToStat(Stat.MAGIC, this.hero.getStat(Stat.ENDURANCE) / 2);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Remove all debuffs. +X Magic where X is 50% of your Endurance.";
    }


    @Override
    public String getName() {
        return "Unleash Emptiness";
    }
}
