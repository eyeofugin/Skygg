package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Barrage extends Skill {

    public S_Barrage(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/barrage.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.15));
        this.effects = List.of(new Injured(1));
        this.targetType = TargetType.ENEMY_LINE;
        this.dmg = 10;
        this.distance = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.cdMax = 4;
        this.abilityType = AbilityType.ULT;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Hits enemies on line, injures";
    }

    @Override
    public String getName() {
        return "Barrage";
    }
}
