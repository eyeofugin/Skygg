package game.entities.individuals.angelguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Exalted;
import jdk.jfr.Percentage;

import java.util.List;

public class S_LightSpikes extends Skill {

    public S_LightSpikes(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightspikes.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ENEMY;
        this.dmg = 5;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.cdMax = 4;
        this.faithCost = 7;
        this.damageType = DamageType.MAGIC;
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 25) {
            return 2;
        }
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 1;
        }
        return 0;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for 10% of the damage done";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.equals(pl.cast)) {
            this.hero.heal(pl.cast.hero, pl.dmgDone*10/100, this, false);
        }
    }

    @Override
    public String getName() {
        return "Light Spikes";
    }
}
