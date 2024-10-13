package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;

public class Poisoned extends Effect {

    public Poisoned(int turns) {
        this.turns = turns;
        this.name = "Poisoned";
        this.stackable = false;
        this.description = "Can't heal";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public Poisoned getNew() {
        return new Poisoned(this.turns);
    }
    @Override
    public int getHealChanges(Hero caster, Hero target, Skill skill, int result) {
        Logger.logLn(this.Hero.name + ".Poisoned.getHealChanges");
        return 0;
    }

}
