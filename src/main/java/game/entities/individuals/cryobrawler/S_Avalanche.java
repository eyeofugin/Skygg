package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Frost;

import java.util.List;

public class S_Avalanche extends Skill {

    public S_Avalanche(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/avalanche.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5,6,7};
        this.dmg = 10;
        this.effects = List.of(new Frost(1));
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 6;
        this.ultimate = true;
        this.abilityType = AbilityType.ULT;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Frost.";
    }


    @Override
    public String getName() {
        return "Avalanche";
    }
}
