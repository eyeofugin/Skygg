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
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_FrostBite extends Skill {

    public S_FrostBite(Hero hero) {
        super(hero);
        this.iconPath = "/icons/frostbite.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2),
                new Multiplier(Stat.MAGIC, 0.2));
        this.effects = List.of(new Injured(1));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 9;
        this.damageType = DamageType.FROST;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Injures.";
    }


    @Override
    public String getName() {
        return "Frost Bite";
    }
}
