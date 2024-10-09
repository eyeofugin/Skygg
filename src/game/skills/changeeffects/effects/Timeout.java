package game.skills.changeeffects.effects;


import framework.Logger;
import game.skills.Effect;

public class Timeout extends Effect {
    public Timeout() {
        this.name = "Timeout";
        this.stackable = false;
        this.description = "Skip the next turn.";
        this.type = ChangeEffectType.EFFECT;
    }
    public Timeout(int turns) {
        this();
        this.turns = turns;
    }
    @Override
    public Timeout getNew() {
        return new Timeout(this.turns);
    }
    @Override
    public int getActionChanges() {
        Logger.logLn(this.entity.name + ".Timeout.getActionChanges");
        return 999;
    }
}
