package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class DefensiveStance extends Effect {
    public DefensiveStance() {
        this.name = "Defensive Stance";
        this.stackable = false;
        this.intensity = 30;
        this.description = "Receive " + intensity + " percent less damage.";
        this.type = ChangeEffectType.EFFECT;
    }

    @Override
    public DefensiveStance getNew() {
        return new DefensiveStance();
    }

    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill skill, int result, Stat damageType, boolean simulated) {
        if (target == this.entity) {
            Logger.logLn(this.entity.name + ".DefensiveStance.getDamageChanges");
            result *= (double) (100 - this.intensity) /100;
        }
        return result;
    }
}
