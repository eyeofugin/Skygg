package game.skills.changeeffects.effects;

import game.skills.Effect;

public class AxeSwingCounter extends Effect {

    public AxeSwingCounter(int stacks) {
        this.turns = -1;
        this.name = "Axe Swing Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Amount of Axe Swings.";
    }

    @Override
    public Effect getNew() {
        return new AxeSwingCounter(this.stacks);
    }

    @Override
    public void addSubscriptions() {
    }
}
