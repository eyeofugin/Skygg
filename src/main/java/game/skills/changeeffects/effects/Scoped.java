package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.individuals.rifle.S_Barrage;
import game.entities.individuals.rifle.S_Mobilize;
import game.entities.individuals.rifle.S_PiercingBolt;
import game.skills.Effect;
import game.skills.Skill;

import java.util.Arrays;

public class Scoped extends Effect {

    public static String ICON_STRING = "SCO";
    public Scoped(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Scoped";
        this.stackable = false;
        this.description = "+20 Accuracy";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Scoped(this.turns);
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero)) {
            skill.setAccuracy(skill.getAccuracy() + 20);
        }
    }
}
