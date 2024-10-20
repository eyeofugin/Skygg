package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class BaseDmgChangesPayload extends ConnectionPayload {
    public int dmg;
    public Hero caster;
    public Hero target;
    public Skill skill;

    public BaseDmgChangesPayload setDmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public BaseDmgChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public BaseDmgChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public BaseDmgChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
