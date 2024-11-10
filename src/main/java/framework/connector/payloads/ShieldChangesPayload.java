package framework.connector.payloads;

import framework.connector.ConnectionPayload;

public class ShieldChangesPayload extends ConnectionPayload {
    public int shield;

    public ShieldChangesPayload setShield(int shield) {
        this.shield = shield;
        return this;
    }
}
