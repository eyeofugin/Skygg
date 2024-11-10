package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class CastChangePayload extends ConnectionPayload {
    public Skill skill;

    public CastChangePayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
