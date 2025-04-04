package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.Effect;
import game.skills.Stat;

import java.util.HashMap;

public class DarkSecrets extends Effect {

    public static String ICON_STRING = "DKS";
    public DarkSecrets() {
        this.name ="Dark Secrets";
        this.iconString = ICON_STRING;
        this.description = "100 Lethality";
        this.type = ChangeEffectType.BUFF;
        this.statBonus = new HashMap<>();
        this.statBonus.put(Stat.LETHALITY, 100);
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new DarkSecrets();
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.cast != null && this.hero.equals(pl.cast.hero)) {
            this.hero.removePermanentEffectOfClass(DarkSecrets.class);
        }
    }
}
