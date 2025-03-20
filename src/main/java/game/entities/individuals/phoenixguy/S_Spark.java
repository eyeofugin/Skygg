package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Spark extends Skill {

    public S_Spark(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/spark.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 12;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
        this.faithGain = true;
        this.comboEnabled = true;
        this.abilityType = AbilityType.PRIMARY;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 1, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Spark";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "+3 Favor. Combo: +1 Favor.";
    }

}
