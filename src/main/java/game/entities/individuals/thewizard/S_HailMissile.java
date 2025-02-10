package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_HailMissile extends Skill {

    public S_HailMissile(Hero hero) {
        super(hero);
        this.iconPath = "/icons/hailmissile.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 4;
        this.damageType = DamageType.FROST;
        this.damageMode = DamageMode.MAGICAL;
        this.comboEnabled = true;
        this.canMiss = false;
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            target.addEffect(new Injured(1), this.hero);
            this.hero.removePermanentEffectOfClass(Combo.class);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Never misses. Combo: Injure target";
    }


    @Override
    public String getName() {
        return "Hail Missile";
    }
}
