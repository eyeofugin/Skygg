package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_AxeSwing extends Skill {

    public S_AxeSwing(Hero hero) {
        super(hero);
        this.iconPath = "/icons/axeswing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(AxeSwingCounter.class) == 2) {
            this.hero.removePermanentEffectOfClass(AxeSwingCounter.class);
            target.addEffect(new Bleeding(3), this.hero);
        } else {
            this.hero.addEffect(new AxeSwingCounter(1), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.hasPermanentEffect(AxeSwingCounter.class) == 2) {
            return 2;
        }
        return 0;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Every third attack bleeds target";
    }

    @Override
    public String getName() {
        return "Axe Swing";
    }
}
