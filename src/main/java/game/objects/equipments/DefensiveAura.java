package game.objects.equipments;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.objects.Equipment;
import game.skills.DamageMode;

public class DefensiveAura extends Equipment {

    public DefensiveAura() {
        super("defensiveaura", "Defensive Aura");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public String getDescription() {
        return "+4 on each Defense Stat. Reduce magical damage by 10%.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (this.active && pl.target != null && pl.target.equals(this.hero)) {
            if (pl.dmgMode != null && pl.dmgMode.equals(DamageMode.MAGICAL)) {
                pl.dmg = (int)(0.9*pl.dmg);
            }
        }
    }
}
