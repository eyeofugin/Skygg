package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import game.skills.Skill;

public class Rooted extends Effect {
    public Rooted(int turns) {
        this.turns = turns;
        this.name = "Rooted";
        this.stackable = false;
        this.description = "Can't use movement skills.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    @Override
    public Rooted getNew() {
        return new Rooted(this.turns);
    }
    @Override
    public boolean performSuccessCheck(Skill cast) {
        if (cast.entity == this.entity && cast.isMove()) {
            Logger.logLn(this.entity.name + ".Rooted.performSuccessCheck: false");
            return false;
        }
        return true;
    }
}
