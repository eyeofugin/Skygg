package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_LightJavelin extends Skill {

    public S_LightJavelin(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/lightjavelin.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.possibleTargetPositions = new int[]{4,5,6};
        this.possibleCastPositions = new int[]{2,3};
        this.damageMode = DamageMode.PHYSICAL;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.abilityType = AbilityType.PRIMARY;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_HALO, Stat.HALO, 1, this.hero);
        target.addToStat(Stat.EVASION, -10);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "magical/light, gain 1 Favor, give -10 Evasion";
    }


    @Override
    public String getName() {
        return "Light Javelin";
    }
}
