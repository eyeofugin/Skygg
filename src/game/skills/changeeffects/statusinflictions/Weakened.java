package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import game.skills.Skill;

public class Weakened extends Effect {
    public Weakened(int turns) {
        this.turns = turns;
        this.name = "Weakened";
        this.stackable = false;
        this.description = "All skills have +1 cooldown.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    public Weakened(int turns, int chance) {
        this(turns);
        this.successChance = chance;
    }
    @Override
    public Weakened getNew() {
        return new Weakened(this.turns);
    }
    @Override
    public void replacementEffect(Skill cast){
        if (cast.Hero == this.Hero) {
            Logger.logLn(this.Hero.name + ".weakened.replacementEffect");
            cast.setCdMax(1+cast.getCdMax());
        }
    }
}
