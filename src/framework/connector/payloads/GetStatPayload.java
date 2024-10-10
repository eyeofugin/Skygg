package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Stat;

public class GetStatPayload  extends ConnectionPayload {
    Stat stat;
    int value;
    Hero hero;

    public GetStatPayload setStat(Stat stat) {
        this.stat = stat;
        return this;
    }

    public GetStatPayload setValue(int value) {
        this.value = value;
        return this;
    }

    public GetStatPayload setHero(Hero hero) {
        this.hero = hero;
        return this;
    }
}
