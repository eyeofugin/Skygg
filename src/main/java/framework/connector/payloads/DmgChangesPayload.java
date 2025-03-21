package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.Skill;

public class DmgChangesPayload  extends ConnectionPayload {
    public Hero caster;
    public Hero target;
    public Skill skill;
    public int dmg;
    public DamageMode dmgMode;
    public boolean simulate;

    public DmgChangesPayload setSimulate(boolean simulate) {
        this.simulate = simulate;
        return this;
    }

    public DmgChangesPayload setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public DmgChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public DmgChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public DmgChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public DmgChangesPayload setDmgMode(DamageMode mode){
        this.dmgMode = mode;
        return this;
    }
}
