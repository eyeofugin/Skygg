package game.entities.individuals.longsword;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class S_SupremeDefense extends Skill {

    public S_SupremeDefense(Hero hero) {
        super(hero);
        this.iconPath = "/icons/supremedefense.png";
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
        return "When dealt less than 10% max health dmg, gain 1 Action";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            if (pl.cast != null) {
                int tenPercent = this.hero.getStat(Stat.LIFE) * 10 / 100;
                if (pl.dmgDone < tenPercent) {
                    this.hero.addToStat(Stat.CURRENT_ACTION, 1);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Supreme Defense";
    }
}
