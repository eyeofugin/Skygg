package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class Disadvantage extends Effect {
    private static final int INTENSITY = 10;
    public Disadvantage() {
        this.name = "Disadvantage";
        this.stackable = false;
        this.intensity = INTENSITY;
        this.description = "Receive " + intensity + " percent more damage.";
        this.type = ChangeEffectType.EFFECT;
    }

    public Disadvantage(int turns) {
        this();
        this.turns = turns;
    }
    @Override
    public Disadvantage getNew() {
        return new Disadvantage(this.turns);
    }
    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        if (target == this.entity) {
            Logger.logLn(this.entity.name + ".Disadvantage.getDamageChanges");
            return result * (100+this.intensity)/100;
        }
        return result;
    }
}
