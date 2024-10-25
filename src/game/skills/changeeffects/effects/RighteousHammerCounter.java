package game.skills.changeeffects.effects;

import game.skills.Effect;

public class RighteousHammerCounter extends Effect {
    public RighteousHammerCounter(int stacks) {
        this.turns = -1;
        this.name = "Hammer Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Hammer Swings.";
        this.type = ChangeEffectType.BUFF;
    }

    @Override
    public Effect getNew() {
        return new RighteousHammerCounter(this.stacks);
    }

    @Override
    public void addSubscriptions() {
    }
}
