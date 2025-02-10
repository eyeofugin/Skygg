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
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_Chop extends Skill {

    public S_Chop(Hero hero) {
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
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.3), new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 10;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int bleedingSuccess = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(bleedingSuccess)) {
            target.addEffect(new Bleeding(1), this.hero);
        }
        int burningSuccess = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(burningSuccess)) {
            target.addEffect(new Burning(1), this.hero);
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
        return "Chance to bleed and burn.";
    }

    @Override
    public String getName() {
        return "Chop";
    }
}
