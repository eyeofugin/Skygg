package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_CarefulShot extends Skill {

    public S_CarefulShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/carefulshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{6,7};
        this.primary = true;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.abilityType = AbilityType.PRIMARY;
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
