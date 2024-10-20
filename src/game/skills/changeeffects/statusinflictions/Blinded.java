package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;
import game.skills.Skill;

public class Blinded extends Effect {

    public Blinded(int turns) {
        this.turns = turns;
        this.name = "Blinded";
        this.stackable = false;
        this.description = "All skills have -1 range.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public Effect getNew() {
        return new Blinded(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, "castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            skill.setDistance(skill.getDistance() - 1);
        }
    }
}