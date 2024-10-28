package game.entities.individuals.sniper;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class S_Cloaked extends Skill {

    private boolean broken = false;

    public S_Cloaked(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/cloaked.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
        this.hero.addToStat(Stat.EVASION, 50);
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
        return "50 Evasion until getting damaged";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (!broken && this.hero.equals(pl.target)) {
            this.broken = true;
            this.hero.addToStat(Stat.EVASION,-1*50);
        }
    }

    @Override
    public String getName() {
        return "Cloaked";
    }
}
