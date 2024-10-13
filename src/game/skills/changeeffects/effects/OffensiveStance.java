package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class OffensiveStance extends Effect {
    public OffensiveStance() {
        this.name = "Offensive Stance";
        this.stackable = false;
        this.intensity = 30;
        this.description = "Does " + intensity + " percent more damage.";
        this.type = ChangeEffectType.EFFECT;
    }

    @Override
    public OffensiveStance getNew() {
        return new OffensiveStance();
    }

    @Override
    public int getDamageChanges(Hero caster, Hero target, Skill skill, int result, Stat damageType, boolean simulated) {
        if (caster == this.Hero) {
            Logger.logLn(this.Hero.name + ".OffensiveStance.getDamageChanges");
            result *= (double) (100 + this.intensity) /100;
        }
        return result;
    }
}
