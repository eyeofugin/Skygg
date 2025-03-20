package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_FrozenShield extends Skill {

    public S_FrozenShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostshield.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SELF;
        this.manaCost = 6;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.shield( 5 + (this.hero.getStat(Stat.MANA) / 5), this.hero);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get 5 + 20% Max Mana Shield.";
    }


    @Override
    public String getName() {
        return "Frozen Shield";
    }
}
