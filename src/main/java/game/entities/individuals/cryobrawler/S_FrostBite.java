package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_FrostBite extends Skill {

    public S_FrostBite(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/frostbite.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2),
                new Multiplier(Stat.MAGIC, 0.2));
        this.effects = List.of(new Injured(1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4};
        this.dmg = 9;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }


    @Override
    public String getName() {
        return "Frost Bite";
    }
}
