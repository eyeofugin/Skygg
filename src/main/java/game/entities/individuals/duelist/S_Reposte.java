package game.entities.individuals.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Reposte extends Skill {

    public S_Reposte(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/reposte.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 8;
        this.casterEffects = List.of(new Cover(1));
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain Cover(1)";
    }


    @Override
    public String getName() {
        return "Reposte";
    }
}
