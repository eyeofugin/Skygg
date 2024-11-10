package game.skills.changeeffects.effects;

import game.skills.Effect;

public class BlastingCounter extends Effect {
    public BlastingCounter(int stacks) {
        this.turns = -1;
        this.name = "Blasting Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Amount rife shots.";
        this.type = ChangeEffectType.BUFF;
    }

    @Override
    public Effect getNew() {
        return new BlastingCounter(this.stacks);
    }
}
