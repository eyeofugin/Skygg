package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.Effect;
import game.skills.Stat;

public class Blight extends Effect {

    public Blight(int stacks) {
        this.name = "Blight";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "When receiving dmg, remove blight stacks and get 3 dmg per stack.";
        this.type = ChangeEffectType.DEBUFF;
    }
    @Override
    public Effect getNew() {
        return new Blight(this.stacks);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            this.hero.addResource(Stat.CURRENT_LIFE, Stat.LIFE, -3*stacks, pl.cast.hero);
            this.hero.removePermanentEffectOfClass(Blight.class);
        }
    }
}
