package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.individuals.rifle.S_Barrage;
import game.entities.individuals.rifle.S_Blasting;
import game.skills.Effect;
import game.skills.Skill;

public class Scoped extends Effect {
    public Scoped() {
        this.turns = -1;
        this.name = "Scoped";
        this.stackable = false;
        this.description = "This hero uses the scoped mode.";
        this.type = ChangeEffectType.BUFF;
    }

    @Override
    public Effect getNew() {
        return new Scoped();
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            if (skill instanceof S_Barrage || skill instanceof S_Blasting) {
                skill.setDistance(skill.getDistance() + 1);
            }
        }
    }
}
