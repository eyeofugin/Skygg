package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import utils.MyMaths;

import java.util.List;

public class S_SingingBlades extends Skill {

    public S_SingingBlades(Hero hero) {
        super(hero);
        this.iconPath = "entities/firedancer/icons/singingblades.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.15),
                new Multiplier(Stat.POWER, 0.4));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 10;
        this.damageMode = DamageMode.PHYSICAL;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Burns. If target has less than half their life, gain combo.";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Burning(2), this.hero);
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 50) {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Singing Blades";
    }

}
