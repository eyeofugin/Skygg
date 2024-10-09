package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Bounty extends Effect {
    public Bounty() {
        this.name = "Bounty";
        this.description = "Your bounty stack.";
        this.stackable = true;
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Bounty getNew() {
        return new Bounty();
    }
}
