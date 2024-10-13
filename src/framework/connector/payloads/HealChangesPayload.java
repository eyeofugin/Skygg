package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class HealChangesPayload  extends ConnectionPayload {
    private Hero caster;
    private Hero target;
    private Skill skill;
    private int heal;

    public Hero getCaster() {
        return caster;
    }

    public HealChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public Hero getTarget() {
        return target;
    }

    public HealChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public Skill getSkill() {
        return skill;
    }

    public HealChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public int getHeal() {
        return heal;
    }

    public HealChangesPayload setHeal(int heal) {
        this.heal = heal;
        return this;
    }
}
