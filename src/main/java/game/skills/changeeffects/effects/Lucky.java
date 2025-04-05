package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.Effect;
import game.skills.Stat;

import java.util.HashMap;

public class Lucky extends Effect {

    public static String ICON_STRING = "LUK";

    public Lucky(int turns) {
        this.name ="Lucky";
        this.iconString = ICON_STRING;
        this.turns = turns;
        this.description = "100 Crit Chance";
        this.type = ChangeEffectType.BUFF;
        this.statBonus = new HashMap<>();
        this.statBonus.put(Stat.CRIT_CHANCE, 100);
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Lucky(this.turns);
    }
}
