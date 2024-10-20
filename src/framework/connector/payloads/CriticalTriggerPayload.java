package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class CriticalTriggerPayload  extends ConnectionPayload {
    public Hero target;
    public Skill cast;

    public CriticalTriggerPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public CriticalTriggerPayload setCast(Skill cast) {
        this.cast = cast;
        return this;
    }
}
