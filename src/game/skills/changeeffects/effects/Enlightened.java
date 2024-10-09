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
        Logger.logLn(this.entity.name + ".Enlightened.turnLogic");
        this.entity.heal(this.entity, this.stacks*this.intensity,null);
    }
}
