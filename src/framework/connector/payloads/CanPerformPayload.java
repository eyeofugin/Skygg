package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class CanPerformPayload  extends ConnectionPayload {

    public Skill skill;

    public boolean success = true;

    public CanPerformPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
