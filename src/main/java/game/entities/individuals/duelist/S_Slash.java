package game.entities.individuals.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.SwiftStrikeCounter;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "/icons/slash.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2), new Multiplier(Stat.SPEED, 0.5));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
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
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Injured(1), this.hero);
        } else {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Combo: Injure. Else gain combo.";
    }


    @Override
    public String getName() {
        return "Slash";
    }
}
