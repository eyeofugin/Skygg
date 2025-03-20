package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.*;
import game.objects.Equipment;
import game.skills.changeeffects.effects.Burning;

public class RedOrb extends Equipment {

    public RedOrb() {
        super("redorb", "Red Orb");
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "start"));
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void start(StartOfMatchPayload pl) {
        if (this.active && this.hero != null) {
            this.hero.addEffect(new Burning(1), this.hero);
        }
    }
    public void castChange(CastChangePayload pl) {
        if (this.active && pl.skill.hero != null && pl.skill.hero.equals(this.hero)) {
            pl.skill.setDistance(pl.skill.getDistance() + 1);
        }
    }
}
