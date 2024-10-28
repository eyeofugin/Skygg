package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.HealChangesPayload;
import game.skills.Effect;

public class RegenStop extends Effect {

    public RegenStop(int turns) {
        this.turns = turns;
        this.name = "Regen Stop";
        this.stackable = false;
        this.description = "Disables life regen.";
        this.type = ChangeEffectType.DEBUFF;
    }

    @Override
    public Effect getNew() {
        return new RegenStop(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
    }
    public void healChanges(HealChangesPayload pl) {
        if (this.hero.equals(pl.target) && pl.regen) {
            pl.heal *= 0;
        }
    }
}
