package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_Headsmash extends Skill {

    public S_Headsmash(Hero hero) {
        super(hero);
        this.iconPath = "/icons/headsmash.png";
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
        this.distance = 2;
        this.dmg = 1;
        this.cdMax = 3;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.arena.stun(target);
        } else {
            this.hero.addEffect(new Dazed(2), this.hero);
            this.hero.addEffect(new Combo(), this.hero);
        }
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "combo: stun target; no combo: daze self and get combo";
    }


    @Override
    public String getName() {
        return "Headsmash";
    }
}