package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;

public class ShieldBrokenPayload extends ConnectionPayload {
    public Hero target;

    public ShieldBrokenPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }
}
