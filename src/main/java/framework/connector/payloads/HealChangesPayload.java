package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class HealChangesPayload  extends ConnectionPayload {
    public Hero caster;
    public Hero target;
    public Skill skill;
    public int heal;
    public boolean regen;


    public HealChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }



    public HealChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }


    public HealChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }


    public HealChangesPayload setHeal(int heal) {
        this.heal = heal;
        return this;
    }

    public HealChangesPayload setRegen(boolean regen) {
        this.regen = regen;
        return this;
    }
}
