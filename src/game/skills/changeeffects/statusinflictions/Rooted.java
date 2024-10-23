package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.Effect;
import game.skills.Skill;

public class Rooted extends Effect {

    public Rooted(int turns) {
        this.turns = turns;
        this.name = "Rooted";
        this.stackable = false;
        this.description = "Can't use movement skills.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    @Override
    public Rooted getNew() {
        return new Rooted(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAN_PERFORM, new Connection(this, CanPerformPayload.class, "canPerform"));
    }

    public void canPerform(CanPerformPayload canPerformPayload) {
        if (!canPerformPayload.success) {
            return;
        }
        Skill skill = canPerformPayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            if (skill.tags.contains(Skill.SkillTag.MOVE)) {
                canPerformPayload.success = false;
            }
        }
    }
}
