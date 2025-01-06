package game.entities.individuals.eldritchguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;


public class S_UnnaturalDefenses extends Skill {

    public S_UnnaturalDefenses(Hero hero) {
        super(hero);
        this.iconPath = "/icons/unnaturaldefenses.png";
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
        return "+1 Endurance when receiving non-normal damage";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target) && !pl.damageType.equals(DamageType.NORMAL)) {
            this.hero.addToStat(Stat.ENDURANCE, 1);
        }
    }

    @Override
    public String getName() {
        return "Unnatural Defenses";
    }
}
