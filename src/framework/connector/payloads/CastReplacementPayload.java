package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class CastReplacementPayload extends ConnectionPayload {
    Skill cast;

    public CastReplacementPayload setCast(Skill cast) {
        this.cast = cast;
        return this;
    }
}
