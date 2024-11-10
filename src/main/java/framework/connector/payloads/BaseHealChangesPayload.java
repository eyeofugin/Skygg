package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.skills.Skill;

public class BaseHealChangesPayload  extends ConnectionPayload {
    public int heal;
    public Hero caster;
    public Hero target;
    public Skill skill;

    public BaseHealChangesPayload setHeal(int heal) {
        this.heal = heal;
        return this;
    }

    public BaseHealChangesPayload setCaster(Hero caster) {
        this.caster = caster;
        return this;
    }

    public BaseHealChangesPayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public BaseHealChangesPayload setSkill(Skill skill) {
        this.skill = skill;
        return this;
    }
}
