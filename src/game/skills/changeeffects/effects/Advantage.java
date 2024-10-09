package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class Advantage extends Effect {
    public Advantage() {
        this.name = "Advantage";
        this.stackable = false;
        this.intensity = 10;
        this.description = "Does " + intensity + " percent more damage.";
        this.type = ChangeEffectType.EFFECT;
    }

    @Override
    public Advantage getNew() {
        return new Advantage(this.turns);
    }

    public Advantage(int turns) {
        this();
        this.turns = turns;
    }

    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill skill, int result, Stat damageType, boolean simulated) {
        if (caster == this.entity) {
            Logger.logLn(this.entity.name + ".Advantage.getDamageChanges");
            result *= (double) (100 + this.intensity) /100;
        }
        return result;
    }
}