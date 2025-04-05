package game.skills.changeeffects.effects;

import game.skills.Effect;

public class AxeSwingCounter extends Effect {

    public static String ICON_STRING = "AXE";
    public AxeSwingCounter(int stacks) {
        this.turns = -1;
        this.iconString = ICON_STRING;
        this.name = "Axe Swing Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Amount of Axe Swings.";
        this.type = ChangeEffectType.BUFF;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public Effect getNew() {
        return new AxeSwingCounter(this.stacks);
    }

}
