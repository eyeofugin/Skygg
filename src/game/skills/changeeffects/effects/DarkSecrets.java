package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.Effect;
import game.skills.Stat;

import java.util.HashMap;

public class DarkSecrets extends Effect {

    public DarkSecrets() {
        this.name ="Dark Secrets";
        this.description = "100 Lethality";
        this.type = ChangeEffectType.BUFF;
        this.statBonus = new HashMap<>();
        this.statBonus.put(Stat.LETHALITY, 100);
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
