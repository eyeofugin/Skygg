package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import framework.connector.payloads.OnPerformPayload;
import game.entities.Hero;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;


public class S_BlueLife extends Skill {

    public S_BlueLife(Hero hero) {
        super(hero);
        this.iconPath = "/icons/bluelife.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.passive = true;
    }

    @Override
    protected void initAnimation() {
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 50% of mana you spend as life";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.ON_PERFORM, new Connection(this, OnPerformPayload.class, "onPerform"));
    }

    public void onPerform(OnPerformPayload pl) {
        if (this.hero.equals(pl.skill.hero) && pl.skill.getManaCost() > 0) {
            this.hero.addResource(Stat.CURRENT_LIFE, Stat.LIFE, pl.skill.getManaCost() / 2, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Blue Life";
    }
}
