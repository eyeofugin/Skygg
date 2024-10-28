package game.entities.individuals.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;

public class S_Lifesteal extends Skill {

    public S_Lifesteal(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/lifesteal.png";
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
        return "Heal for 20% of all dmg done";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.cast.hero)) {
            this.hero.heal(this.hero, pl.dmgDone * 20/100, this,false);
        }
    }

    @Override
    public String getName() {
        return "Lifesteal";
    }
}
