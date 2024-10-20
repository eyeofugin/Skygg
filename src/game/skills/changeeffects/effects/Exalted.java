package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;

public class Exalted extends Effect {

    public Exalted(int turns) {
        this.turns = turns;
        this.name = "Exalted";
        this.stackable = false;
        this.description = "All skills have +1 Range.";
        this.type = ChangeEffectType.EFFECT;
    }

    @Override
    public Effect getNew() {
        return new Exalted(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, "castChange"));
    }

    public void castChange(CastChangePayload pl) {
        if (pl.skill != null && pl.skill.hero != null && pl.skill.hero.equals(this.hero)) {
            pl.skill.setDistance(pl.skill.getDistance() + 1);
        }
    }
}
