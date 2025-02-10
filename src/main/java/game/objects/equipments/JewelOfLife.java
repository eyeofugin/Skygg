package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.objects.Equipment;

public class JewelOfLife extends Equipment {

    public JewelOfLife() {
        super("jeweloflife", "Jewel of Life");
        this.statBonus = this.loadStatBonus();
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
}
