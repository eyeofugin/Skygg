package game.entities.individuals.longsword;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.changeeffects.effects.Steadfast;


public class S_Steadfast extends Skill {

    public S_Steadfast(Hero hero) {
        super(hero);
        this.iconPath = "/icons/steadfast.png";
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
        return "Get 2 Steadfast stack, when get damage. start of turn lose a stack. Steadfast: 10% dmg reduction";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            this.hero.addEffect(new Steadfast(2), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Steadfast";
    }
}
