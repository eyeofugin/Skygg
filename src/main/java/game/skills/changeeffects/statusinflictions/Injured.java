package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.HealChangesPayload;
import game.skills.Effect;

public class Injured extends Effect {

    public static String ICON_STRING = "INJ";
    public Injured(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Injured";
        this.stackable = false;
        this.description = "You dont heal at the end of your turn.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Injured getNew() {
        return new Injured(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EOT_HEAL_CHANGES, new Connection(this, HealChangesPayload.class, "healChanges"));
    }

    public void healChanges(HealChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            pl.heal *= 0;
        }
    }
}
