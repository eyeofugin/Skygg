package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Hotwings extends Skill {

    public S_Hotwings(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/hotwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(1));
        this.distance = 2;
        this.dmg = 4;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
        this.faithGain = true;
        this.abilityType = AbilityType.PRIMARY;
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



    @Override
    public String getDescriptionFor(Hero hero) {
        return "+1 Favor. Burns.";
    }

}
