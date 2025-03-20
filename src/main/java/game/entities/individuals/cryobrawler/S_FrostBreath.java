package game.entities.individuals.cryobrawler;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Frost;

import java.util.List;

public class S_FrostBreath extends Skill {

    public S_FrostBreath(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.effects = List.of(new Frost(1));
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2),
                new Multiplier(Stat.MANA, 0.3));
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.dmg = 5;
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 4;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -1);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "-1 Speed. Give Frost Stack.";
    }

    @Override
    public String getName() {
        return "Frost Breath";
    }
}
