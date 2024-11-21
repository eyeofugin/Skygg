package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_SpearOfLight extends Skill {

    public S_SpearOfLight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/spearoflight.png";
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
        this.dmg = 4;
        this.damageType = DamageType.MAGIC;
        this.primary = true;
        this.comboEnabled = true;
        this.faithGain = true;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH,  5);
        }
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "+3" +Stat.FAITH.getIconString() + ", if combo gain extra 5"+Stat.FAITH.getIconString()+".";
    }

    @Override
    public String getName() {
        return "Spear of Light";
    }
}
