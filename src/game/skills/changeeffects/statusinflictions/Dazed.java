package game.skills.changeeffects.statusinflictions;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.skills.Effect;
import game.skills.Skill;

public class Dazed extends Effect {

    public Dazed(int turns) {
        this.turns = turns;
        this.name = "Dazed";
        this.stackable = false;
        this.description = "CD for skills is +1.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public Effect getNew() {
        return new Dazed(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, "castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            skill.setCdMax(skill.getCdMax() + 1);
        }
    }
}
