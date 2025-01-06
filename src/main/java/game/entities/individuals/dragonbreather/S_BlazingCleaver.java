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
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_BlazingCleaver extends Skill {

    public S_BlazingCleaver(Hero hero) {
        super(hero);
        this.iconPath = "/icons/blazingcleaver.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1), new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 2;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Burning(1), this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            int burnStacks = target.getPermanentEffectStacks(Burning.class);
            target.effectDamage(burnStacks, new Burning(0));
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.hasPermanentEffect(AxeSwingCounter.class) == 1) {
            return 2;
        }
        return 0;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Burns. Combo: Activates target burn stacks";
    }

    @Override
    public String getName() {
        return "Chop";
    }
}
