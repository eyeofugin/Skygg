package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class Shielded extends Effect {
    public Shielded() {
        this.name = "Shielded";
        this.description = "Prevent the next damage.";
        this.stackable = false;
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Shielded getNew() {
        return new Shielded();
    }
    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        if (target == this.entity) {
            Logger.logLn(this.entity.name + ".Shielded.getDamageChanges");
            result = 0;
            if (!simulated) {
                this.turns = 0;
            }
        }
        return result;
    }
}
