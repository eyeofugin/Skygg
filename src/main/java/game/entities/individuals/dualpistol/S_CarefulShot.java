package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_CarefulShot extends Skill {

    public S_CarefulShot(Hero hero) {
        super(hero);
        this.iconPath = "/icons/carefulshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.primary = true;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class)> 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Bleeding(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Combo: Bleed.";
    }
    @Override
    public String getName() {
        return "Careful Shot";
    }
}
