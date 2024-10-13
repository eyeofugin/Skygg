package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import game.skills.Stat;

public class Injured extends Effect {
    public Injured(int turns) {
        this.turns = turns;
        this.name = "Burning";
        this.stackable = false;
        this.intensity = 10;
        this.description = "Lose " + intensity + " percent of life per turn.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    public Injured(int turns, int burnChance) {
        this(turns);
        this.successChance = burnChance;
    }

    @Override
    public Injured getNew() {
        return new Injured(this.turns,this.successChance);
    }
    @Override
    public void turnLogic() {
        Logger.logLn(this.Hero.name + ".Injured.turnLogic");
        int percentageDmg = this.Hero.getStat(Stat.CURRENT_LIFE) * 10 / 100;
        this.Hero.effectDamage(percentageDmg);
    }
}
