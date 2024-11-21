package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.BlastingCounter;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Blasting extends Skill {

    public S_Blasting(Hero hero) {
        super(hero);
        this.iconPath = "/icons/blasting.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 5;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FINESSE, 0.3));
        this.damageType = DamageType.NORMAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(BlastingCounter.class) == 2) {
            this.hero.removePermanentEffectOfClass(BlastingCounter.class);
            this.hero.addEffect(new Combo(), this.hero);
        } else {
            this.hero.addEffect(new BlastingCounter(1), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.hasPermanentEffect(BlastingCounter.class) == 2) {
            return 2;
        }
        return 0;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain combo every third attack";
    }


    @Override
    public String getName() {
        return "Blasting";
    }
}
