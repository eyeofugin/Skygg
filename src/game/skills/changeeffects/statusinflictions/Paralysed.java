package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import utils.MyMaths;

public class Paralysed extends Effect {

    private static final int PARA_CHANCE = 30;

    public Paralysed(int turns) {
        this.turns = turns;
        this.name = "Paralysed";
        this.stackable = false;
        this.description = PARA_CHANCE + "% chance to lose 1 action at the start of turn";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    public Paralysed(int turns, int chance) {
        this(turns);
        this.successChance = chance;
    }

    @Override
    public Paralysed getNew() {
        return new Paralysed(this.turns, this.successChance);
    }
    @Override
    public int getActionChanges() {
        if (MyMaths.success(PARA_CHANCE)) {
            Logger.logLn(this.Hero.name + ".Paralysed.getActionChanges:-1");
            return 1;
        }
        return 0;
    }
}