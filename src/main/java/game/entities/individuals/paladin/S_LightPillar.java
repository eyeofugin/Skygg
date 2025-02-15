package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Disenchanted;

import java.util.ArrayList;
import java.util.List;

public class S_LightPillar extends Skill {

    public S_LightPillar(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightpillar.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.CURRENT_FAITH, 0.6));
        this.effects = List.of(new Disenchanted(1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 2;
        this.faithCost = 2;
        this.damageType = DamageType.LIGHT;
        this.damageMode = DamageMode.MAGICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        return 3;
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Disenchants.";
    }

    @Override
    public String getName() {
        return "Light Pillar";
    }
}
