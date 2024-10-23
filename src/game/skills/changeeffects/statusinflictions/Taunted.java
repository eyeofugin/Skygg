package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import game.skills.Effect;
import game.skills.Skill;

public class Taunted extends Effect {
    public Taunted(int turns) {
        this.turns = turns;
        this.name = "Taunted";
        this.stackable = false;
        this.description = "Can only use skills that to dmg.";
        this.type = ChangeEffectType.STATUS_INFLICTION;

    }

    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAN_PERFORM, new Connection(this, CanPerformPayload.class, "performCheck"));
    }

    @Override
    public Taunted getNew() {
        return new Taunted(this.turns);
    }

    public void performCheck(CanPerformPayload payload) {
        if (payload.skill.hero.equals(this.hero) && payload.success) {
            if (!payload.skill.tags.contains(Skill.SkillTag.DMG)) {
                payload.success = false;
            }
        }
    }
}
