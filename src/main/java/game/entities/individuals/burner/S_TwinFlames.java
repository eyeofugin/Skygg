package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_TwinFlames extends Skill {

    public S_TwinFlames(Hero hero) {
        super(hero);
        this.iconPath = "entities/burner/icons/twinflames.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.4));
        this.effects = List.of(new Burning(1));
        this.targetType = TargetType.TWO_RDM;
        this.dmg = 2;
        this.damageMode = DamageMode.MAGICAL;
        this.faithCost = 2;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Burns";
    }


    @Override
    public String getName() {
        return "Twin Flames";
    }
}
