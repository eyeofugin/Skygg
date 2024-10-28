package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;

public class DeathTriggerPayload extends ConnectionPayload {
    public Hero dead;

    public DeathTriggerPayload setDead(Hero dead) {
        this.dead = dead;
        return this;
    }
}
