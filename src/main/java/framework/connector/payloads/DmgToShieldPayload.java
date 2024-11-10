package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;

public class DmgToShieldPayload extends ConnectionPayload {

    public int dmg;
    public Hero target;

    public DmgToShieldPayload setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public DmgToShieldPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }
}
