package game.skills.changeeffects.effects;

import game.skills.Effect;

public class SwiftStrikeCounter extends Effect {

    public static String ICON_STRING = "SSC";
    public SwiftStrikeCounter(int stacks) {
        this.turns = -1;
        this.iconString = ICON_STRING;
        this.name = "Swift Swing Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Amount of Swift Swings.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new SwiftStrikeCounter(this.stacks);
    }

    @Override
    public void addSubscriptions() {

    }
}
