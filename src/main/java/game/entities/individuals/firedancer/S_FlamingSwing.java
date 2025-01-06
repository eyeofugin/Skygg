package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_FlamingSwing extends Skill {

    public S_FlamingSwing(Hero hero) {
        super(hero);
        this.iconPath = "/icons/flamingswing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.primary = true;
        this.faithGain = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Burning(1), this.hero);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class)> 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 1, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Flaming Swing";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Burns. +2 Favor per hit. Combo: +1.";
    }

}
