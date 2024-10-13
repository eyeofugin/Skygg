package game.skills.changeeffects.effects;


import framework.Logger;
import game.skills.Effect;

public class Enlightened extends Effect {
    public Enlightened() {
        this.name = "Enlightened";
        this.intensity = 5;
        this.stackable = true;
        this.description = "Gain " + intensity + " life per stack of Enlightened per turn.";
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Enlightened getNew() {
        return new Enlightened();
    }
    @Override
    public void turnLogic() {
        Logger.logLn(this.Hero.name + ".Enlightened.turnLogic");
        this.Hero.heal(this.Hero, this.stacks*this.intensity,null);
    }
}
