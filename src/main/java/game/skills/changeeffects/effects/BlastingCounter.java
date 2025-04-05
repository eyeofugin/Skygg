package game.skills.changeeffects.effects;

import game.skills.Effect;

public class BlastingCounter extends Effect {
    public static String ICON_STRING = "BLA";
    public BlastingCounter(int stacks) {
        this.turns = -1;
        this.iconString = ICON_STRING;
        this.name = "Blasting Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Amount rife shots.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new BlastingCounter(this.stacks);
    }
}
