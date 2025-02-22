package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_FrozenShield extends Skill {

    public S_FrozenShield(Hero hero) {
        super(hero);
        this.iconPath = "/icons/frozenshield.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SELF;
        this.manaCost = 6;
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
