package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_BellowingRoar extends Skill {

    public S_BellowingRoar(Hero hero) {
        super(hero);
        this.iconPath = "/icons/bellowingroar.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.manaCost = 12;
        this.ultimate = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addEffect(new Threatening(2), this.hero);
        this.hero.heal(this.hero, this.hero.getStat(Stat.LIFE), null, false);
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 3;
        }
        return 0;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain Threatening. Heal for 100% max life";
    }

    @Override
    public String getName() {
        return "Bellowing Roar";
    }
}
