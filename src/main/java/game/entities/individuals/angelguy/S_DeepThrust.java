package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_DeepThrust extends Skill {

    public S_DeepThrust(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/deepthrust.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.damageMode = DamageMode.PHYSICAL;
        this.dmg = 8;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.4));
        this.abilityType = AbilityType.PRIMARY;
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
    public String getDescriptionFor(Hero hero) {
        return "phys/normal, gain 1 Halo (2 with combo). Can Bleed";
    }


    @Override
    public String getName() {
        return "Deep Thrust";
    }
}
