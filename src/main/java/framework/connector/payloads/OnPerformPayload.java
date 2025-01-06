package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.skills.Skill;

public class OnPerformPayload  extends ConnectionPayload {
    public Skill skill;
    public OnPerformPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
