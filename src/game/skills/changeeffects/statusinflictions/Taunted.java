package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import game.skills.Skill;

public class Taunted extends Effect {
    public Taunted(int turns) {
        this.turns = turns;
        this.name = "Taunted";
        this.stackable = false;
        this.description = "Can only auto attack";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public Taunted getNew() {
        return new Taunted(this.turns);
    }
    @Override
    public boolean canPerformCheck(Skill cast) {
        if (cast.entity == this.entity && !cast.isAutoAttack()) {
            Logger.logLn(this.entity.name + ".Taunted.performSuccessCheck: false");
            return false;
        }
        return true;
    }
}
