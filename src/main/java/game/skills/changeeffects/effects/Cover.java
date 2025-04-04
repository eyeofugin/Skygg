package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.skills.Effect;

public class Cover extends Effect {

    public static String ICON_STRING = "COV";
    public Cover(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Cover";
        this.description = "Gain 20% dmg reduction";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new Cover(this.turns);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            int reduction = pl.dmg*20/100;
            reduction = Math.max(reduction,0);
            pl.dmg-=reduction;
        }
    }
}
