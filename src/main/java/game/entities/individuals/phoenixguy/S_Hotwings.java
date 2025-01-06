package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Hotwings extends Skill {

    public S_Hotwings(Hero hero) {
        super(hero);
        this.iconPath = "/icons/hotwings.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(1));
        this.distance = 2;
        this.dmg = 4;
        this.damageType =  DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
        this.faithGain = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 1, this.hero);
    }

    @Override
    public String getName() {
        return "Hot Wings";
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "+1 Favor. Burns.";
    }

}
