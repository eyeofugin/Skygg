package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.objects.Equipment;
import game.skills.DamageMode;
import game.skills.changeeffects.effects.Burning;

public class FlamingSword extends Equipment {
    public FlamingSword() {
        super("flamingsword", "Flaming Sword");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.active && pl.cast != null && pl.cast.hero.equals(this.hero) && pl.damageMode.equals(DamageMode.PHYSICAL)) {
            pl.target.addEffect(new Burning(1), this.hero);
        }
    }
}
