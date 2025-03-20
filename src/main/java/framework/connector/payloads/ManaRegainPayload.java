package framework.connector.payloads;

import framework.connector.ConnectionPayload;

public class ManaRegainPayload extends ConnectionPayload {
    public int amount;

    public ManaRegainPayload setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
