package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.Effect;
import game.skills.Skill;

public class Rooted extends Effect {

    public Rooted(int turns) {
        this.turns = turns;
        this.name = "Rooted";
        this.stackable = false;
        this.description = "Can't use movement skills.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    @Override
    public Rooted getNew() {
        return new Rooted(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
