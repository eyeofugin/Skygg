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
    public Scoped(int turns) {
        this.turns = turns;
        this.name = "Scoped";
        this.stackable = false;
        this.description = "+1 Range";
        this.type = ChangeEffectType.BUFF;
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
        if (skill != null && skill.hero.equals(this.hero) && skill.isPrimary()) {

            if (Arrays.stream(skill.possibleCastPositions).anyMatch(i -> i == 1)) {
                return;
            }
            int[] newCastPositions = Arrays.copyOf(skill.possibleCastPositions, skill.possibleCastPositions.length+1);
            newCastPositions[newCastPositions.length-1] = 1;
            Arrays.sort(newCastPositions);
            skill.possibleCastPositions = newCastPositions;
        }
    }
}
