package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class Cover extends Effect {
    public Cover() {
        this.name = "Cover";
        this.intensity = 50;
        this.stackable = false;
        this.description = "Receive " + intensity + " percent less damage from attacks.";
        this.type = ChangeEffectType.EFFECT;
    }

    public Cover(int turns) {
        this();
        this.turns = turns;
    }

    @Override
    public Cover getNew() {
        return new Cover(this.turns);
    }

    @Override
    public int getDamageChanges(Hero caster, Hero target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        if (target == this.Hero) {
            Logger.logLn(this.Hero.name + ".Cover.getDamageChanges");
            return result * this.intensity/100;
        }
        return result;
    }
}
