package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_DeepThrust extends Skill {

    public S_DeepThrust(Hero hero) {
        super(hero);
        this.iconPath = "/icons/deepthrust.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.NORMAL;
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_HALO, Stat.HALO, 1, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_HALO, Stat.HALO,  1, this.hero);
        }
        int success = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(success)) {
            target.addEffect(new Bleeding(1), this.hero);
        }
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "phys/normal, gain 1 Favor (2 with combo). Can Bleed";
    }


    @Override
    public String getName() {
        return "Deep Thrust";
    }
}
