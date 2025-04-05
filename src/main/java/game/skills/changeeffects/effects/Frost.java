package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.skills.Effect;

public class Frost extends Effect {

    public static String ICON_STRING = "FRO";
    public Frost(int stacks) {
        this.name = "Frost";
        this.iconString = ICON_STRING;
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Damage adds another stack, then if there are 3 stacks, remove all stacks and stun.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public void turnLogic() {
        this.hero.effectDamage(this.stacks, this);
    }

    @Override
    public Effect getNew() {
        return new Frost(this.stacks);
    }


    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            this.addStack(1);
        }
    }

    @Override
    public void addStack(int amount) {
        this.stacks += amount;
        if (this.stacks >= 3) {
            this.stacks = 0;
            this.hero.arena.stun(this.hero);
        }
    }
}
