package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.individuals.firedancer.S_FlamingSwing;
import game.entities.individuals.firedancer.S_SingingBlades;
import game.entities.individuals.firedancer.S_Slash;
import game.skills.Effect;
import game.skills.Skill;

public class Gifted extends Effect {
    public Gifted() {
        this.turns = -1;
        this.name = "Gifted";
        this.stackable = false;
        this.description = "Primary skills count as two hits.";
        this.type = ChangeEffectType.BUFF;
    }

    @Override
    public Effect getNew() {
        return new Gifted();
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && skill.isPrimary()) {
            skill.setCountsAsHits(2);
        }
    }
}
