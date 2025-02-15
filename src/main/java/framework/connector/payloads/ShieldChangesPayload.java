package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;

public class ShieldChangesPayload extends ConnectionPayload {
    public int shield;
    public Hero target;
    public Hero source;

    public ShieldChangesPayload setShield(int shield) {
        this.shield = shield;
        return this;
    }

    public ShieldChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public ShieldChangesPayload setSource(Hero source) {
        this.source = source;
        return this;
    }
}
