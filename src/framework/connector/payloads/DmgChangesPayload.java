package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class DmgChangesPayload  extends ConnectionPayload {
    private Hero caster;
    private Hero target;
    private Skill skill;
    private int dmg;
    private Stat dmgtype;
    private boolean simulate;

    public boolean isSimulate() {
        return simulate;
    }

    public DmgChangesPayload setSimulate(boolean simulate) {
        this.simulate = simulate;
        return this;
    }

    public Stat getDmgtype() {
        return dmgtype;
    }

    public DmgChangesPayload setDmgtype(Stat dmgtype) {
        this.dmgtype = dmgtype;
        return this;
    }

    public int getDmg() {
        return dmg;
    }

    public DmgChangesPayload setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public DmgChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public Hero getTarget() {
        return target;
    }

    public DmgChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public Hero getCaster() {
        return caster;
    }

    public DmgChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }
}
