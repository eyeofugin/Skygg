package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class Adaptability extends Effect {
    public Adaptability() {
        this.name = "Adaptability";
        this.stackable = true;
        this.intensity = 5;
        this.description = intensity + " percent damage mitigation per stack.";
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Adaptability getNew() {
        return new Adaptability();
    }
    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill skill, int result, Stat damageType, boolean simulated) {
        if (target == this.entity) {
            Logger.logLn(this.entity.name + ".Adaptability.getDamageChanges");
            result *= (double) (100 - this.intensity * stacks) /100;
        }
        return result;
    }
}
