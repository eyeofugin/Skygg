package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Effect;
import game.skills.Skill;

public class DmgTriggerPayload  extends ConnectionPayload {
    public Hero target;
    public Skill cast;
    public int dmgDone;
    public Effect effect;
    public DamageMode damageMode;
    public DamageType damageType;

    public DmgTriggerPayload setDmgDone(int dmgDone) {
        this.dmgDone = dmgDone;
        return this;
    }

    public DmgTriggerPayload setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }

    public DmgTriggerPayload setCast(Skill cast) {
        this.cast = cast;
        return this;
    }

    public DmgTriggerPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public DmgTriggerPayload setDamageType(DamageType damageType) {
        this.damageType = damageType;
        return this;
    }

    public DmgTriggerPayload setDamageMode(DamageMode damageMode) {
        this.damageMode = damageMode;
        return this;
    }
}
